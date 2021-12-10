package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InvoiceNoTest extends AbstractTest {
    @Test
    void given_3invoiceNos_2equals_1not_when_compare_then_2equals_1not() {
        // given
        InvoiceNo id0_1 = new InvoiceNo("0");
        InvoiceNo id0_2 = new InvoiceNo("0");
        InvoiceNo id1 = new InvoiceNo("1");

        // when...then
        assertNotEquals(id0_1, id1);
        assertNotEquals(id0_2, id1);
        assertEquals(id0_1, id0_2, "both InvoiceNos should be equals");

        assertEquals("1", id1.getNo());
    }
}
