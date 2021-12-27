package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(type, invoiceLine.getType());
        assertEquals(product, invoiceLine.getProduct());
        assertEquals(description, invoiceLine.getDescription());
        assertEquals(quantity, invoiceLine.getQuantity());
        assertEquals(price, invoiceLine.getPrice());
    }
}
