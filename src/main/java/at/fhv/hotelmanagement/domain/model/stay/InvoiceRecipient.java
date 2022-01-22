package at.fhv.hotelmanagement.domain.model.stay;

import at.fhv.hotelmanagement.domain.model.guest.Address;

public class InvoiceRecipient {
    // generated hibernate id
    private Long id;
    private String firstName;
    private String lastName;
    private Address address;

    // required for hibernate
    public InvoiceRecipient() {}

    public InvoiceRecipient(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public Long getId() {return this.id;}

    public String getFirstName() {return this.firstName;}

    public String getLastName() {return this.lastName;}

    public Address getAddress() {return this.address;}
}