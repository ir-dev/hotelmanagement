package at.fhv.hotelmanagement.domain.model.category;

public class RoomAlreadyExistsException extends Exception {
    public RoomAlreadyExistsException(Class<?> clazz) {
        super(String.format("Item of type '%s' already exists", clazz.getSimpleName()));
    }
}
