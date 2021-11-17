package at.fhv.hotelmanagement.domain.model;

public class ReservationId {
    private String id;

    private ReservationId() {}

    public ReservationId(String reservationId) {
        this.id = reservationId;
    }

    public String getId() {
        return this.id;
    }
}
