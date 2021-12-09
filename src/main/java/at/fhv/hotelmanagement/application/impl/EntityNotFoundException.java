package at.fhv.hotelmanagement.application.impl;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(Class<?> clazz, String entityId) {
        super(clazz.getSimpleName() + " with id.: " + entityId + " not found");
    }
}
