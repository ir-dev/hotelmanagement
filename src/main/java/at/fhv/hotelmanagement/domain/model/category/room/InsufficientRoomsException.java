package at.fhv.hotelmanagement.domain.model.category.room;

public class InsufficientRoomsException extends Exception {
    public InsufficientRoomsException () {
        super("Not enough rooms available.");
    }
}
