package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class BookingFactory {

    public static Booking createBooking(BookingNo bookingNo,
                                        LocalDate arrivalDate,
                                        LocalDate departureDate,
                                        LocalTime arrivalTime,
                                        Integer numberOfPersons,
                                        Map<Category, Integer> selectedCategoriesRoomCount,
                                        GuestId guestId,
                                        PaymentInformation paymentInformation) throws CreateBookingException {

        // booking constraints validation
        BookingStayValidator.validateBooking(arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount);

        // convert to map with aggregate identity reference as key
        Map<String, Integer> selectedCategoryNamesRoomCount = BookingStayValidator.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount);

        return new Booking(
                bookingNo,
                arrivalDate,
                departureDate,
                arrivalTime,
                numberOfPersons,
                selectedCategoryNamesRoomCount,
                guestId,
                paymentInformation);
    }
}
