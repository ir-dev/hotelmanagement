package at.fhv.hotelmanagement.domain.model;

public class InsufficientRoomsException extends Exception {
    public InsufficientRoomsException (String categoryName) {
        super("Not enough rooms available for category: " + categoryName);
    }
}
