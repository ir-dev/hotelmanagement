package at.fhv.hotelmanagement.application.api;

import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.RoomDTO;

import java.time.LocalDate;
import java.util.List;

public interface CategoryService {
    List<AvailableCategoryDTO> availableCategories(LocalDate arrivalDate, LocalDate departureDate);

    List<RoomDTO> allRooms();

}
