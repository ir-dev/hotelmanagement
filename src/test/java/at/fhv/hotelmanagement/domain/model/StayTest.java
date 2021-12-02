package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.StayStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StayTest {
    @Test
    void given_staydetails_when_createstay_then_detailsequals() {
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("1");
        StayStatus stayStatus = StayStatus.CHECKED_IN;
//        LocalDateTime checkedInAt = LocalDateTime.now();
        LocalDateTime checkedOutAt = null;
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(1);
        LocalTime arrivalTime = LocalTime.now();
        Integer numberOfPersons = 5;
        Map<String, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put("Honeymoon Suite DZ", 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        // when
        Stay stay = new Stay(stayId, bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(stay.getStayId(), stayId);
        assertEquals(stay.getBookingNo(), Optional.of(bookingNo));
        assertEquals(stay.getStayStatus(), stayStatus);
//        assertEquals(stay.getCheckedInAt(), checkedInAt);
        assertEquals(stay.getCheckedOutAt(), Optional.empty());
        assertEquals(stay.getArrivalDate(), arrivalDate);
        assertEquals(stay.getDepartureDate(), departureDate);
        assertEquals(stay.getArrivalTime(), arrivalTime);
        assertEquals(stay.getNumberOfPersons(), numberOfPersons);
        assertEquals(stay.getSelectedCategoriesRoomCount(), selectedCategoriesRoomCount);
        assertEquals(Optional.ofNullable(stay.getNumberOfBookedRooms()), Optional.of(selectedCategoriesRoomCount.values().stream().mapToInt(i -> i).sum()));
        assertEquals(stay.getGuestId(), guestId);
        assertEquals(stay.getPaymentInformation(), paymentInformation);
    }
}