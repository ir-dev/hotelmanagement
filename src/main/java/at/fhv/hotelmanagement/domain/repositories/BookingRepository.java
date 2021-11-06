package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.Booking;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository {

    void save(Booking booking);

    List<Booking> getAll();

    Optional<Booking> findByBookingNr(String bookingNr);

}
