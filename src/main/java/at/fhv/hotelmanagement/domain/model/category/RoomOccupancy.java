package at.fhv.hotelmanagement.domain.model.category;

import at.fhv.hotelmanagement.domain.model.stay.StayId;

import java.time.LocalDate;

public class RoomOccupancy {
    // generated hibernate id
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private StayId stayId;

    // required for hibernate
    private RoomOccupancy() {}

    public RoomOccupancy(LocalDate startDate, LocalDate endDate, StayId stayId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.stayId = stayId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public StayId getStayId() {
        return this.stayId;
    }
}
