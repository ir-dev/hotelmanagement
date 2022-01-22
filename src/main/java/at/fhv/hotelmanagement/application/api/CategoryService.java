package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.CategoryDTO;

import java.time.LocalDate;
import java.util.List;

public interface CategoryService {
    List<AvailableCategoryDTO> availableCategoriesForBooking(LocalDate arrivalDate, LocalDate departureDate);

    List<AvailableCategoryDTO> availableCategories(LocalDate arrivalDate, LocalDate departureDate);

    List<CategoryDTO> allCategories();

    void manageCategory(String categoryName, String roomNumber, String roomState);
}
