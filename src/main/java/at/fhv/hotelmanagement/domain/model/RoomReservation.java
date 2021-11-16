package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;

public class RoomReservation {
    private Long id;
    private ReservationId reservationId;
    private LocalDate start;
    private LocalDate end;

    private RoomReservation(){}

    public RoomReservation(ReservationId reservationId, LocalDate start, LocalDate end) {
        this.reservationId = reservationId;
        this.start = start;
        this.end = end;
    }

    public ReservationId getReservationId() {
        return this.reservationId;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }
}
