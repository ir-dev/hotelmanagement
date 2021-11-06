package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ArrayListBookingRepository implements BookingRepository {

    private ArrayList<Booking> bookingList = new ArrayList<>();

    @Override
    public void save(Booking booking){
        bookingList.add(booking);
    }

    @Override
    public ArrayList<Booking> getAll() {
        return bookingList;
    }

    @Override
    public Optional<Booking> findByBookingNr(String bookingNr) {
        for(Booking b: bookingList){
            if(bookingNr.equals(b.bookingNr())){
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }
}
