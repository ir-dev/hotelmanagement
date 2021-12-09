package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StayFactoryTest extends AbstractTest {
    @Test
    void given_staydetails_when_createstayfromfactory_then_detailsequals() throws CreateStayException, RoomAlreadyExistsException, CreateBookingException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(1L);
        Integer numberOfPersons = 4;

        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Honeymoon Suite DZ", "", 2, p, p);
        category.createRoom(new Room(new RoomNumber("1"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("2"), RoomState.AVAILABLE));

        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);

        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        LocalDate departureDate1 = arrivalDate;
        LocalDate departureDate2 = arrivalDate.plusDays(1L);
        LocalDate departureDate3 = arrivalDate.plusDays(2L);

        Integer numberOfPersons1 = 0;
        Integer numberOfPersons2 = 1;
        Integer numberOfPersons3 = 4;
        Integer numberOfPersons4 = 5;

        // when
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, getContextLocalTime(), numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);
        Stay stay1 = StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(stay1.getBookingNo(), Optional.of(bookingNo));
        assertEquals(stay1.getArrivalDate(), arrivalDate);
        assertEquals(stay1.getDepartureDate(), departureDate);
        assertEquals(stay1.getArrivalTime(), getContextLocalTime());
        assertEquals(stay1.getNumberOfPersons(), numberOfPersons);
        assertEquals(Optional.ofNullable(stay1.getNumberOfBookedRooms()), Optional.of(selectedCategoriesRoomCount.values().stream().mapToInt(i -> i).sum()));
        assertEquals(stay1.getGuestId(), guestId);
        assertEquals(stay1.getPaymentInformation(), paymentInformation);


        //Test selectedCategoryRoomCount
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
        }

        assertEquals(stay1.getSelectedCategoriesRoomCount(), selectedCategoryNamesRoomCount);

        //ArrivalDate must be today
        assertDoesNotThrow(() -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForWalkIn(stayId, arrivalDate.plusDays(1L), departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //DepartureDate must be after ArrivalDate (at least one day)
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate1, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate2,numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate3, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //NumberOfPersons must be greater or equal to 1
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons1, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons2, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons3, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test numberOfPersons < totalMaxPersons
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons4, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test availableRoomCount < selectedCategoryRoomCount
        selectedCategoriesRoomCount.put(category, 4);
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        selectedCategoriesRoomCount.put(category, 2);

        //Booking must have Pending state
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        booking.close();
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
    }
}
