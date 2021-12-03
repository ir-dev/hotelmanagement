package at.fhv.hotelmanagement.domain.model.category;

public class InsufficientRoomsException extends Exception {
    public InsufficientRoomsException () {
        super("Not enough rooms available.");
    }
}
