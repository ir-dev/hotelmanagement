package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.room.Room;
import at.fhv.hotelmanagement.domain.model.category.room.RoomState;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository {
    CategoryId nextIdentity();

    List<Category> findAll();

    // find by name is possible because name is unique
    Optional<Category> findByName(String categoryName);

    List<Room> findCategoryRoomsByState(String categoryName, RoomState state);

    void store(Category category);
}

