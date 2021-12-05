package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.stay.Invoice;
import at.fhv.hotelmanagement.domain.model.stay.InvoiceId;
import at.fhv.hotelmanagement.domain.model.stay.InvoiceLine;
import at.fhv.hotelmanagement.domain.model.stay.InvoiceState;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    @Test
    void given_details_when_create_invoice_then_returnequalsinvoice() {
        // given
        InvoiceId invoiceId = new InvoiceId("1");
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);

        // when
        Invoice invoice = new Invoice(invoiceId, arrivalDate, departureDate);

        // then
        assertEquals(invoiceId, invoice.getInvoiceId());
        assertEquals(arrivalDate, invoice.getCreatedDate());
        assertEquals(departureDate, invoice.getDueDate());
    }

    @Test
    void given_invoice_when_create_invoice_then_invoicestate_pending() {
        //given
        InvoiceId invoiceId = new InvoiceId("1");
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);

        //when
        Invoice invoice = new Invoice(invoiceId, arrivalDate, departureDate);

        //then
        assertEquals(invoice.getInvoiceState(), InvoiceState.PENDING);
    }

    @Test
    void given_invoice_when_invoice_close_then_invoicestate_confirmed() {
        //given
        InvoiceId invoiceId = new InvoiceId("1");
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        Invoice invoice = new Invoice(invoiceId, arrivalDate, departureDate);

        //when
        invoice.close();

        //then
        assertEquals(invoice.getInvoiceState(), InvoiceState.CONFIRMED);
    }


    @Test
    void given_invoice_and_invoiceLine_when_addLineItem_then_equals() {
        //given
        InvoiceId invoiceId = new InvoiceId("1");
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(1);
        Invoice invoice = new Invoice(invoiceId, arrivalDate, departureDate);

        //when
        invoice.addLineItem(new InvoiceLine("Business EZ", "For Business Guests", 2, 100));

        //then
        assertEquals(invoice.getSubTotal(), 200);
        assertEquals(invoice.getTax(), 20);
        assertEquals(invoice.getGrandTotal(), 220);
    }





}