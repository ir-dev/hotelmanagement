package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.CategoryDTO;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
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
    public void manageRoom(String categoryName, String roomNumber, String roomState) {
        Optional<Category> category = this.categoryRepository.findByName(categoryName);
        if (category.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Optional<Room> room = category.get().getRoomByRoomNumber(roomNumber);
        if (room.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (Arrays.stream(RoomState.values()).noneMatch(state -> state.name().equalsIgnoreCase(roomState))) {
            throw new IllegalArgumentException();
        }
        RoomState currState = room.get().getRoomState();
        switch (roomState) {
            case "available":
                if (currState == RoomState.CLEANING || currState == RoomState.MAINTENANCE) room.get().available();
                break;
            case "cleaning":
                if (currState == RoomState.AVAILABLE || currState == RoomState.MAINTENANCE) room.get().cleaning();
                break;
            case "maintenance":
                if (currState == RoomState.AVAILABLE || currState == RoomState.CLEANING) room.get().maintenance();
                break;
        }
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
                .withRooms(category.getAllRoomsForDTO())
                .build();
    }

}
