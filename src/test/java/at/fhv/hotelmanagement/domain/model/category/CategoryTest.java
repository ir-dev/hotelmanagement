package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.stay.StayId;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class CategoryTest extends AbstractTest {
    @Test
    void given_categorydetails_when_createcategory_then_detailsequals() {
        //given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Set<Room> rooms = new HashSet<>();
        RoomNumber roomNumber = new RoomNumber("100");
        Room room = new Room(roomNumber, RoomState.AVAILABLE);
        rooms.add(room);
        Set<RoomNumber> roomNumbers = new HashSet<>();
        Price p1 = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Price p2 = Price.of(BigDecimal.ONE, Currency.getInstance("EUR"));

        LocalDate fromdate = LocalDate.now();
        LocalDate todate1 = LocalDate.now().plusDays(1L);
        LocalDate todate2 = LocalDate.now().plusDays(2L);

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
    void given_categorywithavailablerooms_when_assignavailablerooms_then_returnsemptyavailableroomnumbers() throws RoomAlreadyExistsException, InsufficientRoomsException {
        // given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, price, price);
        Room room1 = new Room(new RoomNumber("100"), RoomState.AVAILABLE);
        Room room2 = new Room(new RoomNumber("111"), RoomState.AVAILABLE);
        category.createRoom(room1);
        category.createRoom(room2);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = fromDate.plusDays(1L);

        // when
        category.assignAvailableRooms(2, fromDate, toDate, new StayId("1"));

        // then
        assertEquals(Collections.emptySet(), category.getAvailableRoomNumbers(fromDate, toDate));
    }

    @Test
    void given_categorywithnotenoughavailablerooms_when_assignavailablerooms_then_throws() throws RoomAlreadyExistsException {
        // given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, price, price);
        Room room1 = new Room(new RoomNumber("100"), RoomState.AVAILABLE);
        Room room2 = new Room(new RoomNumber("111"), RoomState.AVAILABLE);
        category.createRoom(room1);
        category.createRoom(room2);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = fromDate.plusDays(1L);

        // when..then
        assertThrows(InsufficientRoomsException.class, () -> category.assignAvailableRooms(3, fromDate, toDate, new StayId("1")));
    }

    @Test
    void given_categorywithoccupiedrooms_when_releaserooms_then_roomsareavailable() throws RoomAlreadyExistsException, InsufficientRoomsException {
        // given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, price, price);
        Room room1 = new Room(new RoomNumber("100"), RoomState.AVAILABLE);
        Room room2 = new Room(new RoomNumber("111"), RoomState.AVAILABLE);
        Room room3 = new Room(new RoomNumber("112"), RoomState.AVAILABLE);
        category.createRoom(room1);
        category.createRoom(room2);
        category.createRoom(room3);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = fromDate.plusDays(1L);
        category.assignAvailableRooms(2, fromDate, toDate, new StayId("1"));
        category.assignAvailableRooms(1, fromDate, toDate, new StayId("2"));

        // when
        List.of(room1.getRoomNumber(), room2.getRoomNumber(), room3.getRoomNumber())
                .forEach(roomNumber -> category.releaseRoom(roomNumber, new StayId("1")));

        // then
        assertEquals(2, category.getAvailableRoomsCount(fromDate, toDate));
    }

    @Test
    void given_categorywithoccupiedrooms_when_releaseillegalroomnumber_then_throws() throws RoomAlreadyExistsException, InsufficientRoomsException {
        // given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, price, price);
        Room room1 = new Room(new RoomNumber("100"), RoomState.AVAILABLE);
        Room room2 = new Room(new RoomNumber("111"), RoomState.AVAILABLE);
        category.createRoom(room1);
        category.createRoom(room2);
        LocalDate fromDate = LocalDate.now();
        LocalDate toDate = fromDate.plusDays(1L);
        category.assignAvailableRooms(2, fromDate, toDate, new StayId("1"));

        // when..then
        assertThrows(IllegalArgumentException.class, () ->
                List.of(room1.getRoomNumber(), room2.getRoomNumber(), new RoomNumber("99"))
                .forEach(roomNumber -> category.releaseRoom(roomNumber, new StayId("1")))
        );
    }

    @Test
    void given_categorywithroom_when_getroombyroomnumber_then_returnroom() throws RoomAlreadyExistsException {
        // given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, price, price);
        Room room1 = new Room(new RoomNumber("100"), RoomState.AVAILABLE);
        Room room2 = new Room(new RoomNumber("111"), RoomState.AVAILABLE);
        category.createRoom(room1);
        category.createRoom(room2);

        // when..then
        assertThrows(IllegalArgumentException.class, () -> category.getRoomByRoomNumber(new RoomNumber("99")));
        assertDoesNotThrow(() -> category.getRoomByRoomNumber(new RoomNumber("100")));
    }

    @Test
    void given_categorywithroom_when_manageroom_then_throwsanddoesnotthrow() throws RoomAlreadyExistsException {
        // given
        CategoryId categoryId = new CategoryId("1");
        String name = ("Honeymoon Suite DZ");
        String description = ("description");
        Integer maxPersons = 2;
        Price price = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(categoryId, name, description, maxPersons, price, price);
        RoomNumber roomNumber1 = new RoomNumber("100");
        Room room1 = new Room(roomNumber1, RoomState.AVAILABLE);
        category.createRoom(room1);

        // when..then
        assertDoesNotThrow(() -> category.manageRoom(roomNumber1, RoomState.CLEANING));
        assertDoesNotThrow(() -> category.manageRoom(roomNumber1, RoomState.AVAILABLE));
        assertDoesNotThrow(() -> category.manageRoom(roomNumber1, RoomState.MAINTENANCE));
        assertThrows(IllegalArgumentException.class, () -> category.manageRoom(new RoomNumber("100"), RoomState.OCCUPIED));
    }
}