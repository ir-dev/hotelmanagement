package at.fhv.hotelmanagement.domain.repositories;

import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository {
    List<Category> findAll();

    // find by name is possible because name is unique
    Optional<Category> findByName(String categoryName);

    List<Room> findAllRoomsByState(RoomState state);

    List<Room> findCategoryRoomsByState(String categoryName, RoomState state);

    void store(Category category);

    void store(RoomOccupancy roomOccupancy);

    OccupancyId nextIdentity();
}

