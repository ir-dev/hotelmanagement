package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.BookingStatus;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import at.fhv.hotelmanagement.view.forms.StayForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static at.fhv.hotelmanagement.view.GenericViewController.redirectError;

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
    public void createStayForBooking(String bookingNo, StayForm stayForm) throws CreateStayException {

        final Optional<Booking> optBooking = this.bookingRepository.findByNo(new BookingNo(bookingNo));

        if (optBooking.isEmpty()) {
            throw new IllegalArgumentException("BookingNo invalid.");
        }

        Booking booking = optBooking.get();

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new CreateStayException("The status of the booking to check-in must be PENDING.");
        }

        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = stayForm.getDepartureDate();
        Integer numberOfPersons = stayForm.getNumberOfPersons();
        Map<String, Integer> selectedCategoriesRoomCount = stayForm.getSelectedCategoriesRoomCount();
        LocalDate birthday = stayForm.getBirthday();

        // DepartureDate must be after ArrivalDate (at least one day)
        if (!departureDate.isAfter(arrivalDate)) {
            throw new CreateStayException("DepartureDate must be after ArrivalDate.");
        }

        // NumberOfPersons must be greater or equal to 1
        if (!(numberOfPersons >= 1)) {
            throw new CreateStayException("NumberOfPersons must be greater or equal to 1");
        }

        // Age (Birthday) must be equal or greater than 18 years
        if (!(birthday.isBefore(LocalDate.now().minusYears(18).plusDays(1)))) {
            throw new CreateStayException("Age (Birthday) must be equal or greater than 18 years");
        }

        int totalMaxPersons = 0;
        for (Map.Entry<String, Integer> selectedCategoryRoomCount: selectedCategoriesRoomCount.entrySet()) {
            Category selectedCategory = this.categoryRepository.findByName(selectedCategoryRoomCount.getKey()).get();

            // SelectedCategories for each category: - min. zero and max. count of available rooms for category
            int roomCount = selectedCategoryRoomCount.getValue();
            int availableRoomCount = selectedCategory.getAvailableRoomsCount(arrivalDate, departureDate);
            int maxPersons = selectedCategory.getMaxPersons();
            if (!(roomCount >= 0 && roomCount <= availableRoomCount)) {
                throw new CreateStayException("SelectedCategoryRoomCount: min. zero, max. count of available rooms for category.");
            }

            totalMaxPersons += (roomCount * maxPersons);
        }

        // SelectedCategories total: - min. sum of all max. persons for each category multiplied by count
        if (!(numberOfPersons <= totalMaxPersons)) {
            throw new CreateStayException("SelectedCategoryRoomCount Total: max. sum of all max. persons (for each category).");
        }

        Organization organization = null;
        if (stayForm.getIsOrganization()) {
            organization = new Organization(stayForm.getOrganizationName(), stayForm.getOrganizationAgreementCode());
        }
        Address address = new Address(
                stayForm.getStreet(),
                stayForm.getZipcode(),
                stayForm.getCity(),
                stayForm.getCountry());
        // TODO: check if guest already exists and then use this
        Guest guest = new Guest(
                this.guestRepository.nextIdentity(),
                organization,
                stayForm.getSalutation(),
                stayForm.getFirstName(),
                stayForm.getLastName(),
                birthday,
                address,
                stayForm.getSpecialNotes());

        this.guestRepository.store(guest);

        PaymentInformation paymentInformation = new PaymentInformation(
                stayForm.getCardHolderName(),
                stayForm.getCardNumber(),
                stayForm.getCardValidThru(),
                stayForm.getCardCvc(),
                stayForm.getPaymentType());

        Stay stay = new Stay(
                this.stayRepository.nextIdentity(),
                new BookingNo(bookingNo),
                arrivalDate,
                departureDate,
                LocalTime.now(),
                numberOfPersons,
                selectedCategoriesRoomCount,
                guest.getGuestId(),
                paymentInformation
        );

        this.stayRepository.store(stay);

        booking.close();
    }
}
