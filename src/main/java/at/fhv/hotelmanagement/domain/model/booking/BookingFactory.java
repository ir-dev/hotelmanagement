package at.fhv.hotelmanagement.domain.model.booking;

import at.fhv.hotelmanagement.domain.model.validation.BookingStayValidation;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;

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
        BookingStayValidation.validateBooking(arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount);

        // convert to map with aggregate identity reference as key
        Map<String, Integer> selectedCategoryNamesRoomCount = BookingStayValidation.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount);

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
