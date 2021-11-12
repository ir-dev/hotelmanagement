package at.fhv.hotelmanagement.domain.infrastructure;

public class AlreadyExistsStoreException extends Exception {

    public AlreadyExistsStoreException(Class classToStore) {
        super("Item " + classToStore.getSimpleName() + " to store already exits");
    }
}



