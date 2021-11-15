package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class BookingServiceImpl implements BookingsService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    GuestRepository guestRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookingDTO> allBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDTO> bookingsDto = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingsDto.add(BookingDTO.builder()
                    .withBookingEntity(booking)
                    .withDetails(buildBookingDetailsDto(booking))
                    .build());
        }

        return bookingsDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookingDTO> bookingByBookingNo(String bookingNo) {
        Optional<Booking> booking = bookingRepository.findByNo(new BookingNo(bookingNo));
        if (booking.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(BookingDTO.builder()
                .withBookingEntity(booking.get())
                .withDetails(buildBookingDetailsDto(booking.get()))
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookingDetailsDTO> bookingDetailsByBookingNo(String bookingNo) {
        Optional<Booking> booking = bookingRepository.findByNo(new BookingNo(bookingNo));
        if (booking.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildBookingDetailsDto(booking.get()));
    }

    private BookingDetailsDTO buildBookingDetailsDto(Booking booking) {
        return BookingDetailsDTO.builder()
                .withBookingEntity(booking)
                .build();
    }

    @Transactional
    @Override
    public void createBooking(BookingForm bookingForm) throws CreateBookingException {
        LocalDate arrivalDate = bookingForm.getArrivalDate();
        LocalDate departureDate = bookingForm.getDepartureDate();
        Integer numberOfPersons = bookingForm.getNumberOfPersons();
        Map<String, Integer> selectedCategoriesRoomCount = bookingForm.getSelectedCategoriesRoomCount();
        LocalDate birthday = bookingForm.getBirthday();

        // ArrivalDate is today or in the future
        if (!arrivalDate.isAfter(LocalDate.now().minusDays(1))) {
            throw new CreateBookingException("ArrivalDate must be today or in the future.");
        }

        // DepartureDate must be after ArrivalDate (at least one day)
        if (!departureDate.isAfter(arrivalDate)) {
            throw new CreateBookingException("DepartureDate must be after ArrivalDate.");
        }

        // NumberOfPersons must be greater or equal to 1
        if (!(numberOfPersons >= 1)) {
            throw new CreateBookingException("NumberOfPersons must be greater or equal to 1");
        }

        // Age (Birthday) must be equal or greater than 18 years
        if (!(birthday.isBefore(LocalDate.now().minusYears(18).plusDays(1)))) {
            throw new CreateBookingException("Age (Birthday) must be equal or greater than 18 years");
        }

        int totalMaxPersons = 0;
        for (Map.Entry<String, Integer> selectedCategoryRoomCount: selectedCategoriesRoomCount.entrySet()) {
            Category selectedCategory = categoryRepository.findByName(selectedCategoryRoomCount.getKey()).get();

            // SelectedCategories for each category: - min. zero and max. count of available rooms for category
            int roomCount = selectedCategoryRoomCount.getValue();
            int availableRoomCount = selectedCategory.getAvailableRoomsCount(arrivalDate, departureDate);
            int maxPersons = selectedCategory.getMaxPersons();
            if (!(roomCount >= 0 && roomCount <= availableRoomCount)) {
                throw new CreateBookingException("SelectedCategoryRoomCount: min. zero, max. count of available rooms for category.");
            }

            totalMaxPersons += (roomCount * maxPersons);
        }

        // SelectedCategories total: - min. sum of all max. persons for each category multiplied by count
        if (!(numberOfPersons <= totalMaxPersons)) {
            throw new CreateBookingException("SelectedCategoryRoomCount Total: max. sum of all max. persons (for each category).");
        }

        Organization organization = null;
        if (bookingForm.getIsOrganization()) {
            organization = new Organization(bookingForm.getOrganizationName(), bookingForm.getOrganizationAgreementCode());
        }

        Address address = new Address(bookingForm.getStreet(), bookingForm.getZipcode(), bookingForm.getCity(), bookingForm.getCountry());
        Guest guest = new Guest(guestRepository.nextIdentity(), organization, bookingForm.getSalutation(), bookingForm.getFirstName(), bookingForm.getLastName(), bookingForm.getBirthday(), address, bookingForm.getSpecialNotes());
        guestRepository.store(guest);

        PaymentInformation paymentInformation = new PaymentInformation(bookingForm.getCardHolderName(), bookingForm.getCardNumber(), bookingForm.getCardValidThru(), bookingForm.getCardCvc(), bookingForm.getPaymentType());
        Booking booking = new Booking(bookingRepository.nextIdentity(), bookingForm.getArrivalDate(), bookingForm.getDepartureDate(), bookingForm.getArrivalTime(), bookingForm.getNumberOfPersons(), bookingForm.getSelectedCategoriesRoomCount(), guest.getGuestId(), paymentInformation);
        bookingRepository.store(booking);
    }
}
