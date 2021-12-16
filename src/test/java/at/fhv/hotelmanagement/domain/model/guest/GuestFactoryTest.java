package at.fhv.hotelmanagement.domain.model.guest;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.guest.*;
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
        String firstName = ("Anna");
        String lastName = ("Bauer");
        LocalDate birthday1 = getContextLocalDate().minusYears(18L);
        LocalDate birthday2 = getContextLocalDate().minusYears(16L);
        Address address = new Address("Musterstraße 5", "6900", "Bregenz", String.valueOf(Country.AT));
        String specialNotes = ("Frühstück aufs Zimmer");

        //when
        Guest guest = GuestFactory.createGuest(guestId, organization, String.valueOf(Salutation.MISS), firstName, lastName, birthday1, address, specialNotes);

        //then
        assertEquals(guestId, guest.getGuestId());
        assertEquals(Optional.of(organization), guest.getOrganization());
        assertEquals(Salutation.MISS, guest.getSalutation());
        assertEquals(firstName, guest.getFirstName());
        assertEquals(lastName, guest.getLastName());
        assertEquals(firstName, guest.getFirstName());
        assertEquals(birthday1, guest.getBirthday());
        assertEquals(address, guest.getAddress());
        assertEquals(specialNotes, guest.getSpecialNotes());

        assertDoesNotThrow(() -> GuestFactory.createGuest(guestId, organization, String.valueOf(Salutation.MISS), firstName, lastName, birthday1, address, specialNotes));
        assertThrows(CreateGuestException.class, () -> GuestFactory.createGuest(guestId, organization, String.valueOf(Salutation.MISS), firstName, lastName, birthday2, address, specialNotes));
    }
}
