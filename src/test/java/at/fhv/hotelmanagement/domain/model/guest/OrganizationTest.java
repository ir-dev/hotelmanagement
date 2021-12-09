package at.fhv.hotelmanagement.domain.model.guest;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.guest.Organization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationTest extends AbstractTest {
    @Test
    void given_organizationdetails_when_createorganization_then_detailsequals() {
        //given
        String organizationName = "FHV";
        String organizationAgreementCode = "21212";

        //when
        Organization organization = new Organization(organizationName, organizationAgreementCode);

        //then
        assertEquals(organization.getOrganizationName(), organizationName);
        assertEquals(organization.getOrganizationAgreementCode(), organizationAgreementCode);
    }
}