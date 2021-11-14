package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.Country;

public class Address {
    private String street;
    private String zipcode;
    private String city;
    private Country country;

    private Address(){}

    public Address(String street, String zipcode, String city, Country country) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
        this.country = country;
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
