package at.fhv.hotelmanagement.view.rest;

import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import at.fhv.hotelmanagement.view.rest.responses.BookingResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    public void given_restservice_when_createbooking_then_returnsuccessinfo() throws RoomAlreadyExistsException {
        // given
        BookingForm bookingForm = initializeBookingForm();
        BookingResponse expectedResponse = BookingResponse.success();

        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        final ObjectNode requestData = mapper.createObjectNode();

        requestData.set("arrivalDate", mapper.convertValue(bookingForm.getArrivalDate(), JsonNode.class));
        requestData.set("departureDate", mapper.convertValue(bookingForm.getDepartureDate(), JsonNode.class));
        requestData.set("salutation", mapper.convertValue(bookingForm.getSalutation(), JsonNode.class));
        requestData.set("numberOfPersons", mapper.convertValue(bookingForm.getNumberOfPersons(), JsonNode.class));
        requestData.set("firstName", mapper.convertValue(bookingForm.getFirstName(), JsonNode.class));
        requestData.set("lastName", mapper.convertValue(bookingForm.getFirstName(), JsonNode.class));
        requestData.set("dateOfBirth", mapper.convertValue(bookingForm.getDateOfBirth(), JsonNode.class));
        requestData.set("street", mapper.convertValue(bookingForm.getStreet(), JsonNode.class));
        requestData.set("zipcode", mapper.convertValue(bookingForm.getZipcode(), JsonNode.class));
        requestData.set("city", mapper.convertValue(bookingForm.getCity(), JsonNode.class));
        requestData.set("country", mapper.convertValue(bookingForm.getCountry(), JsonNode.class));
        requestData.set("specialNotes", mapper.convertValue(bookingForm.getSpecialNotes(), JsonNode.class));
        requestData.set("cardHolderName", mapper.convertValue(bookingForm.getCardHolderName(), JsonNode.class));
        requestData.set("cardNumber", mapper.convertValue(bookingForm.getCardNumber(), JsonNode.class));
        requestData.set("cardValidThru", mapper.convertValue(bookingForm.getCardValidThru(), JsonNode.class));
        requestData.set("cardCvc", mapper.convertValue(bookingForm.getCardCvc(), JsonNode.class));
        requestData.set("paymentType", mapper.convertValue(bookingForm.getPaymentType(), JsonNode.class));
        requestData.set("selectedCategoriesRoomCount", mapper.convertValue(bookingForm.getSelectedCategoriesRoomCount(), JsonNode.class));
        requestData.set("isOrganization", mapper.convertValue(bookingForm.getIsOrganization(), JsonNode.class));
        requestData.set("discountRate", mapper.convertValue(bookingForm.getDiscountRate(), JsonNode.class));
        requestData.set("organizationName", mapper.convertValue(bookingForm.getOrganizationName(), JsonNode.class));

        Category c = createCategoryDummy();
        Mockito.when(this.categoryRepository.findByName("Business Casual EZ")).thenReturn(Optional.of(c));

        // when
        URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port)
                .path("/rest/bookings/create").build().encode().toUri();

        BookingResponse actualResponse = this.restTemplate.postForObject(uri, requestData, BookingResponse.class);

        // then
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

    private Category createCategoryDummy() throws RoomAlreadyExistsException {
        Price p1 = Price.of(BigDecimal.valueOf(150), Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, p1, p1);
        category.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));
        return category;
    }
}