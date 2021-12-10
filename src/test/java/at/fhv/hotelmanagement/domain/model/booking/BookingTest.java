package at.fhv.hotelmanagement.domain.model.booking;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.*;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import at.fhv.hotelmanagement.domain.model.validators.BookingStayValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookingTest extends AbstractTest {
    @Test
    void given_bookingdetails_when_createbooking_then_detailsequals() throws CreateBookingException, RoomAlreadyExistsException {
        // given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 3;
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, p, p);
        category.createRoom(new Room(new RoomNumber("111"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("112"), RoomState.AVAILABLE));

        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        // when
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(bookingNo, booking.getBookingNo());
        assertEquals(BookingState.PENDING, booking.getBookingState());
        assertEquals(arrivalDate, booking.getArrivalDate());
        assertEquals(departureDate, booking.getDepartureDate());
        assertEquals(arrivalTime, booking.getArrivalTime());
        assertEquals(numberOfPersons, booking.getNumberOfPersons());
        assertEquals(BookingStayValidator.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount), booking.getSelectedCategoriesRoomCount());
        assertEquals(guestId, booking.getGuestId());
        assertEquals(paymentInformation, booking.getPaymentInformation());

        booking.close();
        assertEquals(booking.getBookingState(), BookingState.CLOSED);
        assertThrows(IllegalStateException.class, booking::close);
    }
}
