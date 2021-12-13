package at.fhv.hotelmanagement;

import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.model.guest.PaymentType;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.category.CategoryService;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import at.fhv.hotelmanagement.domain.model.stay.StayFactory;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private StayRepository stayRepository;

    @Autowired
    CategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Currency curr = Currency.getInstance("EUR");

        Category c1 = CategoryFactory.createCategory(this.categoryRepository.nextIdentity(),"Honeymoon Suite DZ", "A honeymoon suite, or a 'romance suite' aimed at couples and newlyweds.", 2, Price.of(BigDecimal.valueOf(120L), curr), Price.of(BigDecimal.valueOf(150L), curr));
        Category c2 = CategoryFactory.createCategory(this.categoryRepository.nextIdentity(),"Business Casual EZ", "A casual accommodation for business guests.", 1, Price.of(BigDecimal.valueOf(90L), curr), Price.of(BigDecimal.valueOf(120L), curr));
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
      
        room224.occupied(LocalDate.of(2021,11,19), LocalDate.of(2021,11,22), new StayId("0"));
        room224.occupied(LocalDate.of(2021,11,23), LocalDate.of(2021,11,25), new StayId("0"));


        Organization orgaEmpty = null;
        Organization orga1 = new Organization("FHV", "PROMOCODE-XMAS2021");
        Address ad1 = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Address ad2 = new Address("Musterstr. 123", "12345", "München", String.valueOf(Country.DE));
        Guest g1 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.DIVERSE), "Franz", "Beckenbauer", LocalDate.of(1999,12,24), ad1, "I don't want the housekeeping to disturb us");
        Guest g2 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orga1,String.valueOf(Salutation.MISTER),"Fritz", "Mayer", LocalDate.of(1979,12,24), ad2, "");
        this.guestRepository.store(g1);
        this.guestRepository.store(g2);

        Map<Category, Integer> categoryRooms1 = new HashMap<>();
        categoryRooms1.put(c1, 1);
        categoryRooms1.put(c2, 2);
        Map<Category, Integer> categoryRooms2 = new HashMap<>();
        categoryRooms2.put(c2, 1);
        PaymentInformation paymentInformation1 = new PaymentInformation("Franz Beckenbauer", "1234 5678 9876 5432", "11/22", "123", String.valueOf(PaymentType.CREDITCARD));
        PaymentInformation paymentInformation2 = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));
      
        Booking bk1 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.of(2021,12,14),
                LocalDate.of(2021,12,24), LocalTime.of(11,30), 4, categoryRooms1, g1.getGuestId(), paymentInformation1);

        Booking bk2 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.now(),
                LocalDate.now().plusDays(5), null, 1, categoryRooms2, g2.getGuestId(), paymentInformation2);

        this.bookingRepository.store(bk1);
        this.bookingRepository.store(bk2);

        Map<String, Integer> selectedCategoryNamesRoomCount = bk2.getSelectedCategoriesRoomCount();
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        for (Map.Entry<String, Integer> selectedCategoryNameRoomCount : selectedCategoryNamesRoomCount.entrySet()) {
            selectedCategoriesRoomCount.put(this.categoryRepository.findByName(selectedCategoryNameRoomCount.getKey()).orElseThrow(), selectedCategoryNameRoomCount.getValue());
        }


        Stay stay = StayFactory.createStayForBooking(
                this.stayRepository.nextIdentity(),
                bk2,
                bk2.getBookingNo(),
                bk2.getArrivalDate(),
                bk2.getDepartureDate(),
                bk2.getNumberOfPersons(),
                selectedCategoriesRoomCount,
                bk2.getGuestId(),
                bk2.getPaymentInformation()
        );
        this.stayRepository.store(stay);

        this.categoryService.autoAssignRooms(
                selectedCategoriesRoomCount,
                bk2.getArrivalDate(),
                bk2.getDepartureDate(),
                stay.getStayId()
        );

        bk2.close();
    }
}
