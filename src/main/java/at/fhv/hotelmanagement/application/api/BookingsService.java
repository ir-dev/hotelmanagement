package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface BookingsService {

    List<BookingDTO> getAll();

    Optional<BookingDTO> getByBookingNr(String bookingNr);

    Optional<BookingDetailsDTO> getDetailsByBookingNr(String bookingNr);
}
