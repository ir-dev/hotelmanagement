package at.fhv.hotelmanagement.application.api.exceptions;

public class AlreadyExistsStoreException extends Exception {

    public AlreadyExistsStoreException(Class classToStore) {
        super("Item " + classToStore.getSimpleName() + " to store already exits");
    }
}



