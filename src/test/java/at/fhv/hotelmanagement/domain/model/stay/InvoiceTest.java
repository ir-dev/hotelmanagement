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
import java.util.Optional;
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
        BigDecimal discountRate = BigDecimal.valueOf(0.25);
        double taxRate = 0.1;

        // when
        Invoice invoice1 = new Invoice(invoiceNo, lineItems, arrivalDate, departureDate, discountRate, taxRate);

        // then
        assertEquals(invoiceNo, invoice1.getInvoiceNo());
        assertEquals(LocalDate.now(), invoice1.getCreatedDate());
        assertEquals(5, invoice1.getNights());
        assertEquals(discountRate, invoice1.getDiscountRate());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice1.getSubTotalPerNight());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice1.getSubTotal());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice1.getDiscountAmount());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice1.getSubTotalDiscounted());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice1.getGrandTotal());
        assertEquals(Price.of(BigDecimal.ZERO, Currency.getInstance("EUR")), invoice1.getTax());
        assertEquals(lineItems.size(), invoice1.getLineItems().size());
        for (InvoiceLine il : invoice1.getLineItems()) {
            assertTrue(lineItems.contains(il));
        }
    }

    @Test
    void given_invoicedetails_when_createinvoice_then_returnequalsdetails() throws PriceCurrencyMismatchException {
        // given
        InvoiceNo invoiceNo = new InvoiceNo("2");
        Set<InvoiceLine> lineItems = new HashSet<>();
        lineItems.add(new InvoiceLine(ProductType.CATEGORY, "Honeymoon Suite EZ", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr", 2, Price.of(BigDecimal.ONE, Currency.getInstance("EUR"))));
        lineItems.add(new InvoiceLine(ProductType.CATEGORY, "Honeymoon Suite DZ", "Lorem ipsum dolor sit amet, consetetur sadipscing elitr", 1, Price.of(BigDecimal.TEN, Currency.getInstance("EUR"))));
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = arrivalDate.plusDays(5L);
        BigDecimal discountRate = BigDecimal.valueOf(0.25);
        double taxRate = 0.1;

        // when
        Invoice invoice = new Invoice(invoiceNo, lineItems, arrivalDate, departureDate, discountRate, taxRate);

        // then
        assertEquals(invoiceNo, invoice.getInvoiceNo());
        assertEquals(LocalDate.now(), invoice.getCreatedDate());
        Integer nightsExpected = (int) DAYS.between(arrivalDate, departureDate);

        BigDecimal expectedSubTotalPerNight = BigDecimal.valueOf(12L);
        BigDecimal expectedSubTotal = expectedSubTotalPerNight.multiply(BigDecimal.valueOf(nightsExpected));
        BigDecimal expectedDiscountAmount = expectedSubTotal.multiply(discountRate);
        BigDecimal expectedSubTotalDiscounted = expectedSubTotal.add(expectedDiscountAmount.multiply(BigDecimal.valueOf(-1)));
        BigDecimal expectedTax = expectedSubTotalDiscounted.multiply(BigDecimal.valueOf(taxRate));
        BigDecimal expectedGrandTotal = expectedSubTotalDiscounted.add(expectedTax);

        assertEquals(nightsExpected, invoice.getNights());
        assertEquals(Price.of(expectedSubTotalPerNight, Currency.getInstance("EUR")), invoice.getSubTotalPerNight());
        assertEquals(Price.of(expectedSubTotal, Currency.getInstance("EUR")), invoice.getSubTotal());

        assertEquals(Price.of(expectedDiscountAmount, Currency.getInstance("EUR")), invoice.getDiscountAmount());
        assertEquals(Price.of(expectedSubTotalDiscounted, Currency.getInstance("EUR")), invoice.getSubTotalDiscounted());

        assertEquals(Price.of(expectedGrandTotal, Currency.getInstance("EUR")), invoice.getGrandTotal());
        assertEquals(Price.of(expectedTax, Currency.getInstance("EUR")), invoice.getTax());
        assertEquals(lineItems.size(), invoice.getLineItems().size());
        for (InvoiceLine il : invoice.getLineItems()) {
            assertTrue(lineItems.contains(il));
        }
    }
}