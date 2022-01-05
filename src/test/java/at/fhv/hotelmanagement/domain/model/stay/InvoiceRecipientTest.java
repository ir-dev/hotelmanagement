package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.domain.model.guest.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceRecipientTest {

    @Test
    void given_invoicerecipientdetails_when_createinvoicerecipient_then_returnequalsdetails() {
        //given
        String firstName = ("Susi");
        String lastName = ("Oberhauser");
        Address address = new Address("Mariahilfstraße 5", "6900", "Bregenz", String.valueOf(Country.AT));


        //when
        InvoiceRecipient invoiceRecipient = new InvoiceRecipient(
                firstName,
                lastName,
                address
        );

        //then
        assertEquals(firstName, invoiceRecipient.getFirstName());
        assertEquals(lastName, invoiceRecipient.getLastName());
        assertEquals(address, invoiceRecipient.getAddress());
    }
}
