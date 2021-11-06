package at.fhv.hotelmanagement.view.forms;

import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;

import java.util.List;

public class BookingForm {
    private String arrivalDate;
    private String departureDate;
    private String arrivalTime;
    private String numberOfPersons;

    // Categories?!
    private String nrOfCategoryRooms;

    private Boolean organization;
    private String organizationName;
    private String AgreementCode;

    private Salutation salutation;
    private String firstName;
    private String lastName;
    private String birthday;
    private String specialNotes;

    //Address
    private String street;
    private String zipcode;
    private String city;
    private String country;

    //Payment Details
    private String cardHolderName;
    private String cardNumber;
    private String cardValidThru;
    private String cardCvc;
    private PaymentType paymentType;

    // required by spring/thymeleaf
    public BookingForm() {
    }

    public String getNrOfCategoryRooms() {
        return this.nrOfCategoryRooms;
    }

    public void setNrOfCategoryRooms(String nrOfCategoryRooms) {
        this.nrOfCategoryRooms = nrOfCategoryRooms;
    }

    public String getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getNumberOfPersons() {
        return this.numberOfPersons;
    }

    public void setNumberOfPersons(String numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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

    public String getSalutation() {
        return this.salutation.toString();
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

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public String getPaymentType() {
        return this.paymentType.toString();
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = PaymentType.valueOf(paymentType);
    }
}
