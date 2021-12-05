package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestIdTest {
    @Test
    void given_3guestId_2equals_1not_when_compare_then_2equals_1not() {
        // given
        GuestId id0_1 = new GuestId("0");
        GuestId id0_2 = new GuestId("0");
        GuestId id1 = new GuestId("1");

        // when...then
        assertNotEquals(id0_1, id1);
        assertNotEquals(id0_2, id1);
        assertEquals(id0_1, id0_2, "both GuestIds should be equals");

        assertEquals("1", id1.getId());
    }
}
