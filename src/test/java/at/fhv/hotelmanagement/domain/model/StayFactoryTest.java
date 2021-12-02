package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StayFactoryTest {
    @Test
    void given_staydetails_when_createstayfromfactory_then_detailsequals() throws CreateStayException, AlreadyExistsException, CreateBookingException {
        // given
        StayId stayId = new StayId("1");
        BookingNo bookingNo = new BookingNo("1");
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(1);
        Integer numberOfPersons = 4;

        Category category = new Category(new CategoryId("1"), "Honeymoon Suite DZ", "", 2);
        category.createRoom(new Room(new RoomNumber("1"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("2"), RoomState.AVAILABLE));

        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();
        selectedCategoriesRoomCount.put(category, 2);

        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Anna Bauer", "1234", "12/23", "123", "CASH");

        LocalDate departureDate1 = arrivalDate;
        LocalDate departureDate2 = arrivalDate.plusDays(1);
        LocalDate departureDate3 = arrivalDate.plusDays(2);

        Integer numberOfPersons1 = 0;
        Integer numberOfPersons2 = 1;
        Integer numberOfPersons3 = 4;
        Integer numberOfPersons4 = 5;

        // when
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, LocalTime.now(), numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);
        Stay stay = StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount, guestId, paymentInformation);

        // then
        assertEquals(stay.getBookingNo(), Optional.of(bookingNo));
        assertEquals(stay.getArrivalDate(), arrivalDate);
        assertEquals(stay.getDepartureDate(), departureDate);
        assertEquals(stay.getNumberOfPersons(), numberOfPersons);
        assertEquals(Optional.ofNullable(stay.getNumberOfBookedRooms()), Optional.of(selectedCategoriesRoomCount.values().stream().mapToInt(i -> i).sum()));
        assertEquals(stay.getGuestId(), guestId);
        assertEquals(stay.getPaymentInformation(), paymentInformation);

        // test selectedCategoryRoomCount
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
        }
        assertEquals(stay.getSelectedCategoriesRoomCount(), selectedCategoryNamesRoomCount);

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
