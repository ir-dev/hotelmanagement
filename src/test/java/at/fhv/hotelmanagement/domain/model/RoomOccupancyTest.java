package at.fhv.hotelmanagement.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomOccupancyTest {
    @Test
    void given_roomdetails_when_createroom_then_detailsequals() {
        //given
        LocalDate startDate = LocalDate.of(2021,11,7);
        LocalDate endDate = LocalDate.of(2021, 11, 10);

        //when
        RoomOccupancy roomOccupancy = new RoomOccupancy(startDate,endDate);

        //then
        assertEquals(roomOccupancy.getStartDate(), startDate);
        assertEquals(roomOccupancy.getEndDate(), endDate);
    }
}