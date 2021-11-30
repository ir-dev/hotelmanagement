package at.fhv.hotelmanagement.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    @Test
    void given_currentdate_when_createinvoice_then_contractdateequals() {
        // given
        InvoiceId invoiceId = new InvoiceId("1");
        LocalDate currentDate = LocalDate.now();
        // when
        Invoice invoice = new Invoice(invoiceId);
        // then
        assertEquals(currentDate, invoice.getContractDate());
        assertEquals(invoiceId, invoice.getInvoiceId());
    }

}