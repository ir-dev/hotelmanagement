package at.fhv.hotelmanagement.domain.model.guest;

public class PaymentInformation {
    // credit card details (used for advance payment)
    private String cardHolderName;
    private String cardNumber;
    private String cardValidThru;
    private String cardCvc;
    // payment type (used for billing)
    private PaymentType paymentType;

    // required for hibernate
    private PaymentInformation() {}

    public PaymentInformation(String cardHolderName, String cardNumber, String cardValidThru, String cardCvc, String paymentType) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardValidThru = cardValidThru;
        this.cardCvc = cardCvc;
        this.paymentType = PaymentType.valueOf(paymentType);
    }

    public String getCardHolderName() {
        return this.cardHolderName;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getCardValidThru() {
        return this.cardValidThru;
    }

    public String getCardCvc() {
        return this.cardCvc;
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }
}
