package at.fhv.hotelmanagement.domain.model;

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
}
