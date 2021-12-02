package at.fhv.hotelmanagement.domain.model.validation;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
