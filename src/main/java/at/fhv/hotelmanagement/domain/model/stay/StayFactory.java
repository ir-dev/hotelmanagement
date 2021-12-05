package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.domain.model.services.impl.BookingStayValidator;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.BookingState;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;

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

        // booking must have PENDING state
        if (booking.getBookingState() != BookingState.PENDING) {
            throw new CreateStayException("The status of the booking to check-in must be PENDING.");
        }

        // stay constraints validation
        BookingStayValidator.validateStay(arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount);

        // convert to map with aggregate identity reference as key
        Map<String, Integer> selectedCategoryNamesRoomCount = BookingStayValidator.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount);

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

    public static Stay createStayForWalkIn(StayId stayId,
                                            LocalDate arrivalDate,
                                            LocalDate departureDate,
                                            Integer numberOfPersons,
                                            Map<Category, Integer> selectedCategoriesRoomCount,
                                            GuestId guestId,
                                            PaymentInformation paymentInformation) throws CreateStayException {

        // stay constraints validation
        BookingStayValidator.validateStay(arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount);

        // convert to map with aggregate identity reference as key
        Map<String, Integer> selectedCategoryNamesRoomCount = BookingStayValidator.convertToSelectedCategoryNamesRoomCount(selectedCategoriesRoomCount);

        return new Stay(
                stayId,
                null,
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
