package at.fhv.hotelmanagement.domain.model;

public class BookingNo {
    private String id;

    public BookingNo() {

    }

    public BookingNo(String bookingNo) {
        this.id = bookingNo;
    }

    public String getBookingNo() {
        return this.id;
    }
}
