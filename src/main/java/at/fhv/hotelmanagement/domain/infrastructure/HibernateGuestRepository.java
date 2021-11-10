package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.model.GuestId;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HibernateGuestRepository implements GuestRepository {

    @Override
    public List<Guest> findAll() {
        return null;
    }

    @Override
    public Optional<Guest> findById(String guestId) {
        return Optional.empty();
    }

    @Override
    public GuestId nextIdentity() {
        return new GuestId(java.util.UUID.randomUUID().toString().toUpperCase());
    }

    @Override
    public void store(Guest guest) {

    }
}
