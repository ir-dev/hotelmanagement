package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.guest.Guest;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;

import java.util.List;
import java.util.Optional;

public interface GuestRepository {
    GuestId nextIdentity();

    List<Guest> findAll();

    Optional<Guest> findById(GuestId guestId);

    void store(Guest guest);
}
