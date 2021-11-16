package at.fhv.hotelmanagement.domain.model;

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
}
