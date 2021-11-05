package at.fhv.hotelmanagement.domain.model;

public class Address {
    private String street;
    private String zipcode;
    private String city;
    private String country;

    public Address(String street, String zipcode, String city, String country) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
    }

    public String street() {
        return this.street;
    }

    public String zipcode() {
        return this.zipcode;
    }

    public String city() {
        return this.city;
    }

    public String country() {
        return this.country;
    }
}
