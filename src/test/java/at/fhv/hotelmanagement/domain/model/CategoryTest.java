package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CategoryTest extends AbstractTest {
    @Test
    void given_categorydetails_when_createcategory_then_detailsequals() {
        //given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Set<Room> rooms = new HashSet<>();
        RoomNumber roomNumber = new RoomNumber("1ßß");
        Room room = new Room(roomNumber, RoomState.AVAILABLE);
        rooms.add(room);
        Set<RoomNumber> roomNumbers = new HashSet<>();

        LocalDate fromdate = getContextLocalDate();
        LocalDate todate1 = getContextLocalDate().plusDays(1L);
        LocalDate todate2 = getContextLocalDate().plusDays(2L);

        //when
        Category category = new Category(categoryId, name, description, maxPersons);

        //then
        assertEquals(category.getCategoryId(), categoryId);
        assertEquals(category.getName(), name);
        assertEquals(category.getDescription(), description);
        assertEquals(category.getMaxPersons(), maxPersons);

        assertDoesNotThrow(() -> category.createRoom(room));

        assertThrows(AlreadyExistsException.class, () -> category.createRoom(room));

        assertEquals(category.getAvailableRoomsCount(fromdate, todate2), 1);

        room.occupied(fromdate, todate1);
        assertEquals(category.getAvailableRoomsCount(fromdate, todate2), 0);

        assertEquals(category.getAvailableRoomNumbers(fromdate, todate1), roomNumbers);
        roomNumbers.add(roomNumber);
        assertEquals(category.getAvailableRoomNumbers(todate1.plusDays(1L), todate2), roomNumbers);
    }
}