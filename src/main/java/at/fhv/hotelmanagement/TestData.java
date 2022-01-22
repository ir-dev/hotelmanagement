package at.fhv.hotelmanagement;

import at.fhv.hotelmanagement.application.impl.EntityNotFoundException;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.application.converters.CategoryConverter;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.model.stay.InvoiceRecipient;
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
@Profile("dev")
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
    private CategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Currency curr = Currency.getInstance("EUR");

        Category c1 = CategoryFactory.createCategory(this.categoryRepository.nextIdentity(),"Double Room Economy", "Fully air-conditioned, non-smoking, room safe, SAT-TV + Sky, telephone, WLAN", 2, Price.of(BigDecimal.valueOf(120L), curr), Price.of(BigDecimal.valueOf(150L), curr));
        Category c2 = CategoryFactory.createCategory(this.categoryRepository.nextIdentity(),"Single Room Business Casual", "Fully air-conditioned, carpeted floor, room safe, WLAN", 1, Price.of(BigDecimal.valueOf(90L), curr), Price.of(BigDecimal.valueOf(120L), curr));
        this.categoryRepository.store(c1);
        this.categoryRepository.store(c2);
        c1.createRoom(new Room(new RoomNumber("120"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("121"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("122"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("123"), RoomState.CLEANING));
        c1.createRoom(new Room(new RoomNumber("124"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("125"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("126"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("127"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("128"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("129"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("130"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("131"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("132"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("133"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("134"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("135"), RoomState.AVAILABLE));

        c2.createRoom(new Room(new RoomNumber("220"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("221"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("222"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("223"), RoomState.AVAILABLE));
        Room room224 = new Room(new RoomNumber("224"), RoomState.AVAILABLE);
        c2.createRoom(room224);
        c2.createRoom(new Room(new RoomNumber("225"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("226"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("227"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("228"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("229"), RoomState.AVAILABLE));
        c2.createRoom(new Room(new RoomNumber("230"), RoomState.AVAILABLE));

        room224.occupied(LocalDate.of(2021,11,19), LocalDate.of(2021,11,22), new StayId("0"));
        room224.occupied(LocalDate.of(2021,11,23), LocalDate.of(2021,11,25), new StayId("0"));


        Organization orgaEmpty = null;
        Organization orga1 = new Organization("FHV", BigDecimal.valueOf(0.25));

        Address ad1 = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Address ad2 = new Address("Blumenstrasse", "12345", "Blumenhausen", String.valueOf(Country.DE));
        Address ad3 = new Address("Grünstrasse 12", "6850", "Dornbirn", String.valueOf(Country.AT));
        Address ad4 = new Address("Blaustrasse", "8080", "Porthausen", String.valueOf(Country.DE));
        Address ad5 = new Address("Rotstrasse", "7070", "Musterhausen", String.valueOf(Country.DE));
        Address ad6 = new Address("Orangestrasse", "8080", "Porthausen", String.valueOf(Country.DE));
        Address ad7 = new Address("Weißstrasse", "7070", "Musterhausen", String.valueOf(Country.DE));
        Address ad8 = new Address("Blaustrasse", "1234", "München", String.valueOf(Country.DE));

        Guest g1 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orga1, String.valueOf(Salutation.MR), "Franz", "Beckenbauer", LocalDate.of(1999,12,24), ad1, "I don't want the housekeeping to disturb us");
        Guest g2 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orga1, String.valueOf(Salutation.MR),"Fritz", "Mayer", LocalDate.of(1979,12,24), ad2, "");
        Guest g3 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.MR), "Andreas", "Müller", LocalDate.of(1979,12,18), ad3, "Red slippers please");
        Guest g4 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.MS),"Maria", "Schelling", LocalDate.of(1965,4,11), ad4, "");
        Guest g5 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.MR),"Norbert", "Winkler", LocalDate.of(1975,6,21), ad5, "");
        Guest g6 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.MR),"Robert", "Braun", LocalDate.of(1995,2,27), ad6, "");
        Guest g7 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.MR),"Sigmund", "Freud", LocalDate.of(1987,5,23), ad7, "Not the real Sigmund Freud");
        Guest g8 = GuestFactory.createGuest(this.guestRepository.nextIdentity(), orgaEmpty, String.valueOf(Salutation.MR),"Günther", "Jauch", LocalDate.of(1956,7,13), ad8, "");

        this.guestRepository.store(g1);
        this.guestRepository.store(g2);
        this.guestRepository.store(g3);
        this.guestRepository.store(g4);
        this.guestRepository.store(g5);
        this.guestRepository.store(g6);
        this.guestRepository.store(g7);
        this.guestRepository.store(g8);

        Map<Category, Integer> categoryRooms1 = new HashMap<>();
        categoryRooms1.put(c1, 1);
        categoryRooms1.put(c2, 2);
        Map<Category, Integer> categoryRooms2 = new HashMap<>();
        categoryRooms2.put(c1, 1);
        categoryRooms2.put(c2, 2);
        Map<Category, Integer> categoryRooms3 = new HashMap<>();
        categoryRooms3.put(c1, 1);
        categoryRooms3.put(c2, 1);
        Map<Category, Integer> categoryRooms4 = new HashMap<>();
        categoryRooms4.put(c1, 1);

        PaymentInformation paymentInformation1 = new PaymentInformation("Franz Beckenbauer", "1234 5678 9876 5432", "11/22", "123", String.valueOf(PaymentType.CREDITCARD));
        PaymentInformation paymentInformation2 = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "243", String.valueOf(PaymentType.INVOICE));
        PaymentInformation paymentInformation3 = new PaymentInformation("Andreas Müller", "8643 6743 5432 6553", "06/12", "543", String.valueOf(PaymentType.INVOICE));
        PaymentInformation paymentInformation4 = new PaymentInformation("Gerhard Schelling", "6535 5424 6543 5436", "09/18", "842", String.valueOf(PaymentType.CREDITCARD));
        PaymentInformation paymentInformation5 = new PaymentInformation("Norbert Winkler", "4531 5431 3513 3341", "01/28", "942", String.valueOf(PaymentType.CASH));
        PaymentInformation paymentInformation6 = new PaymentInformation("Robert Braun", "4332 5462 3435 4542", "02/22", "325", String.valueOf(PaymentType.CASH));
        PaymentInformation paymentInformation7 = new PaymentInformation("Sigmund Freud", "3254 2353 2353 2431", "01/13", "531", String.valueOf(PaymentType.INVOICE));
        PaymentInformation paymentInformation8 = new PaymentInformation("Günther Jauch", "2341 2324 5542 3356", "04/18", "248", String.valueOf(PaymentType.INVOICE));


        Booking bk1 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.now(),
                LocalDate.now().plusDays(5), LocalTime.now(), 3, categoryRooms1, g1.getGuestId(), paymentInformation1);

        Booking bk2 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3), null, 3, categoryRooms2, g2.getGuestId(), paymentInformation2);

        Booking bk3 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(4), null, 3, categoryRooms3, g3.getGuestId(), paymentInformation3);

        Booking bk4 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.now(),
                LocalDate.now().plusDays(4), null, 3, categoryRooms3, g4.getGuestId(), paymentInformation4);

        Booking bk5 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.now(),
                LocalDate.now().plusDays(6), null, 3, categoryRooms1, g5.getGuestId(), paymentInformation5);

        Booking bk6 = BookingFactory.createBooking(this.bookingRepository.nextIdentity(), LocalDate.now(),
                LocalDate.now().plusDays(6), null, 1, categoryRooms4, g7.getGuestId(), paymentInformation7);

        this.bookingRepository.store(bk1);
        this.bookingRepository.store(bk2);
        this.bookingRepository.store(bk3);
        this.bookingRepository.store(bk4);
        this.bookingRepository.store(bk5);
        this.bookingRepository.store(bk6);

        Map<Category, Integer> selectedCategoriesRoomCount1 = CategoryConverter.convertToSelectedCategoriesRoomCount(bk2.getSelectedCategoriesRoomCount());
        Stay stay1 = StayFactory.createStayForBooking(
                this.stayRepository.nextIdentity(),
                bk2,
                bk2.getBookingNo(),
                LocalDate.now(),
                bk2.getDepartureDate(),
                bk2.getNumberOfPersons(),
                selectedCategoriesRoomCount1,
                bk2.getGuestId(),
                bk2.getPaymentInformation()
        );

        Map<Category, Integer> selectedCategoriesRoomCount2 = CategoryConverter.convertToSelectedCategoriesRoomCount(bk3.getSelectedCategoriesRoomCount());
        Stay stay2 = StayFactory.createStayForBooking(
                this.stayRepository.nextIdentity(),
                bk3,
                bk3.getBookingNo(),
                LocalDate.now(),
                bk3.getDepartureDate(),
                bk3.getNumberOfPersons(),
                selectedCategoriesRoomCount2,
                bk3.getGuestId(),
                bk3.getPaymentInformation()
        );

        Map<Category, Integer> selectedCategoriesRoomCount3 = CategoryConverter.convertToSelectedCategoriesRoomCount(bk4.getSelectedCategoriesRoomCount());
        Stay stay3 = StayFactory.createStayForBooking(
                this.stayRepository.nextIdentity(),
                bk4,
                bk4.getBookingNo(),
                bk4.getArrivalDate(),
                bk4.getDepartureDate(),
                bk4.getNumberOfPersons(),
                selectedCategoriesRoomCount3,
                bk4.getGuestId(),
                bk4.getPaymentInformation()
        );


        Stay stay4 = StayFactory.createStayForWalkIn(
                this.stayRepository.nextIdentity(),
                LocalDate.now(),
                LocalDate.now().plusDays(4),
                3,
                categoryRooms1,
                g6.getGuestId(),
                paymentInformation6
        );

        Stay stay5 = StayFactory.createStayForWalkIn(
                this.stayRepository.nextIdentity(),
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                2,
                categoryRooms4,
                g8.getGuestId(),
                paymentInformation8
        );



        this.stayRepository.store(stay1);
        this.stayRepository.store(stay2);
        this.stayRepository.store(stay3);
        this.stayRepository.store(stay4);
        this.stayRepository.store(stay5);

        this.categoryService.autoAssignRooms(
                selectedCategoriesRoomCount1,
                stay1.getArrivalDate(),
                stay1.getDepartureDate(),
                stay1.getStayId()
        );

        this.categoryService.autoAssignRooms(
                selectedCategoriesRoomCount2,
                stay2.getArrivalDate(),
                stay2.getDepartureDate(),
                stay2.getStayId()
        );

        this.categoryService.autoAssignRooms(
                selectedCategoriesRoomCount3,
                stay3.getArrivalDate(),
                stay3.getDepartureDate(),
                stay3.getStayId()
        );

        this.categoryService.autoAssignRooms(
                categoryRooms1,
                stay4.getArrivalDate(),
                stay4.getDepartureDate(),
                stay4.getStayId()
        );

        this.categoryService.autoAssignRooms(
                categoryRooms4,
                stay5.getArrivalDate(),
                stay5.getDepartureDate(),
                stay5.getStayId()
        );

        bk2.close();
        bk3.close();
        bk4.close();

        InvoiceRecipient invoiceRecipient = new InvoiceRecipient("Andreas", "Müller", ad3);
        this.stayRepository.storeRecipient(invoiceRecipient);
        stay2.finalizeInvoice(this.stayRepository.nextInvoiceSeq(), selectedCategoriesRoomCount2, g3.getOrganizationDiscountRate(), invoiceRecipient);
        stay2.checkout();
        Set<Category> stayCategories;
        try {
            stayCategories = CategoryConverter.convertToSelectedCategoriesRoomCount(stay2.getSelectedCategoriesRoomCount()).keySet();
        } catch (EntityNotFoundException e) {
            throw new NoSuchElementException(e.getMessage());
        }
        this.categoryService.releaseRooms(
                this.categoryRepository.findRoomNumbersByStayId(stay2.getStayId()),
                stayCategories,
                stay2.getStayId()
        );
    }
}
