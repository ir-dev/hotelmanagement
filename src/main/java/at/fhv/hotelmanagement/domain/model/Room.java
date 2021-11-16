package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.RoomState;

import java.time.LocalDate;
import java.util.Optional;

public class Room {
    // generated hibernate id
    private Long id;
    private RoomNumber number;
    private RoomState state;
    //TODO: Adjust to ReservationId - Look at Factory Pattern and Domain Services
    private Optional<RoomReservation> reservation;

    // required for hibernate
    private Room() {}

    public Room(RoomNumber number){
        this.number = number;
    }


    //TODO: Cover all cases
    public boolean isAvailable(LocalDate fromDate, LocalDate toDate) {
        if(fromDate.isAfter(toDate)){
            System.out.println("fromDate is after toDate!");
            return false;
        }else if(fromDate.equals(toDate)){
            System.out.println("fromDate and toDate are the same!");
            return false;
        }

        if(reservation.isEmpty()){
            return true;
        }else{
            RoomReservation res = reservation.get();

            if(toDate.isAfter(res.getStart()) && toDate.isBefore(res.getEnd())){
                System.out.println("toDate is between start & end date of Room Reservation!");
                return false;
            }else if(fromDate.isAfter(res.getStart()) && fromDate.isBefore(res.getEnd())){
                System.out.println("fromDate is between start & end date of Room Reservation!");
                return false;
            }else if(fromDate.isBefore(res.getStart()) && toDate.isAfter(res.getEnd())){
                System.out.println("Room Resevation start & end date is between fromDate and toDate!");
                return false;
            }
            return true;
        }
    }

    public void addReservation(RoomReservation reservation){
        this.reservation = Optional.ofNullable(reservation);
    }

    public RoomNumber getNumber() {
        return this.number;
    }

    public RoomState getState() {
        return this.state;
    }

    public Optional<RoomReservation> getReservation() {
        return this.reservation;
    }
}