package at.fhv.hotelmanagement.domain.model;

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
}
