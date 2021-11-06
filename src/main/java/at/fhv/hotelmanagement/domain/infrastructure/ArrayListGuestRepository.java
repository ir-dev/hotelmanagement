package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ArrayListGuestRepository implements GuestRepository {

    private ArrayList<Guest> guestList = new ArrayList<>();

    @Override
    public void save(Guest guest) {
        guestList.add(guest);
    }

    @Override
    public List<Guest> getAll() {
        return guestList;
    }

    @Override
    public Optional<Guest> findById(String guestId) {
        for(Guest g: guestList){
            if(guestId.equals(g.id())){
                return Optional.of(g);
            }
        }
        return Optional.empty();
    }
}
