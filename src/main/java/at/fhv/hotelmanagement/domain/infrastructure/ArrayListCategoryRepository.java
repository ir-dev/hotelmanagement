package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.Category;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ArrayListCategoryRepository implements CategoryRepository {
    private List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> findAll() {
        if (categories.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(categories);
    }

    @Override
    public Optional<Category> findByName(String categoryName) {
        return categories.stream()
                .filter(category -> category.getName().equals(categoryName))
                .findFirst();
    }

    @Override
    public boolean exists(String categoryName) {
        return findByName(categoryName).isPresent();
    }

    @Override
    public void store(Category category) throws AlreadyExistsStoreException {
        if (exists(category.getName())) {
            throw new AlreadyExistsStoreException(Category.class);
        }

        categories.add(category);
    }
}
