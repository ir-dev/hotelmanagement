package at.fhv.hotelmanagement.domain.model.validators;

import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.stay.CreateStayException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BookingStayValidator {

    public static void validateBooking(LocalDate arrivalDate, LocalDate departureDate, Integer numberOfPersons, Map<Category, Integer> selectedCategoriesRoomCount) throws CreateBookingException {
        // ArrivalDate is today or in the future
        if (arrivalDate.compareTo(LocalDate.now()) < 0) {
            throw new CreateBookingException("ArrivalDate must be today or in the future.");
        }

        try {
            validate(arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount);

        } catch (ValidationException e) {
            throw new CreateBookingException(e.getMessage());
        }
    }

    public static void validateStay(LocalDate arrivalDate, LocalDate departureDate, Integer numberOfPersons, Map<Category, Integer> selectedCategoriesRoomCount) throws CreateStayException {
        // ArrivalDate is today
        if (!arrivalDate.equals(LocalDate.now())) {
            throw new CreateStayException("ArrivalDate must be today.");
        }

        try {
            validate(arrivalDate, departureDate, numberOfPersons, selectedCategoriesRoomCount);

        } catch (ValidationException e) {
            throw new CreateStayException(e.getMessage());
        }
    }

    private static void validate(LocalDate arrivalDate, LocalDate departureDate, Integer numberOfPersons, Map<Category, Integer> selectedCategoriesRoomCount) throws ValidationException {
        // DepartureDate must be after ArrivalDate (at least one day)
        if (!departureDate.isAfter(arrivalDate)) {
            throw new ValidationException("DepartureDate must be after ArrivalDate.");
        }

        // NumberOfPersons must be greater or equal to 1
        if (numberOfPersons < 1) {
            throw new ValidationException("NumberOfPersons must be greater or equal to 1");
        }

        int totalMaxPersons = 0;
        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            // SelectedCategories for each category: - min. zero and max. count of available rooms for category
            Category selectedCategory = selectedCategoryRoomCount.getKey();
            int roomCount = selectedCategoryRoomCount.getValue();
            int availableRoomCount = selectedCategory.getAvailableRoomsCount(arrivalDate, departureDate);
            int maxPersons = selectedCategory.getMaxPersons();

            if (!(roomCount >= 0 && roomCount <= availableRoomCount)) {
                throw new ValidationException("SelectedCategoryRoomCount: min. zero, max. count of available rooms for category.");
            }

            totalMaxPersons += (roomCount * maxPersons);
        }

        // SelectedCategories total: - min. sum of all max. persons for each category multiplied by count
        if (!(numberOfPersons <= totalMaxPersons)) {
            throw new ValidationException("SelectedCategoryRoomCount Total: max. sum of all max. persons (for each category).");
        }
    }

    public static Map<String, Integer> convertToSelectedCategoryNamesRoomCount(Map<Category, Integer> selectedCategoriesRoomCount) {
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();

        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if(selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }

        return selectedCategoryNamesRoomCount;
    }
}
