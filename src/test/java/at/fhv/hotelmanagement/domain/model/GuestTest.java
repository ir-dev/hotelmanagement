package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

public class GuestTest extends AbstractTest {
    @Test
    void given_guestdetails_when_createguest_then_detailsequals() {
        //given
        GuestId guestId = new GuestId("1");
        Organization organization = new Organization("FHV", "123144dsl");
        String firstName = ("Anna");
        String lastName = ("Bauer");
        LocalDate birthday = getContextLocalDate().minusYears(18L);
        Address address = new Address("Musterstraße 5", "6900", "Bregenz", String.valueOf(Country.AT));
        String specialNotes = ("Frühstück aufs Zimmer");

        //when
        Guest guest = new Guest(guestId, organization, String.valueOf(Salutation.MISS), firstName, lastName, birthday, address, specialNotes);

        //then
        assertEquals(guest.getGuestId(), guestId);
        assertEquals(guest.getOrganization(), Optional.of(organization));
        assertEquals(guest.getSalutation(), Salutation.MISS);
        assertEquals(guest.getFirstName(), firstName);
        assertEquals(guest.getLastName(), lastName);
        assertEquals(guest.getFirstName(), firstName);
        assertEquals(guest.getBirthday(), birthday);
        assertEquals(guest.getAddress(), address);
        assertEquals(guest.getSpecialNotes(), specialNotes);
    }
}