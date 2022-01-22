package at.fhv.hotelmanagement.view.forms;

public class InvoiceRecipientForm {
    private String firstName;
    private String lastName;
    private String street;
    private String zipcode;
    private String city;
    private String country;

    // default constructor required by spring/thymeleaf
    public InvoiceRecipientForm() {}

    // required setters/getters for spring/thymeleaf
    public String getFirstName() {return this.firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return this.lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getStreet() {return this.street;}

    public void setStreet(String street) {this.street = street;}

    public String getZipcode() {return this.zipcode;}

    public void setZipcode(String zipcode) {this.zipcode = zipcode;}

    public String getCity() {return this.city;}

    public void setCity(String city) {this.city = city;}

    public String getCountry() {return this.country;}

    public void setCountry(String country) {this.country = country;}
}
