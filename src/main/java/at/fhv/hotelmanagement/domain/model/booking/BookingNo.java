package at.fhv.hotelmanagement.domain.model.booking;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookingNo bookingNo = (BookingNo) o;
        return Objects.equals(this.no, bookingNo.no);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.no);
    }
}
