package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.*;
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
class HibernateStayRepositoryTest {

    private static final Instant FIXED_CLOCK_INSTANT = Instant.parse("2020-01-01T00:00:00Z");
    private static final ZoneId FIXED_CLOCK_ZONE_ID = ZoneId.of("UTC").normalized();

    @Autowired
    HibernateStayRepository stayRepository;

    @Autowired
    private HibernateBookingRepository bookingRepository;

    @Autowired
    private HibernateGuestRepository guestRepository;

    @Autowired
    private HibernateCategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager em;

    private MockedStatic<Clock> mockedStaticClock;

    @BeforeEach
    void tearUp() {
        // setup time context
        // return a fixed clock for systemDefaultZone() which is used by classes like LocalDateTime, LocalDate and LocalTime
        Clock fixedClock = Clock.fixed(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
        this.mockedStaticClock = Mockito.mockStatic(Clock.class);
        this.mockedStaticClock
                .when(Clock::systemDefaultZone)
                .thenReturn(fixedClock);
    }

    @AfterEach
    void tearDown() {
        // reset time context
        this.mockedStaticClock.close();
    }

    private LocalDateTime getTestLocalDateTime() {
        return LocalDateTime.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }

    private LocalDate getTestLocalDate() {
        return LocalDate.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }

    private LocalTime getTestLocalTime() {
        return LocalTime.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }

    @Test
    void given_3staysinrepository_when_findallstays_then_returnequalsstays() throws CreateGuestException, CreateBookingException, CreateStayException, AlreadyExistsException {
        // given
        List<Stay> staysExpected = Arrays.asList(
                createStayDummy(),
                createStayDummy(),
                createStayDummy()
        );
        staysExpected.forEach(stay -> {
            this.stayRepository.store(stay);
        });
        this.em.flush();

        // when
        List<Stay> staysActual = this.stayRepository.findAll();

        // then
        assertEquals(staysExpected.size(), staysActual.size());
        for (Stay s : staysActual) {
            assertTrue(staysExpected.contains(s));
        }
    }

    @Test
    void given_emptyrepository_when_findallstays_then_returnemptystays() {
        assertTrue(this.stayRepository.findAll().isEmpty());
    }

    @Test
    void given_stay_when_addstaytorepository_then_returnequalsstay() throws CreateGuestException, CreateBookingException, CreateStayException, AlreadyExistsException {
        // given
        Stay stayExcepted = createStayDummy();

        // when
        this.stayRepository.store(stayExcepted);
        this.em.flush();
        Stay stayActual = this.stayRepository.findById(stayExcepted.getStayId()).get();

        // then
        assertEquals(stayExcepted, stayActual);
        assertEquals(stayExcepted.getStayId(), stayActual.getStayId());
    }

    private Stay createStayDummy() throws CreateGuestException, CreateBookingException, CreateStayException, AlreadyExistsException {
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
                getTestLocalDate().minusYears(18L),
                address,
                ""
        );

        Booking booking = BookingFactory.createBooking(
                this.bookingRepository.nextIdentity(),
                getTestLocalDate(),
                getTestLocalDate().plusDays(1L),
                null,
                2,
                selectedCategoriesRoomCount,
                guest.getGuestId(),
                paymentInformation
        );
        return StayFactory.createStayForBooking(
                this.stayRepository.nextIdentity(),
                booking,
                booking.getBookingNo(),
                booking.getArrivalDate(),
                booking.getDepartureDate(),
                booking.getNumberOfPersons(),
                selectedCategoriesRoomCount,
                guest.getGuestId(),
                paymentInformation
        );
    }
}

