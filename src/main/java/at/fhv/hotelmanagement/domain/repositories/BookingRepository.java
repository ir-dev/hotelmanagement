package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;

import java.util.List;
import java.util.Optional;

public interface BookingRepository {
    BookingNo nextIdentity();

    List<Booking> findAll();

    Optional<Booking> findByNo(BookingNo bookingNo);

    void store(Booking booking);
}
