package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class BookingServiceImpl implements BookingsService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<BookingDTO> allBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDTO> bookingsDto = new ArrayList<>();

        for (Booking booking : bookings) {
            bookingsDto.add(buildBookingDto(booking));
        }

        return bookingsDto;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookingDTO> bookingByBookingNo(String bookingNo) {
        Optional<Booking> booking = this.bookingRepository.findByNo(new BookingNo(bookingNo));

        if (booking.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildBookingDto(booking.get()));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<BookingDetailsDTO> bookingDetailsByBookingNo(String bookingNo) {
        Optional<Booking> booking = this.bookingRepository.findByNo(new BookingNo(bookingNo));

        if (booking.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildBookingDetailsDto(booking.get()));
    }

    private BookingDTO buildBookingDto(Booking booking) {
        return BookingDTO.builder()
                .withBookingEntity(booking)
                .withDetails(buildBookingDetailsDto(booking))
                .build();
    }

    private BookingDetailsDTO buildBookingDetailsDto(Booking booking) {
        return BookingDetailsDTO.builder()
                .withBookingEntity(booking)
                .withGuestDTO(guestByBooking(booking).get())
                .build();
    }

    public Optional<GuestDTO> guestByBooking(Booking booking) {
        Optional<Guest> guest = this.guestRepository.findById(booking.getGuestId());
        if (guest.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buildGuestDto(guest.get()));
    }

    private GuestDTO buildGuestDto(Guest guest) {
        return GuestDTO.builder()
                .withGuestEntity(guest)
                .build();
    }

    @Transactional
    @Override
    public void createBooking(BookingForm bookingForm) throws CreateBookingException, CreateGuestException {
        // create booking value objects
        Organization organization = null;
        if (bookingForm.getIsOrganization()) {
            organization = new Organization(bookingForm.getOrganizationName(), bookingForm.getOrganizationAgreementCode());
        }
        Address address = new Address(
                bookingForm.getStreet(),
                bookingForm.getZipcode(),
                bookingForm.getCity(),
                bookingForm.getCountry()
        );
        PaymentInformation paymentInformation = new PaymentInformation(
                bookingForm.getCardHolderName(),
                bookingForm.getCardNumber(),
                bookingForm.getCardValidThru(),
                bookingForm.getCardCvc(),
                bookingForm.getPaymentType()
        );

        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        for (Map.Entry<String, Integer> selectedCategoryNameRoomCount : bookingForm.getSelectedCategoriesRoomCount().entrySet()) {
            selectedCategoriesRoomCount.put(this.categoryRepository.findByName(selectedCategoryNameRoomCount.getKey()).orElseThrow(), selectedCategoryNameRoomCount.getValue());
        }
        // create guest and booking entity
        Guest guest = GuestFactory.createGuest(
                this.guestRepository.nextIdentity(),
                organization,
                bookingForm.getSalutation(),
                bookingForm.getFirstName(),
                bookingForm.getLastName(),
                bookingForm.getBirthday(),
                address,
                bookingForm.getSpecialNotes()
        );
        Booking booking = BookingFactory.createBooking(
                this.bookingRepository.nextIdentity(),
                bookingForm.getArrivalDate(),
                bookingForm.getDepartureDate(),
                bookingForm.getArrivalTime(),
                bookingForm.getNumberOfPersons(),
                selectedCategoriesRoomCount,
                guest.getGuestId(),
                paymentInformation
        );

        this.guestRepository.store(guest);
        this.bookingRepository.store(booking);
    }
}
