package at.fhv.hotelmanagement.domain.model.guest;

import at.fhv.hotelmanagement.AbstractTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GuestFactoryTest extends AbstractTest {
    @Test
    void given_guestdetails_when_createguestfromfactory_then_detailsequals() throws CreateGuestException {
        //given
        GuestId guestId = new GuestId("1");
        Organization organization = new Organization("FHV", BigDecimal.valueOf(0.25));
        Salutation salutation = Salutation.MS;
        String firstName = ("Anna");
        String lastName = ("Bauer");
        LocalDate dateOfBirth = LocalDate.now().minusYears(18L);
        Address address = new Address("Musterstraße 5", "6900", "Bregenz", String.valueOf(Country.AT));
        String specialNotes = "Frühstück aufs Zimmer";

        //when
        Guest guest = GuestFactory.createGuest(guestId, organization, String.valueOf(salutation), firstName, lastName, dateOfBirth, address, specialNotes);

        //then
        assertEquals(guestId, guest.getGuestId());
        assertEquals(Optional.of(organization), guest.getOrganization());
        assertEquals(salutation, guest.getSalutation());
        assertEquals(firstName, guest.getFirstName());
        assertEquals(lastName, guest.getLastName());
        assertEquals(firstName, guest.getFirstName());
        assertEquals(dateOfBirth, guest.getDateOfBirth());
        assertEquals(address, guest.getAddress());
        assertEquals(specialNotes, guest.getSpecialNotes());
    }

    @Test
    void given_underageguest_when_createguestfromfactory_then_throwscreateguestexception() {
        //given
        GuestId guestId = new GuestId("1");
        Organization organization = new Organization("FHV", BigDecimal.valueOf(0.25));
        Salutation salutation = Salutation.MS;
        String firstName = ("Anna");
        String lastName = ("Bauer");
        LocalDate dateOfBirth = LocalDate.now().minusYears(18L).plusDays(1L);
        Address address = new Address("Musterstraße 5", "6900", "Bregenz", String.valueOf(Country.AT));
        String specialNotes = "Frühstück aufs Zimmer";

        //when..then
        assertThrows(CreateGuestException.class, () -> GuestFactory.createGuest(guestId, organization, String.valueOf(salutation), firstName, lastName, dateOfBirth, address, specialNotes));
    }
}
