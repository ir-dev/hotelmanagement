package at.fhv.hotelmanagement.domain.model;

import org.apache.tomcat.jni.Address;

public class Guest {
    private final Long id;
    private String firstName;
    private String lastName;
    private Address address;


    public Guest(Long id) {
        this.id = id;
    }
}
