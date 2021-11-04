package at.fhv.hotelmanagement.domain.model;

import org.apache.tomcat.jni.Address;

import java.util.Objects;

public class Guest {
    private final Long id;
    private String firstName;
    private String lastName;
    private Address address;


    public Guest(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Guest guest = (Guest) o;
        return Objects.equals(this.id, guest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
