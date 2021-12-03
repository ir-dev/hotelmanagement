package at.fhv.hotelmanagement.domain.model;

import java.util.Objects;

public class RoomNumber {
    private String number;

    // required for hibernate
    private RoomNumber() {}

    public RoomNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomNumber that = (RoomNumber) o;
        return Objects.equals(this.number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number);
    }
}
