package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.domain.model.Category;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<AvailableCategoryDTO> availableCategories(LocalDate arrivalDate, LocalDate departureDate) {
        List<Category> categories = categoryRepository.findAll();
        List<AvailableCategoryDTO> availableCategoriesDto = new ArrayList<>();

        for (Category category : categories) {
            int availableRoomsCount = category.getAvailableRoomsCount(arrivalDate, departureDate);

            if (availableRoomsCount > 0) {
                availableCategoriesDto.add(AvailableCategoryDTO.builder()
                        .withName(category.getName())
                        .withDescription(category.getDescription())
                        .withAvailableRoomsCount(availableRoomsCount)
                        .build());
            }
        }

        return availableCategoriesDto;
    }
}
