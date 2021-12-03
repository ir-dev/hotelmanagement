package at.fhv.hotelmanagement.domain.model;

import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentInformationTest {
    @Test
    void given_paymentinformationdetails_when_createpaymentinformation_then_detailsequals() {
        //given
        String cardHolderName = "testname";
        String cardNumber = "testnumber";
        String cardValidThru = "12/22";
        String cardCvc = "123";
        PaymentType paymentType = PaymentType.CASH;

        //when
        PaymentInformation paymentInformation = new PaymentInformation(cardHolderName, cardNumber, cardValidThru, cardCvc, paymentType.name());

        //then
        assertEquals(paymentInformation.getCardHolderName(),cardHolderName);
        assertEquals(paymentInformation.getCardNumber(),cardNumber);
        assertEquals(paymentInformation.getCardValidThru(), cardValidThru);
        assertEquals(paymentInformation.getCardCvc(), cardCvc);
        assertEquals(paymentInformation.getPaymentType(), paymentType);
    }
}