package at.fhv.hotelmanagement.domain.model;

public class Address {
    private String street;
    private Integer number;
    private String zipCode;
    private String city;
    private String state;

    public Address(String street, Integer number, String zipCode, String city, String state) {
        this.street = street;
        this.number = number;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    public String street() {
        return this.street;
    }

    public Integer number() {
        return this.number;
    }

    public String zipCode() {
        return this.zipCode;
    }

    public String city() {
        return this.city;
    }

    public String state() {
        return this.state;
    }
}
