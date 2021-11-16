package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.StayStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public class Stay {
    // generated hibernate id
    private Long id;
    private StayId stayId;
    private BookingNo bookingNo;
    private StayStatus stayStatus;
    private LocalDateTime checkedInAt;
    // may be null
    private LocalDateTime checkedOutAt;

    // required for hibernate
    private Stay() {}

    public Stay(StayId stayId, BookingNo bookingNo) {
        this.stayId = stayId;
        this.bookingNo = bookingNo;
        this.stayStatus = StayStatus.CHECKED_IN;
        this.checkedInAt = LocalDateTime.now();
        this.checkedOutAt = null;
    }

    public StayId getStayId() {
        return this.stayId;
    }

    public BookingNo getBookingNo() {
        return this.bookingNo;
    }

    public StayStatus getStayStatus() {
        return this.stayStatus;
    }

    public LocalDateTime getCheckedInAt() {
        return this.checkedInAt;
    }

    public Optional<LocalDateTime> getCheckedOutAt() {
        return Optional.ofNullable(this.checkedOutAt);
    }
}
