package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.domain.model.Category;
import at.fhv.hotelmanagement.domain.model.Room;
import at.fhv.hotelmanagement.domain.model.RoomNumber;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AvailableCategoryDTO> availableCategories(LocalDate arrivalDate, LocalDate departureDate) {
        List<Category> categories = categoryRepository.findAll();
        List<AvailableCategoryDTO> availableCategoriesDto = new ArrayList<>();

        for (Category category : categories) {
            int availableRoomsCount = category.getAvailableRoomsCount(arrivalDate, departureDate);

            if (availableRoomsCount > 0) {
                availableCategoriesDto.add(
                        buildAvailableCategoryDto(category, availableRoomsCount)
                );
            }
        }

        return availableCategoriesDto;
    }

    @Override
    public Map<String, Set<RoomNumber>> getAvailableRooms(Map<String, Integer> selectedCategories, LocalDate fromDate, LocalDate toDate) {

        // get available roomNumbers for each category
        HashMap<String, Set<RoomNumber>> result = new HashMap<>();
        for (Map.Entry<String, Integer> categoryName : selectedCategories.entrySet()) {

            Category category = categoryRepository.findByName(categoryName.getKey()).get();
            Set<Room> availableRooms = category.getAvailableRooms(fromDate, toDate);

            // get next available roomNumber
            Set<RoomNumber> roomNumbers = new HashSet<>();
            while (roomNumbers.size() < categoryName.getValue()) {
                roomNumbers.add(availableRooms.iterator().next().getNumber());
            }

            result.put(categoryName.getKey(), roomNumbers);
        }

        return Collections.unmodifiableMap(result);
    }

    private AvailableCategoryDTO buildAvailableCategoryDto(Category category, int availableRoomsCount) {
        return AvailableCategoryDTO.builder()
                .withName(category.getName())
                .withDescription(category.getDescription())
                .withAvailableRoomsCount(availableRoomsCount)
                .build();
    }
}
