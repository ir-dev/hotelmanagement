package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
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

        // ArrivalDate is today or in the future
        if (!arrivalDate.isAfter(LocalDate.now().minusDays(1))) {
            throw new CreateBookingException("ArrivalDate must be today or in the future.");
        }

        // DepartureDate must be after ArrivalDate (at least one day)
        if (!departureDate.isAfter(arrivalDate)) {
            throw new CreateBookingException("DepartureDate must be after ArrivalDate.");
        }

        // NumberOfPersons must be greater or equal to 1
        if (!(numberOfPersons >= 1)) {
            throw new CreateBookingException("NumberOfPersons must be greater or equal to 1");
        }

        int totalMaxPersons = 0;
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            // SelectedCategories for each category: - min. zero and max. count of available rooms for category
            Category selectedCategory = selectedCategoryRoomCount.getKey();
            int roomCount = selectedCategoryRoomCount.getValue();
            int availableRoomCount = selectedCategory.getAvailableRoomsCount(arrivalDate, departureDate);
            int maxPersons = selectedCategory.getMaxPersons();

            if (!(roomCount >= 0 && roomCount <= availableRoomCount)) {
                throw new CreateBookingException("SelectedCategoryRoomCount: min. zero, max. count of available rooms for category.");
            }

            totalMaxPersons += (roomCount * maxPersons);
        }

        // SelectedCategories total: - min. sum of all max. persons for each category multiplied by count
        if (!(numberOfPersons <= totalMaxPersons)) {
            throw new CreateBookingException("SelectedCategoryRoomCount Total: max. sum of all max. persons (for each category).");
        }

        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
        }

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
