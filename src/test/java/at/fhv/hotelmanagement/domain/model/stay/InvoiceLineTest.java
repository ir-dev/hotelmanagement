package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.guest.Address;
import at.fhv.hotelmanagement.domain.model.guest.Country;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class InvoiceLineTest extends AbstractTest {
    @Test
    void given_invoicelinedetails_when_createinvoiceline_then_returnequalsdetails() {
        //given
        ProductType type = ProductType.CATEGORY;
        String product = "Honeymoon Suite EZ";
        String description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr";
        Integer quantity = 2;
        Price price = Price.of(BigDecimal.TEN, Currency.getInstance("EUR"));

        //when
        InvoiceLine invoiceLine = new InvoiceLine(type, product, description, quantity, price);

        //then
        assertEquals(invoiceLine.getType(), type);
        assertEquals(invoiceLine.getProduct(), product);
        assertEquals(invoiceLine.getDescription(), description);
        assertEquals(invoiceLine.getQuantity(), quantity);
        assertEquals(invoiceLine.getPrice(), price);
    }
}
