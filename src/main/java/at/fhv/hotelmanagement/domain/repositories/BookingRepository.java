package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.BookingNo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository {
    List<Booking> findAll();

    Optional<Booking> findByNo(BookingNo bookingNo);

    BookingNo nextIdentity();

    void store(Booking booking);
}
