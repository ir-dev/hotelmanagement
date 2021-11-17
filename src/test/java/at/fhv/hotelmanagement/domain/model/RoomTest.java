package at.fhv.hotelmanagement.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void isNotAvailable() {
        RoomReservation rr = new RoomReservation(new ReservationId("1234"), LocalDate.of(2021,10,20), LocalDate.of(2021,10,22));
        Room r1 = new Room(new RoomNumber("200"));
        r1.addReservation(rr);

        LocalDate fromDate = LocalDate.of(2021,10,20);
        LocalDate toDate = LocalDate.of(2021,10,21);

        assertEquals(r1.isAvailable(fromDate, toDate), false);
    }
}