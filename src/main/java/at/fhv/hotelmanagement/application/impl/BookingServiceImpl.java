package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.converters.CategoryConverter;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

@Service
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
        List<Booking> bookings = this.bookingRepository.findAll();
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

        return Optional.of(buildBookingDetailsDto(booking.orElseThrow()));
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
                .withGuestDTO(guestByBooking(booking).orElseThrow())
                .build();
    }

    private Optional<GuestDTO> guestByBooking(Booking booking) {
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
            if (bookingForm.getDiscountRate().compareTo(BigDecimal.valueOf(0)) < 0 || bookingForm.getDiscountRate().compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new CreateGuestException("DiscountRate below 0 or above 100");
            }
            organization = new Organization(bookingForm.getOrganizationName(), bookingForm.getDiscountRate().divide(BigDecimal.valueOf(100)).round(new MathContext(2)));
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

        Map<Category, Integer> selectedCategoriesRoomCount = null;
        try {
            selectedCategoriesRoomCount = CategoryConverter.convertToSelectedCategoriesRoomCount(bookingForm.getSelectedCategoriesRoomCount());
        } catch (EntityNotFoundException e) {
            throw new NoSuchElementException(e.getMessage());
        }

        // create guest and booking entity
        Guest guest = GuestFactory.createGuest(
                this.guestRepository.nextIdentity(),
                organization,
                bookingForm.getSalutation(),
                bookingForm.getFirstName(),
                bookingForm.getLastName(),
                bookingForm.getDateOfBirth(),
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
