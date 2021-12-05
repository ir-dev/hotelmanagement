package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest extends AbstractTest {
    @Test
    void given_roomdetails_when_createroom_then_detailsequals() {
        //given
        RoomNumber roomNumber = new RoomNumber("100");
        RoomState roomState = RoomState.AVAILABLE;
        LocalDate fromdate = getContextLocalDate();
        LocalDate todate = getContextLocalDate().plusDays(5L);
        LocalDate before = getContextLocalDate().minusDays(2L);
        LocalDate middle = getContextLocalDate().plusDays(3L);
        LocalDate after = getContextLocalDate().plusDays(10L);

        //when
        Room room = new Room(roomNumber, roomState);
        room.occupied(fromdate, todate);

        //then
        assertEquals(room.getRoomNumber(), roomNumber);

        // test overlap beginning
        assertThrows(IllegalStateException.class, () -> room.occupied(before, middle));

        // test overlap whole
        assertThrows(IllegalStateException.class, () -> room.occupied(fromdate, todate));

        // test overlap ending
        assertThrows(IllegalStateException.class, () -> room.occupied(middle, after));

        assertThrows(IllegalArgumentException.class, () -> new Room(roomNumber, RoomState.OCCUPIED));

        assertDoesNotThrow(() -> {
            new Room(roomNumber, RoomState.AVAILABLE);
        });
    }
}