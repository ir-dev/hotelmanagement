package at.fhv.hotelmanagement;

import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.BookingStatus;
import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
@Transactional
public class TestData implements ApplicationRunner {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<Room> roomRange1 = new HashSet<>();
        roomRange1.add(new Room(new RoomNumber("120")));
        roomRange1.add(new Room(new RoomNumber("121")));
        roomRange1.add(new Room(new RoomNumber("122")));
        roomRange1.add(new Room(new RoomNumber("123")));
        Set<Room> roomRange2 = new HashSet<>();
        roomRange2.add(new Room(new RoomNumber("150")));
        roomRange2.add(new Room(new RoomNumber("151")));
        Category c1 = new Category("Honeymoon Suite DZ", "A honeymoon suite, or a 'romance suite', in a hotel or other places of accommodation denotes a suite with special amenities primarily aimed at couples and newlyweds.", 2, roomRange1);
        Category c2 = new Category("Business Casual EZ", "A casual accommodation for business guests.", 1, roomRange2);
        categoryRepository.store(c1);
        categoryRepository.store(c2);

        Organization orgaEmpty = null;
        Organization orga1 = new Organization("FHV", "PROMOCODE-XMAS2021");
        Address ad1 = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Address ad2 = new Address("Musterstr. 123", "12345", "München", String.valueOf(Country.DE));
        Guest g1 = new Guest(guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.DIVERSE), "Hüseyin", "Arziman", LocalDate.of(1999,12,24), ad1, "I don't want the housekeeping to disturb us");
        Guest g2 = new Guest(guestRepository.nextIdentity(), orga1,String.valueOf(Salutation.MISTER),"Fritz", "Mayer", LocalDate.of(1979,12,24), ad2, "");
        guestRepository.store(g1);
        guestRepository.store(g2);

        Map<String, Integer> categoryRooms1 = new HashMap<>();
        categoryRooms1.put(c1.getName(), 1);
        categoryRooms1.put(c2.getName(), 2);
        Map<String, Integer> categoryRooms2 = new HashMap<>();
        categoryRooms2.put(c2.getName(), 1);
        PaymentInformation paymentInformation1 = new PaymentInformation("Hüseyin Arziman", "1234 5678 9876 5432", "11/22", "123", String.valueOf(PaymentType.CREDITCARD));
        PaymentInformation paymentInformation2 = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));
        Booking bk1 = new Booking(bookingRepository.nextIdentity(), LocalDate.of(2021,12,12),
                LocalDate.of(2021,12,24), LocalTime.of(11,30), 4, categoryRooms1, g1.getGuestId(), paymentInformation1);
        Booking bk2 = new Booking(bookingRepository.nextIdentity(), LocalDate.of(2021,12,12),
                LocalDate.of(2021,12,24), LocalTime.of(11,30), 1, categoryRooms2, g2.getGuestId(), paymentInformation2);
        bookingRepository.store(bk1);
        bookingRepository.store(bk2);



    }
}
