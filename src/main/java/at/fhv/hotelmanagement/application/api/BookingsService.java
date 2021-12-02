package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.guest.CreateGuestException;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BookingsService {
    List<BookingDTO> allBookings();

    Optional<BookingDTO> bookingByBookingNo(String bookingNo);

    Optional<BookingDetailsDTO> bookingDetailsByBookingNo(String bookingNo);

    void createBooking(BookingForm bookingForm) throws CreateBookingException, CreateGuestException;
}
