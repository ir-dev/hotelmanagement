package at.fhv.hotelmanagement.view.forms;

import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BookingForm {
    private static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter FORMAT_TIME = DateTimeFormatter.ISO_TIME;

    // Stay Details
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private LocalTime arrivalTime;
    private Integer numberOfPersons;

    // Categories Room Selection
    private Map<String, Integer> selectedCategoriesRoomCount;

    // Guest Details
    // * Organization Details
    private Boolean isOrganization;
    private String organizationName;
    private String AgreementCode;
    // * Billing Address
    private Salutation salutation;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String street;
    private String zipcode;
    private String city;
    private Country country;
    private String specialNotes;

    // Payment Details
    private String cardHolderName;
    private String cardNumber;
    private String cardValidThru;
    private String cardCvc;
    private PaymentType paymentType;

    // default constructor required by spring/thymeleaf
    public BookingForm() {
        selectedCategoriesRoomCount = new HashMap<>();
    }

    // required setters/getters for spring/thymeleaf
    public String getArrivalDate() {
        if (this.arrivalDate == null) {
            return null;
        }

        return this.arrivalDate.format(FORMAT_DATE);
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = LocalDate.parse(arrivalDate, FORMAT_DATE);
    }

    public String getDepartureDate() {
        if (this.departureDate == null) {
            return null;
        }

        return this.departureDate.format(FORMAT_DATE);
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = LocalDate.parse(departureDate, FORMAT_DATE);
    }

    public String getArrivalTime() {
        if (this.arrivalTime == null) {
            return null;
        }

        return this.arrivalTime.format(FORMAT_TIME);
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = LocalTime.parse(arrivalTime, FORMAT_TIME);
    }

    public String getNumberOfPersons() {
        return String.valueOf(this.numberOfPersons.toString());
    }

    public void setNumberOfPersons(String numberOfPersons) {
        this.numberOfPersons = Integer.parseInt(numberOfPersons);
    }

    public Map<String, String> getSelectedCategoriesRoomCount() {
        Map<String, String> selectedCategoriesRoomCountParsed = new HashMap<>();

        for (Map.Entry<String, Integer> selectedCategoryRoomCount : this.selectedCategoriesRoomCount.entrySet()) {
            selectedCategoriesRoomCountParsed.put(selectedCategoryRoomCount.getKey(), String.valueOf(selectedCategoryRoomCount.getValue()));
        }

        return selectedCategoriesRoomCountParsed;
    }

    public void setSelectedCategoriesRoomCount(Map<String, String> selectedCategoriesRoomCount) {
        Map<String, Integer> selectedCategoriesRoomCountParsed = new HashMap<>();

        for (Map.Entry<String, String> selectedCategoryRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoriesRoomCountParsed.put(selectedCategoryRoomCount.getKey(), Integer.parseInt(selectedCategoryRoomCount.getValue()));
        }

        this.selectedCategoriesRoomCount = selectedCategoriesRoomCountParsed;
    }

    public String getIsOrganization() {
        return String.valueOf(this.isOrganization);
    }

    public void setIsOrganization(String isOrganization) {
        this.isOrganization = Boolean.parseBoolean(isOrganization);
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
        return String.valueOf(this.salutation);
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

    public String getBirthday() {
        if (this.birthday == null) {
            return null;
        }

        return this.birthday.format(FORMAT_DATE);
    }

    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday, FORMAT_DATE);
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
        return String.valueOf(this.country);
    }

    public void setCountry(String country) {
        this.country = Country.valueOf(country);
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
        return String.valueOf(this.paymentType.toString());
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = PaymentType.valueOf(paymentType);
    }

    // getters to directly access attribute values
    public LocalDate getArrivalDateValue() {
        return this.arrivalDate;
    }

    public LocalDate getDepartureDateValue() {
        return this.departureDate;
    }

    public LocalTime getArrivalTimeValue() {
        return this.arrivalTime;
    }

    public Integer getNumberOfPersonsValue() {
        return this.numberOfPersons;
    }

    public Map<String, Integer> getSelectedCategoriesRoomCountValue() {
        return this.selectedCategoriesRoomCount;
    }

    public Boolean getIsOrganizationValue() {
        return this.isOrganization;
    }

    public String getOrganizationNameValue() {
        return this.organizationName;
    }

    public String getAgreementCodeValue() {
        return this.AgreementCode;
    }

    public Salutation getSalutationValue() {
        return this.salutation;
    }

    public String getFirstNameValue() {
        return this.firstName;
    }

    public String getLastNameValue() {
        return this.lastName;
    }

    public LocalDate getBirthdayValue() {
        return this.birthday;
    }

    public String getStreetValue() {
        return this.street;
    }

    public String getZipcodeValue() {
        return this.zipcode;
    }

    public String getCityValue() {
        return this.city;
    }

    public Country getCountryValue() {
        return this.country;
    }

    public String getSpecialNotesValue() {
        return this.specialNotes;
    }

    public String getCardHolderNameValue() {
        return this.cardHolderName;
    }

    public String getCardNumberValue() {
        return this.cardNumber;
    }

    public String getCardValidThruValue() {
        return this.cardValidThru;
    }

    public String getCardCvcValue() {
        return this.cardCvc;
    }

    public PaymentType getPaymentTypeValue() {
        return this.paymentType;
    }
}
