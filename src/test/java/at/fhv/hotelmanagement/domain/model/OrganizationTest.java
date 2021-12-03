package at.fhv.hotelmanagement.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizationTest {
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