package at.fhv.hotelmanagement.domain.model;

public class OccupancyId {
    private String id;

    private OccupancyId() {}

    public OccupancyId(String occupancyId) {
        this.id = occupancyId;
    }

    public String getId() {
        return this.id;
    }
}
