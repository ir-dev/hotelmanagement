package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest extends AbstractTest {
    @Test
    void given_roomdetails_when_createroom_then_detailsequals() {
        //given
        RoomNumber roomNumber = new RoomNumber("100");
        RoomState roomState = RoomState.AVAILABLE;

        //when
        Room room = new Room(roomNumber, roomState);

        //then
        assertEquals(room.getRoomNumber(), roomNumber);

        assertThrows(IllegalArgumentException.class, () -> new Room(roomNumber, RoomState.OCCUPIED));
        assertDoesNotThrow(() -> {
            new Room(roomNumber, RoomState.AVAILABLE);
        });
    }

    @Test
    void given_availableroom_when_occupied_then_isandisnotavailableforperiods() {
        //given
        RoomNumber roomNumber = new RoomNumber("100");
        RoomState roomState = RoomState.AVAILABLE;
        LocalDate fromDate = getContextLocalDate();
        LocalDate toDate = fromDate.plusDays(5L);
        LocalDate inBetweenPeriod = fromDate.plusDays(1L);
        Room room = new Room(roomNumber, roomState);
        StayId stayId = new StayId("1");

        // when
        room.occupied(fromDate, toDate, stayId);

        // then
        assertTrue(room.isAvailableForPeriod(toDate, toDate.plusDays(1L)));
        assertTrue(room.isAvailableForPeriod(fromDate.minusDays(1L), fromDate));

        assertFalse(room.isAvailableForPeriod(inBetweenPeriod, toDate));
        assertFalse(room.isAvailableForPeriod(fromDate, inBetweenPeriod));
    }

    @Test
    void given_occupiedroom_when_available_then_isavailableforperiods() {
        //given
        RoomNumber roomNumber = new RoomNumber("100");
        RoomState roomState = RoomState.AVAILABLE;
        LocalDate fromDate = getContextLocalDate();
        LocalDate toDate = fromDate.plusDays(5L);
        LocalDate inBetweenPeriod = fromDate.plusDays(1L);
        Room room = new Room(roomNumber, roomState);
        StayId stayId = new StayId("1");
        room.occupied(fromDate, toDate, stayId);

        // when
        room.cleaning(stayId);

        // then
        assertTrue(room.isAvailableForPeriod(toDate, toDate.plusDays(1L)));
        assertTrue(room.isAvailableForPeriod(fromDate.minusDays(1L), fromDate));

        assertTrue(room.isAvailableForPeriod(inBetweenPeriod, toDate));
        assertTrue(room.isAvailableForPeriod(fromDate, inBetweenPeriod));
    }
}