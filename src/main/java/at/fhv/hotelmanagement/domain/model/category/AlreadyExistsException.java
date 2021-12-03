package at.fhv.hotelmanagement.domain.model.category;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException (Class<?> clazz) {
        super(String.format("Item of type '%s' already exists", clazz.getSimpleName()));
    }

    public AlreadyExistsException (Class<?> clazz, String duplicateName, String duplicateValue) {
        super(String.format("Item of type '%s' already exists for '%s' with duplicate value '%s'", clazz.getSimpleName(), duplicateName, duplicateValue));
    }
}
