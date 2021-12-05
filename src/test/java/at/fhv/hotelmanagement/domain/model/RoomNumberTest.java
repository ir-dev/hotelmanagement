package at.fhv.hotelmanagement.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomNumberTest {
    @Test
    void given_3roomNumbers_2equals_1not_when_compare_then_2equals_1not() {
        // given
        RoomNumber id0_1 = new RoomNumber("0");
        RoomNumber id0_2 = new RoomNumber("0");
        RoomNumber id1 = new RoomNumber("1");

        // when...then
        assertNotEquals(id0_1, id1);
        assertNotEquals(id0_2, id1);
        assertEquals(id0_1, id0_2, "both RoomNumbers should be equals");

        assertEquals("1", id1.getNumber());
    }
}