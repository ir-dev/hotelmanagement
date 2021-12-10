package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;

import java.time.LocalDate;
import java.util.List;

public interface CategoryService {
    List<AvailableCategoryDTO> availableCategories(LocalDate arrivalDate, LocalDate departureDate);

//    Map<String, Set<RoomNumber>> getAvailableRooms(Map<String, Integer> selectedCategories, LocalDate fromDate, LocalDate toDate);
}
