package at.fhv.hotelmanagement.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public class Price {
    private BigDecimal amount;
    private Currency currency;

    // required for hibernate
    private Price() {}

    private Price(BigDecimal amount, Currency currency) {
        this.amount = amount.setScale(2, RoundingMode.UP);
        this.currency = currency;
    }

    public static Price of(BigDecimal amount, Currency currency) {
        return new Price(amount, currency);
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Price add(Price price) throws PriceCurrencyMismatchException {
        if (!this.currency.equals(price.currency)) {
            throw new PriceCurrencyMismatchException();
        }

        return new Price(this.amount.add(price.amount), this.currency);
    }

    public Price multiply(Integer multiplicand) { return new Price(this.amount.multiply(BigDecimal.valueOf(multiplicand)), this.currency); }

    public Price multiply(BigDecimal multiplicand) { return new Price(this.amount.multiply(multiplicand), this.currency); }

    @Override
    public String toString() {
        return this.amount + this.currency.getSymbol();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        return this.amount.equals(price.amount) && this.currency.equals(price.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.amount, this.currency);
    }
}
