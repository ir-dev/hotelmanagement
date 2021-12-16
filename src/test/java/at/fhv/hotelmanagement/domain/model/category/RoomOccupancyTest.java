package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.category.RoomOccupancy;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomOccupancyTest extends AbstractTest {
    @Test
    void given_roomdetails_when_createroom_then_detailsequals() {
        //given
        LocalDate startDate = getContextLocalDate();
        LocalDate endDate = getContextLocalDate().plusDays(3L);

        //when
        RoomOccupancy roomOccupancy = new RoomOccupancy(startDate, endDate, new StayId("1"));

        //then
        assertEquals(roomOccupancy.getStartDate(), startDate);
        assertEquals(roomOccupancy.getEndDate(), endDate);
    }
}