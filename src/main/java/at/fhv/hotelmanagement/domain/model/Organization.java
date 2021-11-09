package at.fhv.hotelmanagement.domain.model;

public class Organization {
    private String organizationName;
    private String agreementCode;

    public Organization(String organizationName, String agreementCode) {
        this.organizationName = organizationName;
        this.agreementCode = agreementCode;
    }
}
