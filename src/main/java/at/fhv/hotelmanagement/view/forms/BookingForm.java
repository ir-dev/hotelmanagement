package at.fhv.hotelmanagement.view.forms;

import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.Enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.Enums.Salutation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BookingForm {
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private Integer numberOfPersons;

    // Categories?!
    //private List<Category> selectedCategories;
    //private int nrOfRooms;

    private Boolean organization;
    private String organizationName;
    private String AgreementCode;

    private Salutation salutation;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Address address;
    private String specialNotes;

    private String cardHolderName;
    private String cardNumber;
    private String cardValidThru;
    private String cardCvc;
    private PaymentType paymentType;

    // required by spring/thymeleaf
    public BookingForm() {
    }

    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = LocalDate.parse(arrivalDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalDate getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = LocalDate.parse(departureDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public LocalTime getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = LocalTime.parse(arrivalTime, DateTimeFormatter.ofPattern("H:mm"));
    }

    public Integer getNumberOfPersons() {
        return this.numberOfPersons;
    }

    public void setNumberOfPersons(String numberOfPersons) {
        this.numberOfPersons = Integer.parseInt(numberOfPersons);
    }

    public boolean isOrganization() {
        return this.organization;
    }

    public void setOrganization(boolean organization) {
        this.organization = organization;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAgreementCode() {
        return this.AgreementCode;
    }

    public void setAgreementCode(String agreementCode) {
        this.AgreementCode = agreementCode;
    }

    public Salutation getSalutation() {
        return this.salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = Salutation.valueOf(salutation);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public Address getAddress() {
        return this.address;
    }

    //TODO: How to map all fields to method call
//    public void setAddress(String street, String zipcode, String city, String country) {
//        this.address = new Address(street, zipcode, city, country);
//    }

    public String getSpecialNotes() {
        return this.specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public String getCardHolderName() {
        return this.cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardValidThru() {
        return this.cardValidThru;
    }

    public void setCardValidThru(String cardValidThru) {
        this.cardValidThru = cardValidThru;
    }

    public String getCardCvc() {
        return this.cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = PaymentType.valueOf(paymentType);
    }
}
