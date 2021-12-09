package at.fhv.hotelmanagement.domain.model.validators;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
