package at.fhv.hotelmanagement.domain.model.guest;

public class Organization {
    private String organizationName;
    private String organizationAgreementCode;

    // required for hibernate
    private Organization() {}

    public Organization(String organizationName, String organizationAgreementCode) {
        this.organizationName = organizationName;
        this.organizationAgreementCode = organizationAgreementCode;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public String getOrganizationAgreementCode() {
        return this.organizationAgreementCode;
    }
}
