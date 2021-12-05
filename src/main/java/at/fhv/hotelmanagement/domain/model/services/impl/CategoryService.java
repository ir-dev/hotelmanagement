package at.fhv.hotelmanagement.domain.model.services.impl;

import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.InsufficientRoomsException;
import at.fhv.hotelmanagement.domain.model.category.RoomAssignmentException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class CategoryService {

    public void autoAssignRooms(Map<Category, Integer> selectedCategoriesRoomCount, LocalDate fromDate, LocalDate toDate) throws RoomAssignmentException {

        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            Category category = selectedCategoryRoomCount.getKey();
            Integer categoryCount = selectedCategoryRoomCount.getValue();

            try {
                category.assignAvailableRooms(categoryCount, fromDate, toDate);
            } catch (InsufficientRoomsException e) {
                throw new RoomAssignmentException(category.getName(), e.getMessage());
            }
        }
    }
}
