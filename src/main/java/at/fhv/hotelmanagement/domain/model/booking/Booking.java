package at.fhv.hotelmanagement.domain.model.booking;

import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class Booking {
    // generated hibernate id
    private Long id;
    private BookingNo bookingNo;
    private BookingState bookingState;
    // TODO: copy to stay at check-in (maybe we shall use a value object for this purpose)
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private Integer numberOfPersons;
    private Map<String, Integer> selectedCategoriesRoomCount;
    private GuestId guestId;
    private PaymentInformation paymentInformation;

    // required for hibernate
    private Booking() {}

    Booking(BookingNo bookingNo, LocalDate arrivalDate, LocalDate departureDate, LocalTime arrivalTime, Integer numberOfPersons, Map<String, Integer> selectedCategoriesRoomCount, GuestId guestId, PaymentInformation paymentInformation) {
        this.bookingNo = bookingNo;
        this.bookingState = BookingState.PENDING;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.arrivalTime = arrivalTime;
        this.numberOfPersons = numberOfPersons;
        this.selectedCategoriesRoomCount = selectedCategoriesRoomCount;
        this.guestId = guestId;
        this.paymentInformation = paymentInformation;
    }

    public void close() {
        if (this.bookingState == BookingState.PENDING) {
            this.bookingState = BookingState.CLOSED;
        } else {
            throw new IllegalStateException("Only booking with PENDING status can be closed.");
        }
    }

    public BookingNo getBookingNo() {
        return this.bookingNo;
    }

    public BookingState getBookingState() {
        return this.bookingState;
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
        return this.selectedCategoriesRoomCount.values().stream().mapToInt(i->i).sum();
    }

    public GuestId getGuestId() {
        return this.guestId;
    }

    public PaymentInformation getPaymentInformation() {
        return this.paymentInformation;
    }
}
