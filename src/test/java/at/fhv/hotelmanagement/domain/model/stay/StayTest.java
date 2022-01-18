package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.converters.CategoryConverter;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StayTest extends AbstractTest {
    @Test
    void given_staydetails_when_createstayforbooking_then_returnequalsdetails() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = LocalTime.now();
        Integer numberOfPersons = 5;
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, p, p);
        Room room = new Room(new RoomNumber("123"), RoomState.AVAILABLE);
        category.createRoom(room);
        selectedCategoriesRoomCount.put(category, 1);

        GuestId guestId = new GuestId("3");

        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());
        Booking booking = BookingFactory.createBooking(
                bookingNo,
                arrivalDate,
                departureDate,
                arrivalTime,
                numberOfPersons,
                selectedCategoriesRoomCount,
                guestId,
                paymentInformation
        );

        // when
        Stay stay = StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(bookingNo, stay.getBookingNo().orElseThrow());
        assertEquals(StayState.CHECKED_IN, stay.getStayState());
        assertEquals(LocalDateTime.now(), stay.getCheckedInAt());
        assertEquals(Optional.empty(), stay.getCheckedOutAt());
        assertEquals(arrivalDate, stay.getArrivalDate());
        assertEquals(departureDate, stay.getDepartureDate());
        assertEquals(arrivalTime, stay.getArrivalTime());
        assertEquals(numberOfPersons, stay.getNumberOfPersons());
        assertEquals(CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount), stay.getSelectedCategoriesRoomCount());
        assertEquals(guestId, stay.getGuestId());
        assertEquals(paymentInformation, stay.getPaymentInformation());
        assertEquals(Collections.emptySet(), stay.getInvoices());

        assertEquals(1, stay.getNumberOfBookedRooms());

        assertTrue(stay.isCheckedIn());
        assertFalse(stay.isBilled());
        assertThrows(BillingOpenException.class, stay::checkout);
    }

    @Test
    void given_checkedinstay_when_billedandcheckedout_then_returnequalsdetails() throws CreateBookingException, CreateStayException, PriceCurrencyMismatchException, BillingOpenException, RoomAlreadyExistsException, CreateGuestException, GenerateInvoiceException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = LocalTime.now();
        Integer numberOfPersons = 5;
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, p, p);
        Room room = new Room(new RoomNumber("123"), RoomState.AVAILABLE);
        category.createRoom(room);
        selectedCategoriesRoomCount.put(category, 1);

        GuestId guestId = new GuestId("3");
        Organization organization = new Organization("FHV", BigDecimal.valueOf(0.25));
        Address address = new Address("Straße", "6971", "Hard", String.valueOf(Country.AT));
        Guest guest = GuestFactory.createGuest(guestId, organization, String.valueOf(Salutation.MR), "Lukas", "Kaufmann", LocalDate.now().minusYears(18L), address, "");
        InvoiceRecipient invoiceRecipient = new InvoiceRecipient(
                guest.getFirstName(),
                guest.getLastName(),
                guest.getAddress()
        );

        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());
        Booking booking = BookingFactory.createBooking(
                bookingNo,
                arrivalDate,
                departureDate,
                arrivalTime,
                numberOfPersons,
                selectedCategoriesRoomCount,
                guestId,
                paymentInformation
        );
        Stay stay = StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category, 1);

        // when
        InvoiceNo invoiceNo = stay.finalizeInvoice("1",selectedLineItemProductsCount, guest.getOrganizationDiscountRate(), invoiceRecipient);
        stay.checkout();

        // then
        assertEquals(bookingNo, stay.getBookingNo().orElseThrow());
        assertEquals(StayState.CHECKED_OUT, stay.getStayState());
        assertEquals(LocalDateTime.now(), stay.getCheckedInAt());
        assertEquals(LocalDateTime.now(), stay.getCheckedOutAt().orElseThrow());
        assertEquals(arrivalDate, stay.getArrivalDate());
        assertEquals(departureDate, stay.getDepartureDate());
        assertEquals(arrivalTime, stay.getArrivalTime());
        assertEquals(numberOfPersons, stay.getNumberOfPersons());
        assertEquals(CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount), stay.getSelectedCategoriesRoomCount());
        assertEquals(guestId, stay.getGuestId());
        assertEquals(paymentInformation, stay.getPaymentInformation());
        assertEquals(1, stay.getInvoices().size());
        assertEquals(invoiceNo.getNo(), "2020011");

        Invoice finalizedInvoice = stay.getInvoices().stream().findFirst().orElseThrow();
        assertEquals(invoiceRecipient, finalizedInvoice.getInvoiceRecipient());


        assertEquals(1, stay.getNumberOfBookedRooms());

        assertFalse(stay.isCheckedIn());
        assertTrue(stay.isBilled());
        assertThrows(IllegalStateException.class, stay::checkout);
    }
}