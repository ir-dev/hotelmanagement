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
    private RoomReservation reservation;

    // required for hibernate
    private Room() {
    }

    public Room(RoomNumber number) {
        this.number = number;
    }

    //TODO: Cover all cases
    public boolean isAvailable(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate)) {
            System.out.println("fromDate is after toDate!");
            return false;
        } else if (fromDate.equals(toDate)) {
            System.out.println("fromDate and toDate are the same!");
            return false;
        }
        if (reservation == null) {
            return true;
        } else {
            if (toDate.isAfter(reservation.getStart()) && toDate.isBefore(reservation.getEnd()) || toDate.equals(reservation.getEnd())) {
                System.out.println("toDate is between start & end date of Room Reservation!");
                return false;
            } else if (fromDate.isAfter(reservation.getStart()) && fromDate.isBefore(reservation.getEnd()) || fromDate.equals(reservation.getStart())) {
                System.out.println("fromDate is between start & end date of Room Reservation!");
                return false;
            } else if (fromDate.isBefore(reservation.getStart()) && toDate.isAfter(reservation.getEnd())) {
                System.out.println("Room Resevation start & end date is between fromDate and toDate!");
                return false;
            }
            return true;
        }
    }


    public void addReservation(RoomReservation reservation) {
        this.reservation = reservation;
    }

    public RoomNumber getNumber() {
        return this.number;
    }

    public RoomState getState() {
        return this.state;
    }

    public RoomReservation getReservation() {
        return this.reservation;
    }
}