package at.fhv.hotelmanagement.domain.model.category.room;

public class RoomAssignmentException extends Exception {
    public RoomAssignmentException (String categoryName, String message) {
        super("Room assignment for category '" +categoryName+ "' failed because: " +message);
    }
}
