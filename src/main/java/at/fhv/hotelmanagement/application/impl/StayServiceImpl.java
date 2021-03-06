package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.converters.CategoryConverter;
import at.fhv.hotelmanagement.application.dto.*;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryService;
import at.fhv.hotelmanagement.domain.model.category.RoomAssignmentException;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.model.stay.*;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import at.fhv.hotelmanagement.view.forms.InvoiceRecipientForm;
import at.fhv.hotelmanagement.view.forms.StayForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
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
    CategoryService categoryService;

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
                .withDetails(buildStayDetailsDto(stay))
                .build();
    }

    private StayDetailsDTO buildStayDetailsDto(Stay stay) {
        return StayDetailsDTO.builder()
                .withStayEntity(stay)
                .withGuestDTO(buildGuestDto(this.guestRepository.findById(stay.getGuestId()).orElseThrow()))
                .withRoomNumbers(this.categoryRepository.findRoomNumbersByStayId(stay.getStayId()))
                .build();
    }

    private GuestDTO buildGuestDto(Guest guest) {
        return GuestDTO.builder()
                .withGuestEntity(guest)
                .build();
    }

    private Guest createGuestFromStayForm(StayForm stayForm) throws CreateGuestException {
        Organization organization = null;
        if (stayForm.getIsOrganization()) {
            if (stayForm.getDiscountRate().compareTo(BigDecimal.valueOf(0)) < 0 || stayForm.getDiscountRate().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new CreateGuestException("DiscountRate below 0 or above 100");
            }
            organization = new Organization(stayForm.getOrganizationName(), stayForm.getDiscountRate().divide(BigDecimal.valueOf(100)).round(new MathContext(2)));
        }
        Address address = new Address(
                stayForm.getStreet(),
                stayForm.getZipcode(),
                stayForm.getCity(),
                stayForm.getCountry()
        );

        Guest guest = GuestFactory.createGuest(
                this.guestRepository.nextIdentity(),
                organization,
                stayForm.getSalutation(),
                stayForm.getFirstName(),
                stayForm.getLastName(),
                stayForm.getDateOfBirth(),
                address,
                stayForm.getSpecialNotes()
        );

        this.guestRepository.store(guest);

        return guest;
    }



    @Transactional
    @Override
    public void createStayForBooking(String bookingNoStr, StayForm stayForm) throws CreateStayException, CreateGuestException, RoomAssignmentException {
        BookingNo bookingNo = new BookingNo(bookingNoStr);
        Booking booking = this.bookingRepository.findByNo(bookingNo).orElseThrow();
        Map<Category, Integer> selectedCategoriesRoomCount;
        try {
            selectedCategoriesRoomCount = CategoryConverter.convertToSelectedCategoriesRoomCount(stayForm.getSelectedCategoriesRoomCount());
        } catch (EntityNotFoundException e) {
            throw new NoSuchElementException(e.getMessage());
        }
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = stayForm.getDepartureDate();

        Stay stay = StayFactory.createStayForBooking(
                this.stayRepository.nextIdentity(),
                booking,
                bookingNo,
                arrivalDate,
                departureDate,
                stayForm.getNumberOfPersons(),
                selectedCategoriesRoomCount,
                createGuestFromStayForm(stayForm).getGuestId(),
                new PaymentInformation(
                        stayForm.getCardHolderName(),
                        stayForm.getCardNumber(),
                        stayForm.getCardValidThru(),
                        stayForm.getCardCvc(),
                        stayForm.getPaymentType()
                )
        );

        this.stayRepository.store(stay);

        // auto assign rooms to selected categories
        this.categoryService.autoAssignRooms(
                selectedCategoriesRoomCount,
                arrivalDate,
                departureDate,
                stay.getStayId()
        );

        // change booking state to closed
        booking.close();
    }

    @Transactional
    @Override
    public void createStayForWalkIn(StayForm stayForm) throws CreateStayException, CreateGuestException, RoomAssignmentException {
        Map<Category, Integer> selectedCategoriesRoomCount;
        try {
            selectedCategoriesRoomCount = CategoryConverter.convertToSelectedCategoriesRoomCount(stayForm.getSelectedCategoriesRoomCount());
        } catch (EntityNotFoundException e) {
            throw new NoSuchElementException(e.getMessage());
        }
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = stayForm.getDepartureDate();

        Stay stay = StayFactory.createStayForWalkIn(
                this.stayRepository.nextIdentity(),
                arrivalDate,
                departureDate,
                stayForm.getNumberOfPersons(),
                selectedCategoriesRoomCount,
                createGuestFromStayForm(stayForm).getGuestId(),
                new PaymentInformation(
                        stayForm.getCardHolderName(),
                        stayForm.getCardNumber(),
                        stayForm.getCardValidThru(),
                        stayForm.getCardCvc(),
                        stayForm.getPaymentType()
                )
        );

        this.stayRepository.store(stay);

        // auto assign rooms to selected categories
        this.categoryService.autoAssignRooms(
                selectedCategoriesRoomCount,
                arrivalDate,
                departureDate,
                stay.getStayId()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<InvoiceDTO> invoiceByInvoiceNo(String invoiceNo) {
        Optional<Stay> stayOpt = this.stayRepository.findByInvoiceNo(new InvoiceNo(invoiceNo));

        if (stayOpt.isEmpty()) {
            return Optional.empty();
        }

        Optional<Guest> guestOpt = this.guestRepository.findById(stayOpt.get().getGuestId());
        Optional<Invoice> invoiceOpt = this.stayRepository.findInvoiceByInvoiceNo(new InvoiceNo(invoiceNo));

        if (guestOpt.isEmpty() || invoiceOpt.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildInvoiceDto(invoiceOpt.get(), guestOpt.get(), invoiceOpt.get().getInvoiceRecipient(), stayOpt.get().getStayId()));
    }


    @Transactional(readOnly = true)
    @Override
    public List<InvoiceDTO> allStayInvoices(String stayId) throws EntityNotFoundException {
        Stay stay = this.stayRepository.findById(new StayId(stayId)).orElseThrow(() -> new EntityNotFoundException(Stay.class, stayId));
        Guest guest = this.guestRepository.findById(stay.getGuestId()).orElseThrow(() -> new EntityNotFoundException(Guest.class, stay.getGuestId().toString()));
        InvoiceRecipient invoiceRecipient = new InvoiceRecipient(
                guest.getFirstName(),
                guest.getLastName(),
                guest.getAddress()
        );

        if (stay.getInvoices().isEmpty()) {
            return Collections.emptyList();
        }

        return stay.getInvoices().stream()
                .map(i -> buildInvoiceDto(i, guest, invoiceRecipient, stay.getStayId()))
                .collect(Collectors.toList());
    }


    private InvoiceRecipient createInvoiceRecipient(InvoiceRecipientForm invoiceRecipientForm) {
        Address address = new Address(
                invoiceRecipientForm.getStreet(),
                invoiceRecipientForm.getZipcode(),
                invoiceRecipientForm.getCity(),
                invoiceRecipientForm.getCountry()
        );
        return new InvoiceRecipient(
                invoiceRecipientForm.getFirstName(),
                invoiceRecipientForm.getLastName(),
                address);
    }


    @Transactional(readOnly = true)
    @Override
    public InvoiceDTO chargeStayPreview(String stayId) throws EntityNotFoundException, PriceCurrencyMismatchException {
        Stay stay = this.stayRepository.findById(new StayId(stayId)).orElseThrow(() -> new EntityNotFoundException(Stay.class, stayId));
        Guest guest = this.guestRepository.findById(stay.getGuestId()).orElseThrow(() -> new EntityNotFoundException(Guest.class, stay.getGuestId().toString()));
        InvoiceRecipient invoiceRecipient = new InvoiceRecipient(
                guest.getFirstName(),
                guest.getLastName(),
                guest.getAddress()
        );

        Map<Category, Integer> billableLineItemCounts = CategoryConverter.convertToSelectedCategoriesRoomCount(stay.billableLineItemCounts());

        return buildInvoiceDto(stay.generateInvoice(billableLineItemCounts, guest.getOrganizationDiscountRate(), invoiceRecipient), guest, invoiceRecipient, stay.getStayId());
    }

    @Transactional(readOnly = true)
    @Override
    public InvoiceDTO chargeStayPreview(String stayId, Map<String, Integer> selectedLineItemProductNamesCount, InvoiceRecipientForm invoiceRecipientForm) throws EntityNotFoundException, PriceCurrencyMismatchException {
        Stay stay = this.stayRepository.findById(new StayId(stayId)).orElseThrow(() -> new EntityNotFoundException(Stay.class, stayId));
        Guest guest = this.guestRepository.findById(stay.getGuestId()).orElseThrow(() -> new EntityNotFoundException(Guest.class, stay.getGuestId().toString()));
        InvoiceRecipient invoiceRecipient = createInvoiceRecipient(invoiceRecipientForm);

        Map<Category, Integer> selectedLineItemProductsCount = CategoryConverter.convertToSelectedCategoriesRoomCount(selectedLineItemProductNamesCount);

        return buildInvoiceDto(stay.generateInvoice(selectedLineItemProductsCount, guest.getOrganizationDiscountRate(), invoiceRecipient), guest, invoiceRecipient, stay.getStayId());
    }

    @Transactional
    @Override
    public String chargeStay(String stayId, Map<String, Integer> selectedLineItemProductNamesCount, InvoiceRecipientForm invoiceRecipientForm) throws EntityNotFoundException, PriceCurrencyMismatchException {
        InvoiceRecipient invoiceRecipient = createInvoiceRecipient(invoiceRecipientForm);
        this.stayRepository.storeRecipient(invoiceRecipient);

        Stay stay = this.stayRepository.findById(new StayId(stayId)).orElseThrow(() -> new EntityNotFoundException(Stay.class, stayId));
        Guest guest = this.guestRepository.findById(stay.getGuestId()).orElseThrow(() -> new EntityNotFoundException(Guest.class, stay.getGuestId().toString()));

        Map<Category, Integer> selectedLineItemProductsCount = CategoryConverter.convertToSelectedCategoriesRoomCount(selectedLineItemProductNamesCount);

        return stay.finalizeInvoice(this.stayRepository.nextInvoiceSeq(), selectedLineItemProductsCount, guest.getOrganizationDiscountRate(), invoiceRecipient).getNo();
    }

    @Transactional
    @Override
    public void checkoutStay(String stayId) throws EntityNotFoundException, BillingOpenException, IllegalStateException {
        Stay stay = this.stayRepository.findById(new StayId(stayId)).orElseThrow(() -> new EntityNotFoundException(Stay.class, stayId));

        stay.checkout();

        Set<Category> stayCategories;
        try {
            stayCategories = CategoryConverter.convertToSelectedCategoriesRoomCount(stay.getSelectedCategoriesRoomCount()).keySet();
        } catch (EntityNotFoundException e) {
            throw new NoSuchElementException(e.getMessage());
        }
        this.categoryService.releaseRooms(
                this.categoryRepository.findRoomNumbersByStayId(stay.getStayId()),
                stayCategories,
                stay.getStayId()
        );
    }

    private InvoiceDTO buildInvoiceDto(Invoice invoice, Guest guest, InvoiceRecipient invoiceRecipient, StayId stayId) {
        return InvoiceDTO.builder()
                .withInvoiceEntity(invoice)
                .withLineItemsDTO(buildLineItemsDto(invoice.getLineItems()))
                .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                .withInvoiceRecipientDTO(InvoiceRecipientDTO.builder().withInvoiceRecipientEntity(invoiceRecipient).build())
                .withStayId(stayId)
                .build();
    }

    private Set<InvoiceLineDTO> buildLineItemsDto(Set<InvoiceLine> lineItems) {
        Set<InvoiceLineDTO> lineItemsDto = new HashSet<>();

        for (InvoiceLine lineItem : lineItems) {
            lineItemsDto.add(InvoiceLineDTO.builder().withInvoiceLineEntity(lineItem).build());
        }

        return lineItemsDto;
    }
}