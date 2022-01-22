package at.fhv.hotelmanagement.domain.model.guest;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.guest.Address;
import at.fhv.hotelmanagement.domain.model.guest.Country;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class AddressTest extends AbstractTest {
    @Test
    void given_addressdetails_when_createaddress_then_detailsequals() {
        //given
        String street = "Bauerstraße 2";
        String zipcode = "6900";
        String city = "Bregenz";
        Country country = Country.AT;

        //when
        Address address = new Address(street, zipcode, city, String.valueOf(country));

        //then
        assertEquals(street, address.getStreet());
        assertEquals(zipcode, address.getZipcode());
        assertEquals(city, address.getCity());
        assertEquals(country, address.getCountry());
    }
}