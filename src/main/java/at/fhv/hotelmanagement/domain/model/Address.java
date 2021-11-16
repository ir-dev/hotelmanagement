package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.Country;

public class Address {
    private String street;
    private String zipcode;
    private String city;
    private Country country;

    // required for hibernate
    private Address() {}

    public Address(String street, String zipcode, String city, String country) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.country = Country.valueOf(country);
    }

    public String getStreet() {
        return this.street;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public Country getCountry() {
        return this.country;
    }
}
