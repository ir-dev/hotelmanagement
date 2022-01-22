package at.fhv.hotelmanagement.view.forms;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class BookingForm {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate arrivalDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime arrivalTime;
    private Integer numberOfPersons;
    private Map<String, Integer> selectedCategoriesRoomCount;
    private Boolean isOrganization;
    private String organizationName;
    private BigDecimal discountRate;
    private String salutation;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private String street;
    private String zipcode;
    private String city;
    private String country;
    private String specialNotes;
    private String cardHolderName;
    private String cardNumber;
    private String cardValidThru;
    private String cardCvc;
    private String paymentType;

    // default constructor required by spring/thymeleaf
    public BookingForm() {
        this.selectedCategoriesRoomCount = new HashMap<>();
    }

    // required setters/getters for spring/thymeleaf
    public LocalDate getArrivalDate() {
        return this.arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDate getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getNumberOfPersons() {
        return this.numberOfPersons;
    }

    public void setNumberOfPersons(Integer numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public Map<String, Integer> getSelectedCategoriesRoomCount() {
        return this.selectedCategoriesRoomCount;
    }

    public void setSelectedCategoriesRoomCount(Map<String, Integer> selectedCategoriesRoomCount) {
        this.selectedCategoriesRoomCount = selectedCategoriesRoomCount;
    }

    public Boolean getIsOrganization() {
        return this.isOrganization;
    }

    public void setIsOrganization(Boolean isOrganization) {
        this.isOrganization = isOrganization;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public BigDecimal getDiscountRate() {
        if (this.discountRate != null) {
            return this.discountRate;
        } else {
            return BigDecimal.valueOf(0);
        }
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public String getSalutation() {
        return this.salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
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

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
        return this.paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
