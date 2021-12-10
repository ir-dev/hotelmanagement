package at.fhv.hotelmanagement.domain.model;

public class PriceCurrencyMismatchException extends Exception {
    public PriceCurrencyMismatchException() {
        super("Operations on different currencies are not supported.");
    }
}
