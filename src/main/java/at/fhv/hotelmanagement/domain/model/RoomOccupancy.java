package at.fhv.hotelmanagement.domain.model;

import java.time.LocalDate;

public class RoomOccupancy {
    private Long id;
    private OccupancyId occupancyId;
    private LocalDate start;
    private LocalDate end;
    private RoomNumber room;

    // required for hibernate
    private RoomOccupancy(){}

    public RoomOccupancy(OccupancyId occupancyId, RoomNumber room, LocalDate start, LocalDate end) {
        this.occupancyId = occupancyId;
        this.room = room;
        this.start = start;
        this.end = end;
    }

    public OccupancyId getOccupancyId() {
        return this.occupancyId;
    }

    public LocalDate getStart() {
        return this.start;
    }

    public LocalDate getEnd() {
        return this.end;
    }

    public RoomNumber getRoom() {
        return this.room;
    }
}
