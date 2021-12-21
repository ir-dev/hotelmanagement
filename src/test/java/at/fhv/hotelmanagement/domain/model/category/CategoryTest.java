package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
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
        Price p1 = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Price p2 = Price.of(BigDecimal.ONE, Currency.getInstance("EUR"));

        LocalDate fromdate = getContextLocalDate();
        LocalDate todate1 = getContextLocalDate().plusDays(1L);
        LocalDate todate2 = getContextLocalDate().plusDays(2L);

        //when
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, p1, p2);

        //then
        assertEquals(category.getCategoryId(), categoryId);
        assertEquals(category.getName(), name);
        assertEquals(category.getDescription(), description);
        assertEquals(category.getMaxPersons(), maxPersons);
        assertEquals(category.getHalfBoardPrice(), p1);
        assertEquals(category.getFullBoardPrice(), p2);

        assertDoesNotThrow(() -> category.createRoom(room));

        assertThrows(RoomAlreadyExistsException.class, () -> category.createRoom(room));

        assertEquals(category.getAvailableRoomsCount(fromdate, todate2), 1);

        room.occupied(fromdate, todate1, null);
        assertEquals(category.getAvailableRoomsCount(fromdate, todate2), 0);

        assertEquals(category.getAvailableRoomNumbers(fromdate, todate1), roomNumbers);
        roomNumbers.add(roomNumber);
        assertEquals(category.getAvailableRoomNumbers(todate1.plusDays(1L), todate2), roomNumbers);
    }

    @Test
    void given_categorywithoccupiedrooms_when_releaserooms_then_availableroomnumbersequalsrooms() {
        // given

        // when

        // then
    }
}