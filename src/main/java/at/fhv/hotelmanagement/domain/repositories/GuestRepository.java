package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.Guest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository {
    List<Guest> findAll();

    Optional<Guest> findById(String guestId);

    void store(Guest guest);
}