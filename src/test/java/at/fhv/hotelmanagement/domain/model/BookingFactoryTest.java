package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookingFactoryTest extends AbstractTest {
    @Test
    void given_bookingdetails_when_createbookingfromfactory_then_detailsequals() throws CreateBookingException, AlreadyExistsException {
        // given
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 4;

        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Honeymoon Suite DZ", "", 2);
        category.createRoom(new Room(new RoomNumber("1"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("2"), RoomState.AVAILABLE));

        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);

        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        LocalDate arrivalDate1 = arrivalDate.minusDays(1L);
        LocalDate arrivalDate2 = arrivalDate;
        LocalDate arrivalDate3 = arrivalDate.plusDays(1L);
        LocalDate departureDate1 = arrivalDate;
        LocalDate departureDate2 = arrivalDate.plusDays(1L);
        LocalDate departureDate3 = arrivalDate.plusDays(2L);

        Integer numberOfPersons1 = 0;
        Integer numberOfPersons2 = 1;
        Integer numberOfPersons3 = 4;
        Integer numberOfPersons4 = 5;

        // when
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(booking.getBookingNo(), bookingNo);
        assertEquals(booking.getArrivalDate(), arrivalDate);
        assertEquals(booking.getDepartureDate(), departureDate);
        assertEquals(booking.getArrivalTime(), arrivalTime);
        assertEquals(booking.getNumberOfPersons(), numberOfPersons);
        assertEquals(Optional.ofNullable(booking.getNumberOfBookedRooms()), Optional.of(selectedCategoriesRoomCount.values().stream().mapToInt(i -> i).sum()));
        assertEquals(booking.getGuestId(), guestId);
        assertEquals(booking.getPaymentInformation(), paymentInformation);

        // test selectedCategoryRoomCount
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
        }
        assertEquals(booking.getSelectedCategoriesRoomCount(), selectedCategoryNamesRoomCount);

        //ArrivalDate is today or in the future
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate1, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate2, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate3, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //DepartureDate must be after ArrivalDate (at least one day)
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate1, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate2, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate3, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //NumberOfPersons must be greater or equal to 1
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons1, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons2, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons3, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test numberOfPersons < totalMaxPersons
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons4, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test availableRoomCount < selectedCategoryRoomCount
        selectedCategoriesRoomCount.put(category, 4);
        assertThrows(CreateBookingException.class, () -> BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
    }
}
