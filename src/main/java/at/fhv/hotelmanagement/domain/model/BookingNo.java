package at.fhv.hotelmanagement.domain.model;

public class BookingNo {
    private String no;

    // required for hibernate
    private BookingNo() {}

    public BookingNo(String bookingNo) {
        this.no = bookingNo;
    }

    public String getNo() {
        return this.no;
    }
}
