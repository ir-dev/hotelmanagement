package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.enums.BookingState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookingTest extends AbstractTest {
    @Test
    void given_bookingdetails_when_createbooking_then_detailsequals() {
        // given
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 5;
        Map<String, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put("Honeymoon Suite DZ", 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        // when
        Booking booking = new Booking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(booking.getBookingNo(), bookingNo);
        assertEquals(booking.getBookingState(), BookingState.PENDING);
        assertEquals(booking.getArrivalDate(), arrivalDate);
        assertEquals(booking.getDepartureDate(), departureDate);
        assertEquals(booking.getArrivalTime(), arrivalTime);
        assertEquals(booking.getNumberOfPersons(), numberOfPersons);
        assertEquals(booking.getSelectedCategoriesRoomCount(), selectedCategoriesRoomCount);
        assertEquals(Optional.ofNullable(booking.getNumberOfBookedRooms()), Optional.of(selectedCategoriesRoomCount.values().stream().mapToInt(i -> i).sum()));
        assertEquals(booking.getGuestId(), guestId);
        assertEquals(booking.getPaymentInformation(), paymentInformation);

        booking.close();
        assertEquals(booking.getBookingState(), BookingState.CLOSED);
        assertThrows(IllegalStateException.class, booking::close);
    }
}
