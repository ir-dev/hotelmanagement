package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    @Test
    void given_roomdetails_when_createroom_then_detailsequals() {
        //given
        RoomNumber roomNumber = new RoomNumber("100");
        RoomState roomState = RoomState.AVAILABLE;

        //when
        Room room = new Room(roomNumber, roomState);
        room.occupied(LocalDate.of(2021, 12, 12), LocalDate.of(2021, 12, 24));

        //then
        assertEquals(room.getRoomNumber(), roomNumber);

        // test overlap beginning
        assertThrows(IllegalStateException.class, () -> room.occupied(LocalDate.of(2021, 12, 10), LocalDate.of(2021, 12, 14)));

        // test overlap whole
        assertThrows(IllegalStateException.class, () -> room.occupied(LocalDate.of(2021, 12, 12), LocalDate.of(2021, 12, 24)));

        // test overlap ending
        assertThrows(IllegalStateException.class, () -> room.occupied(LocalDate.of(2021, 12, 22), LocalDate.of(2021, 12, 28)));

        assertThrows(IllegalArgumentException.class, () -> new Room(roomNumber, RoomState.OCCUPIED));

        assertDoesNotThrow(() -> {
            new Room(roomNumber, RoomState.AVAILABLE);
        });

        // TODO: test getRoomOccupancies ?
    }
}