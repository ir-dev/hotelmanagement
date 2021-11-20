package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.RoomState;

import java.time.LocalDate;


public class Room {
    // generated hibernate id
    private Long id;
    private RoomNumber number;
    private RoomState roomState;

    // required for hibernate
    private Room() {}

    public Room(RoomNumber number) {
        this.number = number;
        //When Creating a Room, RoomState is on Available
        this.roomState = RoomState.AVAILABLE;
    }

    //TODO: Cover all cases
    public boolean isAvailable(LocalDate fromDate, LocalDate toDate) {
       return true;
    }

    public void setRoomState(RoomState roomState) {
        this.roomState = roomState;
    }

    public RoomNumber getNumber() {
        return this.number;
    }

    public RoomState getState() {
        return this.roomState;
    }

}