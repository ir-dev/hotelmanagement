package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class HibernateBookingRepositoryTest extends AbstractTest {

    @Autowired
    private HibernateBookingRepository bookingRepository;

    @Autowired
    private HibernateGuestRepository guestRepository;

    @Autowired
    private HibernateCategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void given_3bookingsinrepository_when_findallbookings_then_returnequalsbookings() throws CreateGuestException, CreateBookingException, AlreadyExistsException {
        // given
        List<Booking> bookingsExpected = Arrays.asList(
                createBookingDummy(),
                createBookingDummy(),
                createBookingDummy()
        );
        bookingsExpected.forEach(booking -> {
            this.bookingRepository.store(booking);
        });
        this.em.flush();

        // when
        List<Booking> bookingsActual = this.bookingRepository.findAll();

        // then
        assertEquals(bookingsExpected.size(), bookingsActual.size());
        for (Booking b : bookingsActual) {
            assertTrue(bookingsExpected.contains(b));
        }
    }

    @Test
    void given_emptyrepository_when_findallcategories_then_returnemptycategories() {
        assertTrue(this.categoryRepository.findAll().isEmpty());
    }

    @Test
    void given_booking_when_addbookingtorepository_then_returnequalsbooking() throws CreateGuestException, CreateBookingException, AlreadyExistsException {
        // given
        Booking bookingExcepted = createBookingDummy();

        // when
        this.bookingRepository.store(bookingExcepted);
        this.em.flush();
        Booking bookingActual = this.bookingRepository.findByNo(bookingExcepted.getBookingNo()).get();

        // then
        assertEquals(bookingExcepted, bookingActual);
        assertEquals(bookingExcepted.getBookingNo(), bookingActual.getBookingNo());
    }

    private Booking createBookingDummy() throws CreateGuestException, CreateBookingException, AlreadyExistsException {
        // create value objects for entities
        Address address = new Address(
                "Musterstr. 123",
                "12345",
                "Musterstadt",
                Country.AT.toString()
        );
        PaymentInformation paymentInformation = new PaymentInformation(
                "Hans Wurst",
                "1234 5678 8765 4321",
                "12/23",
                "123",
                PaymentType.INVOICE.toString()
        );

        Category c1 = CategoryFactory.createCategory(this.categoryRepository.nextIdentity(), "Test EZ", "", 1);
        c1.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("102"), RoomState.AVAILABLE));
        Map<Category, Integer> selectedCategoriesRoomCount = Stream.of(new Object[][] {
                { c1, 2 },
                { CategoryFactory.createCategory(this.categoryRepository.nextIdentity(), "Test DZ", "", 2), 0 },
                { CategoryFactory.createCategory(this.categoryRepository.nextIdentity(), "Test MZ", "", 3), 0 },
        }).collect(Collectors.toMap(data -> (Category) data[0], data -> (Integer) data[1]));

        // create entity/entities
        Guest guest = GuestFactory.createGuest(
                this.guestRepository.nextIdentity(),
                null,
                Salutation.MISTER.toString(),
                "Max",
                "Mustermann",
                getContextLocalDate().minusYears(18L),
                address,
                ""
        );

        return BookingFactory.createBooking(
                this.bookingRepository.nextIdentity(),
                getContextLocalDate(),
                getContextLocalDate().plusDays(1L),
                null,
                2,
                selectedCategoriesRoomCount,
                guest.getGuestId(),
                paymentInformation
        );
    }
}

