package at.fhv.hotelmanagement.domain.model;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
