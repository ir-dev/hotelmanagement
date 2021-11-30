package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class StayFactory {
    public static Stay createStayForBooking(StayId stayId,
                                            Booking booking,
                                            BookingNo bookingNo,
                                            LocalDate arrivalDate,
                                            LocalDate departureDate,
                                            Integer numberOfPersons,
                                            Map<Category, Integer> selectedCategoriesRoomCount,
                                            GuestId guestId,
                                            PaymentInformation paymentInformation) throws CreateStayException {

        // stay constraints validation
        BookingStayService.validateStay(booking.getBookingState(), arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount);

        // convert to map with aggregate identity reference as key
        Map<String, Integer> selectedCategoryNamesRoomCount = BookingStayService.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount);

        return new Stay(
                stayId,
                bookingNo,
                arrivalDate,
                departureDate,
                LocalTime.now(),
                numberOfPersons,
                selectedCategoryNamesRoomCount,
                guestId,
                paymentInformation
        );
    }
}
