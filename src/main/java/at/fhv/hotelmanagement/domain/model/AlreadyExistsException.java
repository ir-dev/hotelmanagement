package at.fhv.hotelmanagement.domain.model;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException (Class<?> clazz) {
        super(String.format("Item of type '%s' already exists", clazz.getSimpleName()));
    }
}
