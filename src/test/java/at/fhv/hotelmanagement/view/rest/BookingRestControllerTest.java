package at.fhv.hotelmanagement.view.rest;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import at.fhv.hotelmanagement.view.rest.responses.BookingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BookingsService bookingsService;

    @Test
    public void given_restservice_when_createbooking_then_returnsuccessinfo() throws Exception {
        // given
        BookingForm bookingForm = initializeBookingForm();
        BookingResponse expectedResponse = BookingResponse.success();

        String json = (new ObjectMapper()).writeValueAsString(bookingForm);

        // when
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port)
                .path("/rest/bookings/create")
                .build().encode().toUri();

        BookingResponse actualResponse = this.restTemplate.postForObject(uri, null, BookingResponse.class);

        // then
        Mockito.verify(this.bookingsService, times(1)).createBooking(bookingForm);
        assertEquals(expectedResponse, actualResponse);
    }

    private BookingForm initializeBookingForm() {
        BookingForm bookingForm = new BookingForm();
        //Payment Information
        bookingForm.setCardHolderName("Franz Huber");
        bookingForm.setCardNumber("1234 5678 9012 3456");
        bookingForm.setCardValidThru("05/24");
        bookingForm.setCardCvc("123");
        bookingForm.setPaymentType("CASH");
        //Guest Information
        bookingForm.setSalutation("MR");
        bookingForm.setFirstName("Fritz");
        bookingForm.setLastName("Mayer");
        bookingForm.setDateOfBirth(LocalDate.now().minusYears(18L));
        bookingForm.setStreet("Musterstrasse 1");
        bookingForm.setZipcode("6850");
        bookingForm.setCity("Dornbirn");
        bookingForm.setCountry("AT");
        bookingForm.setSpecialNotes("");
        bookingForm.setIsOrganization(false);
        //Stay Information
        bookingForm.setArrivalDate(LocalDate.now());
        bookingForm.setDepartureDate(LocalDate.now().plusDays(2));
        bookingForm.setNumberOfPersons(1);
        Map<String, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put("Business Casual EZ", 1);
        bookingForm.setSelectedCategoriesRoomCount(selectCategoriesRoomCount);
        return bookingForm;
    }
}