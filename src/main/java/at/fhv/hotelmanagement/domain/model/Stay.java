package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.StayStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

public class Stay {
    // generated hibernate id
    private Long id;
    private StayId stayId;
    private BookingNo bookingNo;
    private StayStatus stayStatus;
    // TODO: checkedInAt <=> arrivalDate + arrivalTime (remove duplicate?!)
    private LocalDateTime checkedInAt;
    // may be null
    private LocalDateTime checkedOutAt;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private Integer numberOfPersons;
    private Map<String, Integer> selectedCategoriesRoomCount;
    private GuestId guestId;
    private PaymentInformation paymentInformation;

    // required for hibernate
    private Stay() {}

    Stay(StayId stayId, BookingNo bookingNo, LocalDate arrivalDate, LocalDate departureDate, LocalTime arrivalTime, Integer numberOfPersons, Map<String, Integer> selectedCategoriesRoomCount, GuestId guestId, PaymentInformation paymentInformation) {
        this.stayId = stayId;
        this.bookingNo = bookingNo;
        this.stayStatus = StayStatus.CHECKED_IN;
        this.checkedInAt = LocalDateTime.now();
        this.checkedOutAt = null;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.arrivalTime = arrivalTime;
        this.numberOfPersons = numberOfPersons;
        this.selectedCategoriesRoomCount = selectedCategoriesRoomCount;
        this.guestId = guestId;
        this.paymentInformation = paymentInformation;
    }

    public StayId getStayId() {
        return this.stayId;
    }

    public Optional<BookingNo> getBookingNo() {
        return Optional.ofNullable(this.bookingNo);
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

    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return this.departureDate;
    }

    public LocalTime getArrivalTime() {
        return this.arrivalTime;
    }

    public Integer getNumberOfPersons() {
        return this.numberOfPersons;
    }

    public Map<String, Integer> getSelectedCategoriesRoomCount() {
        return this.selectedCategoriesRoomCount;
    }

    public Integer getNumberOfBookedRooms() {
        return selectedCategoriesRoomCount.values().stream().mapToInt(i->i).sum();
    }

    public GuestId getGuestId() {
        return this.guestId;
    }

    public PaymentInformation getPaymentInformation() {
        return this.paymentInformation;
    }
}
