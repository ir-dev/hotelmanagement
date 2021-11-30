package at.fhv.hotelmanagement.domain.model;

public class InsufficientRoomsException extends Exception {
    public InsufficientRoomsException () {
        super("Not enough rooms available.");
    }
}
