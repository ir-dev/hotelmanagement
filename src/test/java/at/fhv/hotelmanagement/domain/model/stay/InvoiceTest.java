package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest extends AbstractTest {
    @Test
    void given_emptyinvoicedetails_when_createemptyinvoice_then_returnequalsdetails() throws PriceCurrencyMismatchException {
        // given
        InvoiceNo invoiceNo = new InvoiceNo("1");
        Set<InvoiceLine> lineItems = new HashSet<>();
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = arrivalDate.plusDays(5L);
        double taxRate = 0.1;
        long dueDateDays = 14L;

        // when
        Invoice invoice = new Invoice(invoiceNo, lineItems, arrivalDate, departureDate, taxRate, dueDateDays);

        // then
        assertEquals(invoiceNo, invoice.getInvoiceNo());
        assertEquals(LocalDate.now(), invoice.getCreatedDate());
        assertEquals(LocalDate.now().plusDays(dueDateDays), invoice.getDueDate());
        assertEquals(5, invoice.getNights());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice.getSubTotalPerNight());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice.getSubTotal());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice.getGrandTotal());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice.getTax());
        assertEquals(lineItems.size(), invoice.getLineItems().size());
        for (InvoiceLine il : invoice.getLineItems()) {
            assertTrue(lineItems.contains(il));
        }
    }

    @Test
    void given_invoicedetails_when_createinvoice_then_returnequalsdetails() throws PriceCurrencyMismatchException {
        // given
        InvoiceNo invoiceNo = new InvoiceNo("1");
        Set<InvoiceLine> lineItems = new HashSet<>();
        lineItems.add(new InvoiceLine(ProductType.CATEGORY, "Honeymoon Suite EZ", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr", 2, Price.of(BigDecimal.ONE, Currency.getInstance("EUR"))));
        lineItems.add(new InvoiceLine(ProductType.CATEGORY, "Honeymoon Suite DZ", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr", 1, Price.of(BigDecimal.TEN, Currency.getInstance("EUR"))));
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = arrivalDate.plusDays(5L);
        double taxRate = 0.1;
        long dueDateDays = 14L;

        // when
        Invoice invoice = new Invoice(invoiceNo, lineItems, arrivalDate, departureDate, taxRate, dueDateDays);

        // then
        assertEquals(invoiceNo, invoice.getInvoiceNo());
        assertEquals(LocalDate.now(), invoice.getCreatedDate());
        LocalDate dueDateExpected = LocalDate.now().plusDays(dueDateDays);
        Integer nightsExpected = (int) DAYS.between(arrivalDate, departureDate);
        BigDecimal expectedSubTotalPerNight = BigDecimal.valueOf(12L);
        BigDecimal expectedSubTotal = expectedSubTotalPerNight.multiply(BigDecimal.valueOf(nightsExpected));
        BigDecimal expectedTax = expectedSubTotal.multiply(BigDecimal.valueOf(taxRate));
        BigDecimal expectedGrandTotal = expectedSubTotal.add(expectedTax);
        assertEquals(dueDateExpected, invoice.getDueDate());
        assertEquals(nightsExpected, invoice.getNights());
        assertEquals(Price.of(expectedSubTotalPerNight, Currency.getInstance("EUR")), invoice.getSubTotalPerNight());
        assertEquals(Price.of(expectedSubTotal, Currency.getInstance("EUR")), invoice.getSubTotal());
        assertEquals(Price.of(expectedGrandTotal, Currency.getInstance("EUR")), invoice.getGrandTotal());
        assertEquals(Price.of(expectedTax, Currency.getInstance("EUR")), invoice.getTax());
        assertEquals(lineItems.size(), invoice.getLineItems().size());
        for (InvoiceLine il : invoice.getLineItems()) {
            assertTrue(lineItems.contains(il));
        }
    }
}