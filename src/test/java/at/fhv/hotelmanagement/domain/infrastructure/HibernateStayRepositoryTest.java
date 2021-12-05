package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.model.guest.PaymentType;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.stay.CreateStayException;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import at.fhv.hotelmanagement.domain.model.stay.StayFactory;
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
class HibernateStayRepositoryTest extends AbstractTest {

    @Autowired
    private HibernateStayRepository stayRepository;

    @PersistenceContext
    private EntityManager em;

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

    private static Integer nextDummyCategoryIdentity = 1;
    private static Integer nextDummyGuestIdentity = 1;
    private static Integer nextDummyBookingIdentity = 1;

    private CategoryId nextDummyCategoryIdentity() {
        return new CategoryId((nextDummyCategoryIdentity++).toString());
    }

    private GuestId nextDummyGuestIdentity() {
        return new GuestId((nextDummyGuestIdentity++).toString());
    }

    private BookingNo nextDummyBookingIdentity() {
        return new BookingNo((nextDummyBookingIdentity++).toString());
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

        Category c1 = CategoryFactory.createCategory(nextDummyCategoryIdentity(), "Test EZ", "", 1);
        c1.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("102"), RoomState.AVAILABLE));
        Map<Category, Integer> selectedCategoriesRoomCount = Stream.of(new Object[][] {
                { c1, 2 },
                { CategoryFactory.createCategory(nextDummyCategoryIdentity(), "Test DZ", "", 2), 0 },
                { CategoryFactory.createCategory(nextDummyCategoryIdentity(), "Test MZ", "", 3), 0 },
        }).collect(Collectors.toMap(data -> (Category) data[0], data -> (Integer) data[1]));

        // create entity/entities
        Guest guest = GuestFactory.createGuest(
                nextDummyGuestIdentity(),
                null,
                Salutation.MISTER.toString(),
                "Max",
                "Mustermann",
                getContextLocalDate().minusYears(18L),
                address,
                ""
        );

        Booking booking = BookingFactory.createBooking(
                nextDummyBookingIdentity(),
                getContextLocalDate(),
                getContextLocalDate().plusDays(1L),
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

