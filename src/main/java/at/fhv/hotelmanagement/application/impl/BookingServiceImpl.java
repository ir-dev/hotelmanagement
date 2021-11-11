package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.BookingStatus;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookingServiceImpl implements BookingsService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    GuestRepository guestRepository;
    @Transactional(readOnly = true)
    @Override
    public List<BookingDTO> allBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDTO> bookingsDto = new ArrayList<>();

        for (Booking booking : bookings){
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
        if (booking.isEmpty()){
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
        if(booking.isEmpty()){
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
    public void createBooking(BookingForm bookingForm) {
        Optional<Organization> organization;
        if (bookingForm.getIsOrganization()) {
            organization = Optional.of(new Organization(bookingForm.getOrganizationName(), bookingForm.getAgreementCode()));
        } else {
            organization = Optional.empty();
        }
        Address address = new Address(bookingForm.getStreet(), bookingForm.getZipcode(), bookingForm.getCity(), bookingForm.getCountry());
        Guest guest = new Guest(new GuestId("1"), organization, bookingForm.getSalutation(), bookingForm.getFirstName(), bookingForm.getLastName(), bookingForm.getBirthday(), address, bookingForm.getSpecialNotes());
        guestRepository.store(guest);

        PaymentInformation paymentInformation = new PaymentInformation(bookingForm.getCardHolderName(), bookingForm.getCardNumber(), bookingForm.getCardValidThru(), bookingForm.getCardCvc(), bookingForm.getPaymentType());
        Booking booking = new Booking(new BookingNo("1234"), BookingStatus.PENDING, bookingForm.getArrivalDate(), bookingForm.getDepartureDate(), bookingForm.getArrivalTime(), bookingForm.getNumberOfPersons(), bookingForm.getSelectedCategoriesRoomCount(), guest.getId(), paymentInformation);
        bookingRepository.store(booking);
    }
}
