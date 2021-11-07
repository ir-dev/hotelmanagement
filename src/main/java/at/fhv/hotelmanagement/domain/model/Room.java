package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;

public class Room {
    // TODO: implement using rooms relation to RoomReservation
    public boolean isAvailable(LocalDate fromDate, LocalDate toDate) {
        return true;
    }
}
