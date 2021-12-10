package at.fhv.hotelmanagement.domain.model.stay;

import java.util.Objects;

public class StayId {
    private String id;

    // required for hibernate
    private StayId() {}

    public StayId(String stayId) {
        this.id = stayId;
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
        StayId stayId = (StayId) o;
        return Objects.equals(this.id, stayId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
