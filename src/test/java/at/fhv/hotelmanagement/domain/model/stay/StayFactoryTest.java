package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StayFactoryTest extends AbstractTest {
    @Test
    void given_staydetails_when_createstayforbookingfromfactory_then_detailsequals() throws CreateStayException, RoomAlreadyExistsException, CreateBookingException {
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
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, getContextLocalTime(), numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // when
        Stay stay = StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(Optional.of(bookingNo), stay.getBookingNo());
        assertEquals(arrivalDate, stay.getArrivalDate());
        assertEquals(departureDate, stay.getDepartureDate());
        assertEquals(getContextLocalTime(), stay.getArrivalTime());
        assertEquals(numberOfPersons, stay.getNumberOfPersons());
        assertEquals(2, stay.getNumberOfBookedRooms());
        assertEquals(guestId, stay.getGuestId());
        assertEquals(paymentInformation, stay.getPaymentInformation());
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
        }
        assertEquals(selectedCategoryNamesRoomCount, stay.getSelectedCategoriesRoomCount());
    }

    @Test
    void given_staydetails_when_createstayforwalkinfromfactory_then_detailsequals() throws CreateStayException, RoomAlreadyExistsException {
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

        // when
        Stay stay = StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(Optional.empty(), stay.getBookingNo());
        assertEquals(arrivalDate, stay.getArrivalDate());
        assertEquals(departureDate, stay.getDepartureDate());
        assertEquals(getContextLocalTime(), stay.getArrivalTime());
        assertEquals(numberOfPersons, stay.getNumberOfPersons());
        assertEquals(2, stay.getNumberOfBookedRooms());
        assertEquals(guestId, stay.getGuestId());
        assertEquals(paymentInformation, stay.getPaymentInformation());
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
        }
        assertEquals(selectedCategoryNamesRoomCount, stay.getSelectedCategoriesRoomCount());
    }

    @Test
    void given_closedbooking_when_createstayforbookingfromfactory_then_throwscreatestayexception() throws RoomAlreadyExistsException, CreateBookingException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(1L);
        LocalDate arrivalDateMinus1d = arrivalDate.minusDays(1L);
        LocalDate arrivalDatePlus1d = arrivalDate.plusDays(1L);
        LocalDate departureDatePlus1d = arrivalDate.plusDays(1L);
        LocalDate departureDatePlus2d = arrivalDate.plusDays(2L);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 4;
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Honeymoon Suite DZ", "", 2, p, p);
        category.createRoom(new Room(new RoomNumber("1"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("2"), RoomState.AVAILABLE));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);
        booking.close();

        // when
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, arrivalDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
    }

    @Test
    void given_staydetailswithinvalidvalidationconstraint_when_createstayforbookingfromfactory_then_throwscreatestayexception() throws RoomAlreadyExistsException, CreateBookingException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(1L);
        LocalDate arrivalDateMinus1d = arrivalDate.minusDays(1L);
        LocalDate arrivalDatePlus1d = arrivalDate.plusDays(1L);
        LocalDate departureDatePlus1d = arrivalDate.plusDays(1L);
        LocalDate departureDatePlus2d = arrivalDate.plusDays(2L);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 4;
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Honeymoon Suite DZ", "", 2, p, p);
        category.createRoom(new Room(new RoomNumber("1"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("2"), RoomState.AVAILABLE));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // when..then
        // NOTE: our booking does not really hold the invalid data, we just simulate it by adjusting the parameters
        //ArrivalDate is today or in the future
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDateMinus1d, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDatePlus1d, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //DepartureDate must be after ArrivalDate (at least one day)
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, arrivalDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDatePlus1d, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDatePlus2d, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //NumberOfPersons must be greater or equal to 1
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, 0, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, 1, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, 4, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test numberOfPersons < totalMaxPersons
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, 5, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test availableRoomCount < selectedCategoryRoomCount
        selectedCategoriesRoomCount.put(category, 4);
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
    }

    @Test
    void given_staydetailswithinvalidvalidationconstraint_when_createstayforwalkinfromfactory_then_throwscreatestayexception() throws RoomAlreadyExistsException {
        // given
        StayId stayId = new StayId("1");
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = arrivalDate.plusDays(1L);
        LocalDate arrivalDateMinus1d = arrivalDate.minusDays(1L);
        LocalDate arrivalDatePlus1d = arrivalDate.plusDays(1L);
        LocalDate departureDatePlus1d = arrivalDate.plusDays(1L);
        LocalDate departureDatePlus2d = arrivalDate.plusDays(2L);
        Integer numberOfPersons = 4;
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Honeymoon Suite DZ", "", 2, p, p);
        category.createRoom(new Room(new RoomNumber("1"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("2"), RoomState.AVAILABLE));
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        // when..then
        //ArrivalDate is today or in the future
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForWalkIn(stayId, arrivalDateMinus1d, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForWalkIn(stayId, arrivalDatePlus1d, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //DepartureDate must be after ArrivalDate (at least one day)
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForWalkIn(stayId, arrivalDate, arrivalDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDatePlus1d, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDatePlus2d, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //NumberOfPersons must be greater or equal to 1
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, 0, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, 1, selectedCategoriesRoomCount, guestId, paymentInformation));
        assertDoesNotThrow(() -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, 4, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test numberOfPersons < totalMaxPersons
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, 5, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test selectedCategoryRoomCount = -1
        selectedCategoriesRoomCount.put(category, -1);
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));

        //test availableRoomCount < selectedCategoryRoomCount
        selectedCategoriesRoomCount.put(category, 4);
        assertThrows(CreateStayException.class, () -> StayFactory.createStayForWalkIn(stayId, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation));
    }
}
