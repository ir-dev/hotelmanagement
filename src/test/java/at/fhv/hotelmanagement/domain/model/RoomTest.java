package at.fhv.hotelmanagement.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void isAvailable() {
        RoomReservation rr = new RoomReservation(new ReservationId("1234"), LocalDate.of(2021,10,20), LocalDate.of(2021,10,22));
        Room r1 = new Room(new RoomNumber("200"));
        r1.addReservation(rr);

        LocalDate fromDate = LocalDate.of(2021,10,19);
        LocalDate toDate = LocalDate.of(2021,10,20);

        assertEquals(r1.isAvailable(fromDate, toDate), true);
    }

    @Test
    void isAvailable2() {
        RoomReservation rr = new RoomReservation(new ReservationId("1234"), LocalDate.of(2021,10,20), LocalDate.of(2021,10,22));
        Room r1 = new Room(new RoomNumber("200"));
        r1.addReservation(rr);

        LocalDate fromDate = LocalDate.of(2021,10,22);
        LocalDate toDate = LocalDate.of(2021,10,24);

        assertEquals(r1.isAvailable(fromDate, toDate), true);
    }

    @Test
    void isNotAvailable1() {
        RoomReservation rr = new RoomReservation(new ReservationId("1234"), LocalDate.of(2021,10,20), LocalDate.of(2021,10,22));
        Room r1 = new Room(new RoomNumber("200"));
        r1.addReservation(rr);

        LocalDate fromDate = LocalDate.of(2021,10,19);
        LocalDate toDate = LocalDate.of(2021,10,22);

        assertEquals(r1.isAvailable(fromDate, toDate), false);
    }

    @Test
    void isNotAvailable2() {
        RoomReservation rr = new RoomReservation(new ReservationId("1234"), LocalDate.of(2021,10,20), LocalDate.of(2021,10,22));
        Room r1 = new Room(new RoomNumber("200"));
        r1.addReservation(rr);

        LocalDate fromDate = LocalDate.of(2021,10,19);
        LocalDate toDate = LocalDate.of(2021,10,21);

        assertEquals(r1.isAvailable(fromDate, toDate), false);
    }

    @Test
    void isNotAvailable3() {
        RoomReservation rr = new RoomReservation(new ReservationId("1234"), LocalDate.of(2021,10,20), LocalDate.of(2021,10,22));
        Room r1 = new Room(new RoomNumber("200"));
        r1.addReservation(rr);

        LocalDate fromDate = LocalDate.of(2021,10,21);
        LocalDate toDate = LocalDate.of(2021,10,19);

        assertEquals(r1.isAvailable(fromDate, toDate), false);
    }

    @Test
    void isNotAvailable4() {
        RoomReservation rr = new RoomReservation(new ReservationId("1234"), LocalDate.of(2021,10,20), LocalDate.of(2021,10,22));
        Room r1 = new Room(new RoomNumber("200"));
        r1.addReservation(rr);

        LocalDate fromDate = LocalDate.of(2021,10,20);
        LocalDate toDate = LocalDate.of(2021,10,23);

        assertEquals(r1.isAvailable(fromDate, toDate), false);
    }




}