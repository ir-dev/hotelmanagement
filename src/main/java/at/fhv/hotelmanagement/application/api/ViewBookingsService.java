package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.BookingDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ViewBookingsService {

    List<BookingDTO> getAll();

    BookingDTO getById(String bookingId);
}
