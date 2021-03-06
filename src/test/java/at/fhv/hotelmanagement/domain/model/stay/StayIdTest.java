package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StayIdTest extends AbstractTest {
    @Test
    void given_3stayId_2equals_1not_when_compare_then_2equals_1not() {
        //given
        StayId id0_1 = new StayId("0");
        StayId id0_2 = new StayId("0");
        StayId id1 = new StayId("1");

        //when...then
        assertNotEquals(id0_1, id1);
        assertNotEquals(id0_2, id1);
        assertEquals(id0_1, id0_2, "both StayIds should be equals");

        assertEquals("1", id1.getId());
    }
}