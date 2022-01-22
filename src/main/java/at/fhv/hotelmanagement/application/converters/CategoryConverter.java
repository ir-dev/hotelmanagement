package at.fhv.hotelmanagement.application.converters;

import at.fhv.hotelmanagement.application.impl.EntityNotFoundException;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryConverter {
    private static CategoryRepository categoryRepository;

    @Autowired
    public CategoryConverter(CategoryRepository categoryRepository) {
        CategoryConverter.categoryRepository = categoryRepository;
    }

    public static Map<String, Integer> convertToSelectedCategoryNamesRoomCount(Map<Category, Integer> selectedCategoriesRoomCount) {
        Map<String, Integer> selectedCategoryNamesRoomCount = new HashMap<>();

        for (Map.Entry<Category, Integer> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            if (selectedCategoryRoomCount.getValue() > 0) {
                selectedCategoryNamesRoomCount.put(selectedCategoryRoomCount.getKey().getName(), selectedCategoryRoomCount.getValue());
            }
        }

        return selectedCategoryNamesRoomCount;
    }

    public static Map<Category, Integer> convertToSelectedCategoriesRoomCount(Map<String, Integer> selectedCategoryNamesRoomCount) throws EntityNotFoundException {
        Map<Category, Integer> selectedCategoriesRoomCount = new HashMap<>();

        for (Map.Entry<String, Integer> scnrc : selectedCategoryNamesRoomCount.entrySet()) {
            selectedCategoriesRoomCount.put(CategoryConverter.categoryRepository.findByName(scnrc.getKey())
                    .orElseThrow(() -> new EntityNotFoundException(Category.class, scnrc.getKey())), scnrc.getValue());
        }

        return selectedCategoriesRoomCount;
    }
}
