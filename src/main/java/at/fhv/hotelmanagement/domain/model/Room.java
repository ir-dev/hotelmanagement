package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;

public class Room {
    // generated hibernate id
    private Long id;
    private String number;

    // required for hibernate
    private Room() {}

    public Room(String number){
        this.number = number;
    }

    // TODO: implement using rooms relation to RoomReservation
    public boolean isAvailable(LocalDate fromDate, LocalDate toDate) {
        return true;
    }

    public String getNumber() {
        return this.number;
    }
}
