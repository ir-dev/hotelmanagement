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
        if (bookingForm.getIsOrganizationValue()) {
            organization = Optional.of(new Organization(bookingForm.getOrganizationNameValue(), bookingForm.getAgreementCodeValue()));
        } else {
            organization = Optional.empty();
        }
        Address address = new Address(bookingForm.getStreetValue(), bookingForm.getZipcodeValue(), bookingForm.getCityValue(), bookingForm.getCountryValue());
        Guest guest = new Guest(new GuestId("1"), organization, bookingForm.getSalutationValue(), bookingForm.getFirstNameValue(), bookingForm.getLastNameValue(), bookingForm.getBirthdayValue(), address, bookingForm.getSpecialNotesValue());
        guestRepository.store(guest);

        PaymentInformation paymentInformation = new PaymentInformation(bookingForm.getCardHolderNameValue(), bookingForm.getCardNumberValue(), bookingForm.getCardValidThruValue(), bookingForm.getCardCvcValue(), bookingForm.getPaymentTypeValue());
        Booking booking = new Booking(new BookingNo("1234"), BookingStatus.PENDING, bookingForm.getArrivalDateValue(), bookingForm.getDepartureDateValue(), bookingForm.getArrivalTimeValue(), bookingForm.getNumberOfPersonsValue(), bookingForm.getSelectedCategoriesRoomCountValue(), guest.getId(), paymentInformation);
        bookingRepository.store(booking);
    }
}
