package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;

public class Room {
    private Long id;
    private String number;

    public Room(){}

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
