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
    void given_staydetails_when_createstayforbooking_then_returnequalsdetails() throws RoomAlreadyExistsException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, price, price);
        Room room = new Room(new RoomNumber("123"), RoomState.AVAILABLE);
        category.createRoom(room);
        selectedCategoriesRoomCount.put(category, 1);
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if(selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }
        GuestId guestId = new GuestId("3");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());

        // when
        Stay stay = new Stay(stayId, bookingNo, arrivalDate, departureDate, LocalTime.now(), numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(stayId, stay.getStayId());
        assertEquals(bookingNo, stay.getBookingNo().orElseThrow());
        assertEquals(StayState.CHECKED_IN, stay.getStayState());
        assertEquals(LocalDateTime.now(), stay.getCheckedInAt());
        assertEquals(Optional.empty(), stay.getCheckedOutAt());
        assertEquals(arrivalDate, stay.getArrivalDate());
        assertEquals(departureDate, stay.getDepartureDate());
        assertEquals(arrivalTime, stay.getArrivalTime());
        assertEquals(numberOfPersons, stay.getNumberOfPersons());
        assertEquals(selectedCategoryNamesRoomCount, stay.getSelectedCategoriesRoomCount());
        assertEquals(guestId, stay.getGuestId());
        assertEquals(paymentInformation, stay.getPaymentInformation());
        assertEquals(Collections.emptySet(), stay.getInvoices());
        assertEquals(1, stay.getNumberOfBookedRooms());
        assertTrue(stay.isCheckedIn());
        assertFalse(stay.isCheckedOut());
        assertFalse(stay.isBilled());
    }

    @Test
    void given_unbilledstay_when_checkout_then_throwsbillingopenexception() throws RoomAlreadyExistsException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, price, price);
        Room room = new Room(new RoomNumber("123"), RoomState.AVAILABLE);
        category.createRoom(room);
        selectedCategoriesRoomCount.put(category, 1);
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if(selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }
        GuestId guestId = new GuestId("3");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());
        Stay stay = new Stay(stayId, bookingNo, arrivalDate, departureDate, LocalTime.now(), numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);

        // when..then
        assertThrows(BillingOpenException.class, stay::checkout);
    }

    @Test
    void given_billedstay_when_checkout_then_returnequalscheckedoutstaydetails() throws RoomAlreadyExistsException, PriceCurrencyMismatchException, GenerateInvoiceException, BillingOpenException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, price, price);
        Room room = new Room(new RoomNumber("123"), RoomState.AVAILABLE);
        category.createRoom(room);
        selectedCategoriesRoomCount.put(category, 1);
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if(selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }
        GuestId guestId = new GuestId("3");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());
        Stay stay = new Stay(stayId, bookingNo, arrivalDate, departureDate, LocalTime.now(), numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);
        stay.composeInvoice(selectedCategoriesRoomCount, Optional.empty());

        // when
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
        assertEquals(selectedCategoryNamesRoomCount, stay.getSelectedCategoriesRoomCount());
        assertEquals(guestId, stay.getGuestId());
        assertEquals(paymentInformation, stay.getPaymentInformation());
        assertEquals(1, stay.getInvoices().size());
        assertEquals(1, stay.getNumberOfBookedRooms());
        assertFalse(stay.isCheckedIn());
        assertTrue(stay.isCheckedOut());
        assertTrue(stay.isBilled());
        assertEquals(Collections.emptyMap(), stay.billableLineItemCounts());
    }

    @Test
    void given_checkedoutstay_when_checkout_then_throwsillegalstateexception() throws RoomAlreadyExistsException, PriceCurrencyMismatchException, GenerateInvoiceException, BillingOpenException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, price, price);
        Room room = new Room(new RoomNumber("123"), RoomState.AVAILABLE);
        category.createRoom(room);
        selectedCategoriesRoomCount.put(category, 1);
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if(selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }
        GuestId guestId = new GuestId("3");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());
        Stay stay = new Stay(stayId, bookingNo, arrivalDate, departureDate, LocalTime.now(), numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);
        stay.composeInvoice(selectedCategoriesRoomCount, Optional.empty());
        stay.checkout();

        // when..then
        assertThrows(IllegalStateException.class, stay::checkout);
    }

    @Test
    void given_partlybilledstay_when_generateinvoiceforinappropriateopenpositionamount_then_throwsgenerateinvoiceexception() throws GenerateInvoiceException, PriceCurrencyMismatchException, RoomAlreadyExistsException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, price, price);
        category.createRoom(new Room(new RoomNumber("123"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("456"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("789"), RoomState.AVAILABLE));
        selectedCategoriesRoomCount.put(category, 3);
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if(selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }
        GuestId guestId = new GuestId("3");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());
        Stay stay = new Stay(stayId, bookingNo, arrivalDate, departureDate, LocalTime.now(), numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);
        Map<Category, Integer> partlyBilled = new HashMap<>();
        partlyBilled.put(category, 1);
        stay.composeInvoice(partlyBilled, Optional.empty());
        Map<Category, Integer> inappropriateCategoryOpenPositionAmount = new HashMap<>();
        inappropriateCategoryOpenPositionAmount.put(category, 3);

        // when..then
        assertThrows(GenerateInvoiceException.class, () -> stay.generateInvoice(inappropriateCategoryOpenPositionAmount, Optional.empty()));
    }

    @Test
    void given_unbilledstay_when_billablelineitemscounts_then_returnsequalsallcategoryroomcounts() throws RoomAlreadyExistsException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, price, price);
        category.createRoom(new Room(new RoomNumber("123"), RoomState.AVAILABLE));
        selectedCategoriesRoomCount.put(category, 1);
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if(selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }
        GuestId guestId = new GuestId("3");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());
        Stay stay = new Stay(stayId, bookingNo, arrivalDate, departureDate, LocalTime.now(), numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);

        // when
        Map<String, Integer> billableLineItemCounts = stay.billableLineItemCounts();

        // then
        assertEquals(selectedCategoryNamesRoomCount, billableLineItemCounts);
    }

    @Test
    void given_billedstay_when_billablelineitemscounts_then_returnsequalsemptybillablelineitems() throws GenerateInvoiceException, PriceCurrencyMismatchException, RoomAlreadyExistsException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("2");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(5);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        Category category = CategoryFactory.createCategory(new CategoryId("10"), "Family Glamping", "Lorem Ipsum", 5, price, price);
        category.createRoom(new Room(new RoomNumber("123"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("456"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("789"), RoomState.AVAILABLE));
        selectedCategoriesRoomCount.put(category, 3);
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if(selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }
        GuestId guestId = new GuestId("3");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", PaymentType.CASH.toString());
        Stay stay = new Stay(stayId, bookingNo, arrivalDate, departureDate, LocalTime.now(), numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);
        Map<Category, Integer> firstInvoicePositions = new HashMap<>();
        firstInvoicePositions.put(category, 1);
        Map<Category, Integer> secondInvoicePositions = new HashMap<>();
        secondInvoicePositions.put(category, 2);
        stay.composeInvoice(firstInvoicePositions, Optional.empty());
        stay.composeInvoice(secondInvoicePositions, Optional.empty());

        // when
        Map<String, Integer> billableLineItemCounts = stay.billableLineItemCounts();

        // then
        assertEquals(Collections.emptyMap(), billableLineItemCounts);
    }
}