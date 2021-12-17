package at.fhv.hotelmanagement.domain.model.guest;

import java.math.BigDecimal;

public class Organization {
    private String organizationName;
    private BigDecimal discountRate;

    // required for hibernate
    private Organization() {}

    public Organization(String organizationName, BigDecimal discountRate) {
        this.organizationName = organizationName;
        this.discountRate = discountRate;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public BigDecimal getDiscountRate() {
        return this.discountRate;
    }
}
