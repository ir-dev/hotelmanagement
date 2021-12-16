package at.fhv.hotelmanagement.domain.model.guest;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.guest.Organization;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationTest extends AbstractTest {
    @Test
    void given_organizationdetails_when_createorganization_then_detailsequals() {
        //given
        String organizationName = "FHV";
        BigDecimal discountRate = BigDecimal.valueOf(0.25);

        //when
        Organization organization = new Organization(organizationName, discountRate);

        //then
        assertEquals(organizationName, organization.getOrganizationName());
        assertEquals(discountRate, organization.getDiscountRate());
    }
}