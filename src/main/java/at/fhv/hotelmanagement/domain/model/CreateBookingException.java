package at.fhv.hotelmanagement.domain.model;

public class CreateBookingException extends ValidationException {
    public CreateBookingException(String message) {
        super(message);
    }
}
