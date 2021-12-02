package at.fhv.hotelmanagement.domain.model.category.room;

import java.time.LocalDate;

public class RoomOccupancy {
    // generated hibernate id
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;

    // required for hibernate
    private RoomOccupancy() {}

    public RoomOccupancy(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
}
