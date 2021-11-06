package at.fhv.hotelmanagement;

import at.fhv.hotelmanagement.domain.model.Address;
import at.fhv.hotelmanagement.domain.model.Booking;
import at.fhv.hotelmanagement.domain.model.Guest;
import at.fhv.hotelmanagement.domain.model.enums.BookingStatus;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
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
    @Autowired
    private GuestRepository guestRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Address ad1 = new Address("Baumgarten", "26850", "Dornbirn", "Austria");

        Booking bk1 = new Booking("5678","1", LocalDate.of(2021,12,12),LocalDate.of(2021,12,24),
                BookingStatus.Pending, 4, LocalTime.of(11,30));

        Booking bk2 = new Booking("5679","2", LocalDate.of(2021,12,12),LocalDate.of(2021,12,24),
                BookingStatus.Pending, 4, LocalTime.of(11,30));

        Guest g1 = new Guest("1", "Hüseyin", "Arziman", LocalDate.of(1999,12,24), ad1);

        Guest g2 = new Guest("2", "Fritz", "Mayer", LocalDate.of(1979,12,24), ad1);

        bookingRepository.save(bk1);
        bookingRepository.save(bk2);

        guestRepository.save(g1);
        guestRepository.save(g2);

    }
}
