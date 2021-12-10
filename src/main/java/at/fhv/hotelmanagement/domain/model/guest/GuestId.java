package at.fhv.hotelmanagement.domain.model.guest;

import java.util.Objects;

public class GuestId {
    private String id;

    // required for hibernate
    private GuestId() {}

    public GuestId(String guestId) {
        this.id = guestId;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GuestId guestId = (GuestId) o;
        return Objects.equals(this.id, guestId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
