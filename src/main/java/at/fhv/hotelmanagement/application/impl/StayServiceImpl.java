package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.*;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.InvoiceState;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import at.fhv.hotelmanagement.domain.model.services.api.InvoiceService;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import at.fhv.hotelmanagement.view.forms.StayForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class StayServiceImpl implements StayService {
    @Autowired
    StayRepository stayRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    InvoiceService invoiceService;

    @Transactional(readOnly = true)
    @Override
    public List<StayDTO> allStays() {
        List<Stay> stays = this.stayRepository.findAll();
        List<StayDTO> staysDto = new ArrayList<>();

        for (Stay stay : stays) {
            staysDto.add(buildStayDto(stay));
        }

        return staysDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<StayDTO> stayByStayId(String stayId) {
        Optional<Stay> stay = this.stayRepository.findById(new StayId(stayId));

        if (stay.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildStayDto(stay.get()));
    }

    private StayDTO buildStayDto(Stay stay) {
        return StayDTO.builder()
                .withStayEntity(stay)
                .build();
    }

    @Transactional
    @Override
    public void createStayForBooking(String bookingNoStr, StayForm stayForm) throws CreateStayException, CreateGuestException, InsufficientRoomsException {
        BookingNo bookingNo = new BookingNo(bookingNoStr);
        Booking booking = this.bookingRepository.findByNo(bookingNo).orElseThrow();

        // create booking value objects
        Organization organization = null;
        if (stayForm.getIsOrganization()) {
            organization = new Organization(stayForm.getOrganizationName(), stayForm.getOrganizationAgreementCode());
        }
        Address address = new Address(
                stayForm.getStreet(),
                stayForm.getZipcode(),
                stayForm.getCity(),
                stayForm.getCountry()
        );
        PaymentInformation paymentInformation = new PaymentInformation(
                stayForm.getCardHolderName(),
                stayForm.getCardNumber(),
                stayForm.getCardValidThru(),
                stayForm.getCardCvc(),
                stayForm.getPaymentType()
        );

        Map<String, Integer> selectedCategoryNamesRoomCount = stayForm.getSelectedCategoriesRoomCount();
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        for (Map.Entry<String, Integer> selectedCategoryNameRoomCount : selectedCategoryNamesRoomCount.entrySet()) {
            selectedCategoriesRoomCount.put(this.categoryRepository.findByName(selectedCategoryNameRoomCount.getKey()).orElseThrow(), selectedCategoryNameRoomCount.getValue());
        }
        LocalDate arrivalDate = LocalDate.now();
        LocalTime arrivalTime = LocalTime.now();
        LocalDate departureDate = stayForm.getDepartureDate();
        // create guest and booking entity
        Guest guest = GuestFactory.createGuest(
                this.guestRepository.nextIdentity(),
                organization,
                stayForm.getSalutation(),
                stayForm.getFirstName(),
                stayForm.getLastName(),
                stayForm.getBirthday(),
                address,
                stayForm.getSpecialNotes()
        );
        Stay stay = StayFactory.createStayForBooking(
                this.stayRepository.nextIdentity(),
                booking,
                bookingNo,
                arrivalDate,
                departureDate,
                stayForm.getNumberOfPersons(),
                selectedCategoriesRoomCount,
                guest.getGuestId(),
                paymentInformation
        );

        this.guestRepository.store(guest);
        this.stayRepository.store(stay);

        // assign rooms to selected categories
        assignRooms(
                selectedCategoryNamesRoomCount,
                arrivalDate,
                departureDate
        );

        // change booking state to closed
        booking.close();
    }

    private void assignRooms(Map<String, Integer> selectedCategories, LocalDate fromDate, LocalDate toDate) throws InsufficientRoomsException {

        for (Map.Entry<String, Integer> selectedCategory : selectedCategories.entrySet()) {
            String selectedCategoryName = selectedCategory.getKey();
            Integer selectedCategoryCount = selectedCategory.getValue();

            List<Room> availableCategoryRooms = this.categoryRepository.findCategoryRoomsByState(selectedCategoryName, RoomState.AVAILABLE);

            if (availableCategoryRooms.size() < selectedCategoryCount) {
                throw new InsufficientRoomsException(selectedCategoryName);
            }

            Iterator<Room> iterator = availableCategoryRooms.iterator();

            for (int i = 0; i < selectedCategoryCount; i++) {
                Room room = iterator.next();
                // change state of room to occupied for given timespan
                room.occupied(fromDate, toDate);
            }
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<InvoiceDTO> viewChargeStay(String stayId) {
        Optional<Stay> stayOpt = this.stayRepository.findById(new StayId(stayId));
        Stay stay = stayOpt.orElseThrow();
        Invoice invoice = stay.getInvoice();

        if(invoice.getInvoiceState().equals(InvoiceState.PENDING)){
            this.invoiceService.composeInvoice(stay, this.categoryRepository.findAll(), false);
        }

        return Optional.of(buildInvoiceDto(stay.getInvoice(), stay.getGuestId()));
    }

    @Transactional
    @Override
    public Optional<InvoiceDTO> chargeStay(String stayId) {
        Optional<Stay> stayOpt = this.stayRepository.findById(new StayId(stayId));
        Stay stay = stayOpt.orElseThrow();
        this.invoiceService.composeInvoice(stay, this.categoryRepository.findAll(), true);

        return Optional.of(buildInvoiceDto(stay.getInvoice(), stay.getGuestId()));
    }


    private InvoiceDTO buildInvoiceDto(Invoice invoice, GuestId guestId) {
        return InvoiceDTO.builder()
                .withInvoiceEntity(invoice)
                .withLineItemsDTO(buildLineItemsDto(invoice.getLineItems()))
                .withGuestDTO(GuestDTO.builder().withGuestEntity(guestById(guestId).get()).build())
                .build();
    }

    private Set<InvoiceLineDTO> buildLineItemsDto(Set<InvoiceLine> lineItems) {
        Set<InvoiceLineDTO> lineItemsDto = new HashSet<>();
        for(InvoiceLine line : lineItems) {
           lineItemsDto.add(InvoiceLineDTO.builder().withInvoiceLineEntity(line).build());
        }
        return lineItemsDto;
    }

    private Optional<Guest> guestById(GuestId guestId) {
        Optional<Guest> guestOpt = this.guestRepository.findById(guestId);
        if (guestOpt.isEmpty()) {
            return Optional.empty();
        }
        return guestOpt;
    }

}
