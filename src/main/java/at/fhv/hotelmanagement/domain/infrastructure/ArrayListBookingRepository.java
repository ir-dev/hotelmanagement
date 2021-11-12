package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.BookingNo;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ArrayListBookingRepository implements BookingRepository {
    private ArrayList<Booking> bookings = new ArrayList<>();

    @Override
    public List<Booking> findAll() {
        if (bookings.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(bookings);
    }

    @Override
    public Optional<Booking> findByNo(BookingNo bookingNo) {
        return bookings.stream()
                .filter(booking -> booking.getBookingNo().equals(bookingNo))
                .findFirst();
    }

    @Override
    public void store(Booking booking){
        bookings.add(booking);
    }
}
