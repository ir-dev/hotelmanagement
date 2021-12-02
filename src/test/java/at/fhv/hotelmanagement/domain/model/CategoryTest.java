package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CategoryTest {
    @Test
    void given_categorydetails_when_createcategory_then_detailsequals() {
        //given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Set<Room> rooms = new HashSet<>();
        Room room = new Room(new RoomNumber("100"), RoomState.AVAILABLE);
        rooms.add(room);

        //when
        Category category = new Category(categoryId, name, description, maxPersons);

        //then
        assertEquals(category.getCategoryId(), categoryId);
        assertEquals(category.getName(), name);
        assertEquals(category.getDescription(), description);
        assertEquals(category.getMaxPersons(), maxPersons);

        assertDoesNotThrow(() -> category.createRoom(room));

        assertThrows(AlreadyExistsException.class, () -> category.createRoom(room));

        assertEquals(category.getAvailableRoomsCount(LocalDate.of(2021, 11, 11), LocalDate.of(2021, 11, 14)), 1);

        room.occupied(LocalDate.of(2021, 11, 11), LocalDate.of(2021, 11, 12));
        assertEquals(category.getAvailableRoomsCount(LocalDate.of(2021, 11, 11), LocalDate.of(2021, 11, 14)), 0);

//        assertTrue(category.getRooms().containsAll(rooms));
    }
}