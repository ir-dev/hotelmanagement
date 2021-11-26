package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.BookingState;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
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

        // DepartureDate must be after ArrivalDate (at least one day)
        if (!departureDate.isAfter(arrivalDate)) {
            throw new CreateStayException("DepartureDate must be after ArrivalDate.");
        }

        // NumberOfPersons must be greater or equal to 1
        if (!(numberOfPersons >= 1)) {
            throw new CreateStayException("NumberOfPersons must be greater or equal to 1");
        }

        int totalMaxPersons = 0;
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            // SelectedCategories for each category: - min. zero and max. count of available rooms for category
            Category selectedCategory = selectedCategoryRoomCount.getKey();
            int roomCount = selectedCategoryRoomCount.getValue();
            int availableRoomCount = selectedCategory.getAvailableRoomsCount(arrivalDate, departureDate);
            int maxPersons = selectedCategory.getMaxPersons();

            if (!(roomCount >= 0 && roomCount <= availableRoomCount)) {
                throw new CreateStayException("SelectedCategoryRoomCount: min. zero, max. count of available rooms for category.");
            }

            totalMaxPersons += (roomCount * maxPersons);
        }

        // SelectedCategories total: - min. sum of all max. persons for each category multiplied by count
        if (!(numberOfPersons <= totalMaxPersons)) {
            throw new CreateStayException("SelectedCategoryRoomCount Total: max. sum of all max. persons (for each category).");
        }

        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
        }

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
