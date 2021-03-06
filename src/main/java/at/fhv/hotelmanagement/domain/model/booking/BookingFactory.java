package at.fhv.hotelmanagement.domain.model.booking;

import at.fhv.hotelmanagement.application.converters.CategoryConverter;
import at.fhv.hotelmanagement.domain.model.validators.BookingStayValidator;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class BookingFactory {
    private BookingFactory() {}

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
        Map<String, Integer> selectedCategoryNamesRoomCount = CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount);

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
