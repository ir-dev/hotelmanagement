package at.fhv.hotelmanagement.infrastructure;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.category.RoomAlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.model.guest.PaymentType;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.stay.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
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
    void given_3staysinrepository_when_findallstays_then_returnequalsstays() throws CreateGuestException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, PriceCurrencyMismatchException, GenerateInvoiceException {
        // given
        List<Stay> staysExpected = Arrays.asList(
                createStayDummy(),
                createStayDummy(),
                createStayDummy()
        );
        staysExpected.forEach(stay -> this.stayRepository.store(stay));
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
    void given_stay_when_addstaytorepository_then_returnequalsstay() throws CreateGuestException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, PriceCurrencyMismatchException, GenerateInvoiceException {
        // given
        Stay stayExcepted = createStayDummy();

        // when
        this.stayRepository.store(stayExcepted);
        this.em.flush();
        Stay stayActual = this.stayRepository.findById(stayExcepted.getStayId()).orElseThrow();

        // then
        assertEquals(stayExcepted, stayActual);
        assertEquals(stayExcepted.getStayId(), stayActual.getStayId());
    }

    @Test
    void given_staywithinvoiceinrepository_when_findstaybyinvoiceno_then_returnequalsstay() throws CreateGuestException, CreateBookingException, CreateStayException, PriceCurrencyMismatchException, RoomAlreadyExistsException, GenerateInvoiceException {
        // given
        Stay stayExpected = createStayDummy();
        this.stayRepository.store(stayExpected);
        this.em.flush();

        // when
        Stay stayActual = this.stayRepository.findByInvoiceNo(stayExpected.getInvoices().stream().findFirst().orElseThrow().getInvoiceNo()).orElseThrow();

        // then
        assertEquals(stayExpected, stayActual);
        assertEquals(stayExpected.getStayId(), stayActual.getStayId());
    }

    @Test
    void given_staywithinvoiceinrepository_when_findinvoicebyinvoiceno_then_returnequalsinvoice() throws CreateGuestException, CreateBookingException, CreateStayException, PriceCurrencyMismatchException, RoomAlreadyExistsException, GenerateInvoiceException {
        // given
        Stay stay = createStayDummy();
        Invoice invoiceExcepted = stay.getInvoices().stream().findFirst().orElseThrow();
        this.stayRepository.store(stay);
        this.em.flush();

        // when
        Invoice invoiceActual = this.stayRepository.findInvoiceByInvoiceNo(invoiceExcepted.getInvoiceNo()).orElseThrow();

        // then
        assertEquals(invoiceExcepted, invoiceActual);
        assertEquals(invoiceExcepted.getInvoiceNo(), invoiceActual.getInvoiceNo());
    }

    @Test
    void given_invoicerecipient_when_addinvoicerecipienttorepository_then_returnequalsinvoicerecipient() throws CreateGuestException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, PriceCurrencyMismatchException, GenerateInvoiceException {
        // given
        Stay stayExcepted = createStayDummy();
        Invoice invoiceExcepted = stayExcepted.getInvoices().stream().findFirst().orElseThrow();
        InvoiceRecipient invoiceRecipientExpected = invoiceExcepted.getInvoiceRecipient();

        // when
        this.stayRepository.save(invoiceRecipientExpected);
        this.em.flush();
        InvoiceRecipient invoiceRecipientActual = this.stayRepository.findRecipientById(invoiceRecipientExpected.getId()).orElseThrow();

        // then
        assertEquals(invoiceRecipientExpected, invoiceRecipientActual);
        assertEquals(invoiceRecipientExpected.getId(), invoiceRecipientActual.getId());
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

    private Stay createStayDummy() throws CreateGuestException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, PriceCurrencyMismatchException, GenerateInvoiceException {
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

        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category c1 = CategoryFactory.createCategory(nextDummyCategoryIdentity(), "Test EZ", "", 1, p, p);
        c1.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));
        c1.createRoom(new Room(new RoomNumber("102"), RoomState.AVAILABLE));
        Map<Category, Integer> selectedCategoriesRoomCount = Stream.of(new Object[][] {
                { c1, 2 },
                { CategoryFactory.createCategory(nextDummyCategoryIdentity(), "Test DZ", "", 2, p, p), 0 },
                { CategoryFactory.createCategory(nextDummyCategoryIdentity(), "Test MZ", "", 3, p, p), 0 },
        }).collect(Collectors.toMap(data -> (Category) data[0], data -> (Integer) data[1]));

        // create entity/entities
        Guest guest = GuestFactory.createGuest(
                nextDummyGuestIdentity(),
                null,
                Salutation.MR.toString(),
                "Max",
                "Mustermann",
                getContextLocalDate().minusYears(18L),
                address,
                ""
        );

        InvoiceRecipient invoiceRecipient = new InvoiceRecipient(
                guest.getFirstName(),
                guest.getLastName(),
                guest.getAddress()
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

        Stay stay = StayFactory.createStayForBooking(
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

        stay.finalizeInvoice(this.stayRepository.nextInvoiceSeq(), selectedCategoriesRoomCount, guest.getOrganizationDiscountRate(), invoiceRecipient);

        return stay;
    }
}

