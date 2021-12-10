package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.AbstractTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceTest extends AbstractTest {
    @Test
    void given_pricedetails_when_createprice_then_returnequalsdetails() {
        // given
        BigDecimal amount = BigDecimal.valueOf(10L).setScale(2, RoundingMode.UP);
        Currency currency = Currency.getInstance("EUR");

        // when
        Price price = Price.of(BigDecimal.valueOf(10L), Currency.getInstance("EUR"));

        // then
        assertEquals(amount, price.getAmount());
        assertEquals(currency, price.getCurrency());
    }

    @Test
    void given_price_when_addprice_then_returnequalssum() throws PriceCurrencyMismatchException {
        // given
        Price price = Price.of(BigDecimal.valueOf(10L), Currency.getInstance("EUR"));
        Price expectedPrice = Price.of(BigDecimal.valueOf(25L), Currency.getInstance("EUR"));

        // when
        Price actualPrice = price.add(Price.of(BigDecimal.valueOf(15L), Currency.getInstance("EUR")));

        // then
        assertEquals(expectedPrice.getAmount(), actualPrice.getAmount());
        assertEquals(expectedPrice.getCurrency(), actualPrice.getCurrency());
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void given_price_when_multiplypricebyintegermultiplicand_then_returnequalsproduct() {
        // given
        Price price = Price.of(BigDecimal.valueOf(10.25), Currency.getInstance("EUR"));
        Price expectedPrice = Price.of(BigDecimal.valueOf(51.25), Currency.getInstance("EUR"));

        // when
        Price actualPrice = price.multiply(5);

        // then
        assertEquals(expectedPrice.getAmount(), actualPrice.getAmount());
        assertEquals(expectedPrice.getCurrency(), actualPrice.getCurrency());
        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void given_price_when_multiplypricebybigdecimalmultiplicand_then_returnequalsproduct() {
        // given
        Price price = Price.of(BigDecimal.valueOf(10.25), Currency.getInstance("EUR"));
        // exact expectedPrice amount = 1.5375 (but rounded up to 2 decimals) = 1.54
        Price expectedPrice = Price.of(BigDecimal.valueOf(1.54), Currency.getInstance("EUR"));

        // when
        Price actualPrice = price.multiply(BigDecimal.valueOf(0.15));

        // then
        assertEquals(expectedPrice.getAmount(), actualPrice.getAmount());
        assertEquals(expectedPrice.getCurrency(), actualPrice.getCurrency());
        assertEquals(expectedPrice, actualPrice);
    }
}
