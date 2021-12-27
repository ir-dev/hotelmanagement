package at.fhv.hotelmanagement.domain.model.booking;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookingFactoryTest extends AbstractTest {
    @Test
    void given_bookingdetails_when_createbookingfromfactory_then_detailsequals() throws CreateBookingException, RoomAlreadyExistsException {
        // given
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 4;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Honeymoon Suite DZ", "", 2, price, price);
        category.createRoom(new Room(new RoomNumber("1"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("2"), RoomState.AVAILABLE));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        // when
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(bookingNo, booking.getBookingNo());
        assertEquals(arrivalDate, booking.getArrivalDate());
        assertEquals(departureDate, booking.getDepartureDate());
        assertEquals(arrivalTime, booking.getArrivalTime());
        assertEquals(numberOfPersons, booking.getNumberOfPersons());
        assertEquals(Optional.of(2), Optional.ofNullable(booking.getNumberOfBookedRooms()));
        assertEquals(guestId, booking.getGuestId());
        assertEquals(paymentInformation, booking.getPaymentInformation());
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
        }
        assertEquals(selectedCategoryNamesRoomCount, booking.getSelectedCategoriesRoomCount());

    }

    @Test
    void given_bookingwithinvalidvalidationconstraint_when_createbookingfromfactory_then_throwscreatebookingexception() throws RoomAlreadyExistsException {
        // given
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1);
        LocalDate arrivalDateMinus1d = arrivalDate.minusDays(1L);
        LocalDate arrivalDatePlus1d = arrivalDate.plusDays(1L);
        LocalDate departureDatePlus1d = arrivalDate.plusDays(1L);
        LocalDate departureDatePlus2d = arrivalDate.plusDays(2L);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 4;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Honeymoon Suite DZ", "", 2, price, price);
        category.createRoom(new Room(new RoomNumber("1"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("2"), RoomState.AVAILABLE));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        // when..then
        //ArrivalDate is today or in the future
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDateMinus1d, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDatePlus1d, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //DepartureDate must be after ArrivalDate (at least one day)
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate, arrivalDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDatePlus1d, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDatePlus2d, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //NumberOfPersons must be greater or equal to 1
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, 0, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, 1, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, 4, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test numberOfPersons < totalMaxPersons
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, 5, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test availableRoomCount < selectedCategoryRoomCount
        selectedCategoriesRoomCount.put(category, 4);
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
    }
}
