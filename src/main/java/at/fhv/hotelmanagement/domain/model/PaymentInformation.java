package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.PaymentType;

public class PaymentInformation {
    // Credit Card Details (used for advance payment)
    private String cardHolderName;
    private String cardNumber;
    private String cardValidThru;
    private String cardCvc;
    // Payment Type (used for billing)
    private PaymentType paymentType;

    public PaymentInformation(String cardHolderName, String cardNumber, String cardValidThru, String cardCvc, PaymentType paymentType) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardValidThru = cardValidThru;
        this.cardCvc = cardCvc;
        this.paymentType = paymentType;
    }
}
