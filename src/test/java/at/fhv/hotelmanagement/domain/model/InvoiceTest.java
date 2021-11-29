package at.fhv.hotelmanagement.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceTest {

    @Test
    void given_currentdate_when_createinvoice_then_contractdateequals() {
        // given
        LocalDate currentDate = LocalDate.now();
        // when
        Invoice invoice = new Invoice();
        // then
        assertEquals(currentDate, invoice.getContractDate());
    }

}