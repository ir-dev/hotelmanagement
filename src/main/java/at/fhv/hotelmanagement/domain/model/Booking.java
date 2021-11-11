package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class Booking {
    // TODO: generate bookingNo (maybe use generated no of db; factory for generation may also be an idea..)
    private Long id;
    private BookingNo bookingNo;
    private BookingStatus bookingStatus;

    // TODO: copy to stay at check-in
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private Integer numberOfPersons;

    private Map<String, Integer> selectedCategoriesRoomCount;
    private GuestId guest;
    private PaymentInformation paymentInformation;
//    private CancellationModality cancellationModality;
//    private List<AdditionalService> additionalServices;

    //for Hibernate
    private Booking(){

    }

    public Booking(BookingNo bookingNo, BookingStatus bookingStatus, LocalDate arrivalDate, LocalDate departureDate, LocalTime arrivalTime, Integer numberOfPersons, Map<String, Integer> selectedCategoriesRoomCount, GuestId guest, PaymentInformation paymentInformation) {
        this.bookingNo = bookingNo;
        this.bookingStatus = bookingStatus;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.arrivalTime = arrivalTime;
        this.numberOfPersons = numberOfPersons;
        this.selectedCategoriesRoomCount = selectedCategoriesRoomCount;
        this.guest = guest;
        this.paymentInformation = paymentInformation;
    }

    public BookingNo getBookingNo() {
        return this.bookingNo;
    }

    public BookingStatus getBookingStatus() {
        return this.bookingStatus;
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

    public GuestId getGuestId() {
        return this.guest;
    }

    public PaymentInformation getPaymentInformation() {
        return this.paymentInformation;
    }
}
