package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ArrayListGuestRepository implements GuestRepository {
    private ArrayList<Guest> guests = new ArrayList<>();

    @Override
    public List<Guest> findAll() {
        if (guests.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(guests);
    }

    @Override
    public Optional<Guest> findById(String guestId) {
        return guests.stream()
                .filter(guest -> guest.getId().equals(guestId))
                .findFirst();
    }

    @Override
    public void store(Guest guest) {
        guests.add(guest);
    }
}
