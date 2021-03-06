package at.fhv.hotelmanagement.domain.model.guest;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import at.fhv.hotelmanagement.domain.model.guest.PaymentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentInformationTest extends AbstractTest {
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
        assertEquals(cardHolderName, paymentInformation.getCardHolderName());
        assertEquals(cardNumber, paymentInformation.getCardNumber());
        assertEquals(cardValidThru, paymentInformation.getCardValidThru());
        assertEquals(cardCvc, paymentInformation.getCardCvc());
        assertEquals(paymentType, paymentInformation.getPaymentType());
    }
}