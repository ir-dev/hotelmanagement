package at.fhv.hotelmanagement.view.forms;

import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class StayForm {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;
    private Integer numberOfPersons;
    private Map<String, Integer> selectedCategoriesRoomCount;
    private Boolean isOrganization;
    private String organizationName;
    private String organizationAgreementCode;
    private String salutation;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
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

    public void addBooking(BookingDTO bookingDTO) {
        BookingDetailsDTO bookingDetailsDTO = bookingDTO.details();
        GuestDTO guestDTO = bookingDetailsDTO.guest();

        this.departureDate = bookingDTO.departureDate();
        this.numberOfPersons = bookingDTO.numberOfPersons();
        this.selectedCategoriesRoomCount = bookingDetailsDTO.selectedCategoriesRoomCount();
        this.isOrganization = guestDTO.isOrganization();
        this.organizationName = guestDTO.organizationName();
        this.organizationAgreementCode = guestDTO.organizationAgreementCode();
        this.salutation = String.valueOf(guestDTO.salutation());
        this.firstName = guestDTO.firstName();
        this.lastName = guestDTO.lastName();
        this.birthday = guestDTO.birthday();
        this.street = guestDTO.addressStreet();
        this.zipcode = guestDTO.addressZipcode();
        this.city = guestDTO.addressCity();
        this.country = String.valueOf(guestDTO.addressCountry());
        this.specialNotes = guestDTO.specialNotes();
        this.cardHolderName = bookingDetailsDTO.cardHolderName();
        this.cardNumber = bookingDetailsDTO.cardNumber();
        this.cardValidThru = bookingDetailsDTO.cardValidThru();
        this.cardCvc = bookingDetailsDTO.cardCvc();
        this.paymentType = bookingDetailsDTO.paymentType();
    }

    // default constructor required by spring/thymeleaf
    public StayForm() {
        this.selectedCategoriesRoomCount = new HashMap<>();
    }

    // required setters/getters for spring/thymeleaf
    public LocalDate getDepartureDate() {
        return this.departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
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

    public Boolean getOrganization() {
        return this.isOrganization;
    }

    public void setOrganization(Boolean organization) {
        this.isOrganization = organization;
    }

    public String getOrganizationName() {
        return this.organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationAgreementCode() {
        return this.organizationAgreementCode;
    }

    public void setOrganizationAgreementCode(String organizationAgreementCode) {
        this.organizationAgreementCode = organizationAgreementCode;
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

    public LocalDate getBirthday() {
        return this.birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
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