package at.fhv.hotelmanagement;

import at.fhv.hotelmanagement.domain.model.Address;
import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.BookingStatus;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TestData implements ApplicationRunner {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Address ad1 = new Address("Baumgarten", 2, "6850", "Dornbirn", "Austria");

        Booking bk1 = new Booking("5678","1", LocalDate.of(2021,12,12),LocalDate.of(2021,12,24),
                BookingStatus.Pending, 4, LocalTime.of(11,30), ad1);

        Booking bk2 = new Booking("5679","1", LocalDate.of(2021,12,12),LocalDate.of(2021,12,24),
                BookingStatus.Pending, 4, LocalTime.of(11,30), ad1);

        bookingRepository.addBooking(bk1);
        bookingRepository.addBooking(bk2);

    }
}
