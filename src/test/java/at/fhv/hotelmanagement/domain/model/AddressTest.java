package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.Country;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AddressTest {
    @Test
    void given_addressdetails_when_createaddress_then_detailsequals() {
        //given
        String street = "Bauerstraße 2";
        String zipcode = "6900";
        String city = "Bregenz";

        //when
        Address address = new Address(street, zipcode, city, String.valueOf(Country.AT));

        //then
        assertEquals(address.getStreet(), street);
        assertEquals(address.getZipcode(), zipcode);
        assertEquals(address.getCity(), city);
        assertEquals(address.getCountry(), Country.AT);
    }
}