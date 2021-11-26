package at.fhv.hotelmanagement.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    @Test
    void given_invoicedetails_when_createinvoice_then_detailsequals() {
        // given
        InvoiceId invoiceId = new InvoiceId("XA21");
        StayId stayId = new StayId("1");

        // when
        Invoice invoice = Invoice.create(invoiceId, stayId);

        // then
        assertEquals(invoiceId, invoice.getInvoiceId());
        assertEquals(stayId, invoice.getStayId());
    }







}