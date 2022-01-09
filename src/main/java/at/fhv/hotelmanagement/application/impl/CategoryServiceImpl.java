package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.CategoryDTO;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
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
        List<Category> categories = this.categoryRepository.findAll();
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

    @Transactional(readOnly = true)
    @Override
    public List<CategoryDTO> allCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDTO> allCategoriesDto = new ArrayList<>();

        for (Category category : categories) {
            allCategoriesDto.add(buildCategoryDto(category));
        }

        return allCategoriesDto;
    }

    @Transactional
    @Override
    public void manageCategory(String categoryName, String roomNumber, String roomState) throws IllegalArgumentException {
        Optional<Category> categoryOpt = this.categoryRepository.findByName(categoryName);
        if (categoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }
        Category category = categoryOpt.get();
        category.manageRoom(
                category.getAllRoomNumbers().stream()
                        .filter((roomNumber1 -> roomNumber1.getNumber().equalsIgnoreCase(roomNumber)))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Room number not found")),
                RoomState.valueOf(roomState));
    }

    private AvailableCategoryDTO buildAvailableCategoryDto(Category category, int availableRoomsCount) {
        return AvailableCategoryDTO.builder()
                .withName(category.getName())
                .withDescription(category.getDescription())
                .withAvailableRoomsCount(availableRoomsCount)
                .build();
    }

    private CategoryDTO buildCategoryDto(Category category) {
        return CategoryDTO.builder()
                .withName(category.getName())
                .withRooms(category.getAllRoomNumbersWithRoomStates())
                .build();
    }

}
