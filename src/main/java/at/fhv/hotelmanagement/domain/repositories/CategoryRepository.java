package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
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

    List<Room> findRoomsByStayId(StayId stayId);

    void store(Category category);
}

