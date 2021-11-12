package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.infrastructure.AlreadyExistsStoreException;
import at.fhv.hotelmanagement.domain.model.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository {
    List<Category> findAll();

    // this is possible because name is unique
    Optional<Category> findByName(String categoryName);

    boolean exists(String categoryName);

    void store(Category category) throws AlreadyExistsStoreException;
}

