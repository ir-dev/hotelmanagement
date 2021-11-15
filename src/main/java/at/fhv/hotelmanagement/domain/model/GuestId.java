package at.fhv.hotelmanagement.domain.model;

public class GuestId {
    private String id;

    private GuestId() {}

    public GuestId(String guestId) {
        this.id = guestId;
    }

    public String getId() {
        return this.id;
    }
}
