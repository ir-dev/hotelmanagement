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

    private PaymentInformation() {

    }
    public PaymentInformation(String cardHolderName, String cardNumber, String cardValidThru, String cardCvc, String paymentType) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardValidThru = cardValidThru;
        this.cardCvc = cardCvc;
        this.paymentType = PaymentType.valueOf(paymentType);
    }
}
