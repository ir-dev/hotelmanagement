package at.fhv.hotelmanagement;

import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
@Transactional
@Profile("!test")
public class TestData implements ApplicationRunner {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Category c1 = CategoryFactory.createCategory("Honeymoon Suite DZ", "A honeymoon suite, or a 'romance suite', in a hotel or other places of accommodation denotes a suite with special amenities primarily aimed at couples and newlyweds.", 2);
        Category c2 = CategoryFactory.createCategory("Business Casual EZ", "A casual accommodation for business guests.", 1);
        this.categoryRepository.store(c1);
        this.categoryRepository.store(c2);
        c1.createRoom(new Room(new RoomNumber("120"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("121"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("122"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("123"), RoomState.CLEANING));
        c1.createRoom(new Room(new RoomNumber("124"), RoomState.MAINTENANCE));

        c2.createRoom(new Room(new RoomNumber("220"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("221"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("222"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("223"), RoomState.AVAILABLE));
        Room room224 = new Room(new RoomNumber("224"), RoomState.AVAILABLE);
        c2.createRoom(room224);
        room224.occupied(LocalDate.of(2021,11,19), LocalDate.of(2021,11,22), null);
        room224.occupied(LocalDate.of(2021,11,23), LocalDate.of(2021,11,25), null);

        Organization orgaEmpty = null;
        Organization orga1 = new Organization("FHV", "PROMOCODE-XMAS2021");
        Address ad1 = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Address ad2 = new Address("Musterstr. 123", "12345", "München", String.valueOf(Country.DE));
        Guest g1 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.DIVERSE), "Hüseyin", "Arziman", LocalDate.of(1999,12,24), ad1, "I don't want the housekeeping to disturb us");
        Guest g2 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orga1,String.valueOf(Salutation.MISTER),"Fritz", "Mayer", LocalDate.of(1979,12,24), ad2, "");
        this.guestRepository.store(g1);
        this.guestRepository.store(g2);

        Map<Category, Integer> categoryRooms1 = new HashMap<>();
        categoryRooms1.put(c1, 1);
        categoryRooms1.put(c2, 2);
        Map<Category, Integer> categoryRooms2 = new HashMap<>();
        categoryRooms2.put(c2, 1);
        PaymentInformation paymentInformation1 = new PaymentInformation("Hüseyin Arziman", "1234 5678 9876 5432", "11/22", "123", String.valueOf(PaymentType.CREDITCARD));
        PaymentInformation paymentInformation2 = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));
        Booking bk1 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.of(2021,12,12),
                LocalDate.of(2021,12,24), LocalTime.of(11,30), 4, categoryRooms1, g1.getGuestId(), paymentInformation1);
        Booking bk2 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.of(2021,12,12),
                LocalDate.of(2021,12,24), LocalTime.of(11,30), 1, categoryRooms2, g2.getGuestId(), paymentInformation2);
        this.bookingRepository.store(bk1);
        this.bookingRepository.store(bk2);



//        Stay stay = new Stay(this.stayRepository.nextIdentity(), this.bookingRepository.nextIdentity(), LocalDate.of(2021,11,24),
//                LocalDate.of(2021,11,26), LocalTime.of(12,30), 4, categoryRooms1, g1.getGuestId(), paymentInformation1);
//        stayRepository.store(stay);

    }
}
