package at.fhv.hotelmanagement.domain.model.booking;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookingNoTest extends AbstractTest {
    @Test
    void given_3bookingNos_2equals_1not_when_compare_then_2equals_1not() {
        // given
        BookingNo id0_1 = new BookingNo("0");
        BookingNo id0_2 = new BookingNo("0");
        BookingNo id1 = new BookingNo("1");

        // when...then
        assertNotEquals(id0_1, id1);
        assertNotEquals(id0_2, id1);
        assertEquals(id0_1, id0_2, "both BookingNos should be equals");

        assertEquals("1", id1.getNo());
    }
}
