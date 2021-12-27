package at.fhv.hotelmanagement.domain.model.booking;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.application.converters.CategoryConverter;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

public class BookingTest extends AbstractTest {
    @Test
    void given_bookingdetails_when_createbooking_then_detailsequals() {
        // given
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 4;
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        selectedCategoryNamesRoomCount.put("Honeymoon Suite DZ", 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        // when
        Booking booking = new Booking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(bookingNo, booking.getBookingNo());
        assertEquals(BookingState.PENDING, booking.getBookingState());
        assertEquals(arrivalDate, booking.getArrivalDate());
        assertEquals(departureDate, booking.getDepartureDate());
        assertEquals(arrivalTime, booking.getArrivalTime());
        assertEquals(numberOfPersons, booking.getNumberOfPersons());
        assertEquals(selectedCategoryNamesRoomCount, booking.getSelectedCategoriesRoomCount());
        assertEquals(2, booking.getNumberOfBookedRooms());
        assertEquals(guestId, booking.getGuestId());
        assertEquals(paymentInformation, booking.getPaymentInformation());
    }

    @Test
    void given_booking_when_close_then_bookingstateclosed() {
        // given
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 4;
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        selectedCategoryNamesRoomCount.put("Honeymoon Suite DZ", 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");
        Booking booking = new Booking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);

        // when
        booking.close();

        // then
        assertEquals(BookingState.CLOSED, booking.getBookingState());
    }

    @Test
    void given_closedbooking_when_close_then_throwsillegalstateexception() {
        // given
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 4;
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        selectedCategoryNamesRoomCount.put("Honeymoon Suite DZ", 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");
        Booking booking = new Booking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoryNamesRoomCount, guestId, paymentInformation);
        booking.close();

        // when..then
        assertThrows(IllegalStateException.class, booking::close);
    }
}
