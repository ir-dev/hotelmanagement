package at.fhv.hotelmanagement.view.forms;

import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StayForm {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;
    private Integer numberOfPersons;
    private Map<String, Integer> selectedCategoriesRoomCount;
    private Boolean isOrganization;
    private String organizationName;
    private BigDecimal discountRate;
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

        this.departureDate                  = this.departureDate != null ? this.departureDate : bookingDTO.departureDate();
        this.numberOfPersons                = this.numberOfPersons != null ? this.numberOfPersons : bookingDTO.numberOfPersons();
        this.selectedCategoriesRoomCount    = !this.selectedCategoriesRoomCount.isEmpty() ? this.selectedCategoriesRoomCount : bookingDetailsDTO.selectedCategoriesRoomCount();
        this.isOrganization                 = this.isOrganization != null ? this.isOrganization : guestDTO.isOrganization();
        this.organizationName               = this.organizationName != null ? this.organizationName : guestDTO.organizationName();
        this.discountRate                   = this.discountRate != null ? this.discountRate : guestDTO.discountRate();
        this.salutation                     = this.salutation != null ? this.salutation : String.valueOf(guestDTO.salutation());
        this.firstName                      = this.firstName != null ? this.firstName : guestDTO.firstName();
        this.lastName                       = this.lastName != null ? this.lastName : guestDTO.lastName();
        this.birthday                       = this.birthday != null ? this.birthday : guestDTO.birthday();
        this.street                         = this.street != null ? this.street : guestDTO.addressStreet();
        this.zipcode                        = this.zipcode != null ? this.zipcode : guestDTO.addressZipcode();
        this.city                           = this.city != null ? this.city : guestDTO.addressCity();
        this.country                        = this.country != null ? this.country : String.valueOf(guestDTO.addressCountry());
        this.specialNotes                   = this.specialNotes != null ? this.specialNotes : guestDTO.specialNotes();
        this.cardHolderName                 = this.cardHolderName != null ? this.cardHolderName : bookingDetailsDTO.cardHolderName();
        this.cardNumber                     = this.cardNumber != null ? this.cardNumber : bookingDetailsDTO.cardNumber();
        this.cardValidThru                  = this.cardValidThru != null ? this.cardValidThru : bookingDetailsDTO.cardValidThru();
        this.cardCvc                        = this.cardCvc != null ? this.cardCvc : bookingDetailsDTO.cardCvc();
        this.paymentType                    = this.paymentType != null ? this.paymentType : bookingDetailsDTO.paymentType();
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
        return this.discountRate;
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