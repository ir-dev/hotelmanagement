package at.fhv.hotelmanagement.view.rest;


import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import at.fhv.hotelmanagement.domain.model.guest.PaymentType;
import at.fhv.hotelmanagement.domain.model.stay.CreateStayException;
import at.fhv.hotelmanagement.domain.model.stay.Stay;
import at.fhv.hotelmanagement.domain.model.stay.StayFactory;
import at.fhv.hotelmanagement.domain.model.stay.StayId;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private StayRepository stayRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @Test
    public void given_arrivaldate_and_departuredate_when_fetchavailablecategoriesforbooking_then_returnequalsavailablecategories() throws RoomAlreadyExistsException, CreateStayException, CreateBookingException {
        // given
        List<Category> categories = createCategoriesDummy();
        AvailableCategoryDTO availableCategoryDTO1 = buildAvailableCategoryDto(categories.get(0), 2);
        AvailableCategoryDTO availableCategoryDTO2 = buildAvailableCategoryDto(categories.get(1), 3);

        List<AvailableCategoryDTO> availableCategoryDTOsExpected = Arrays.asList(availableCategoryDTO1, availableCategoryDTO2);

        String arrivalDate = LocalDate.now().toString();
        String departureDate = LocalDate.now().plusDays(3).toString();

        Stay stay = createStayDummy();
        Booking booking = createBookingDummy();

        Mockito.when(this.stayRepository.findAll()).thenReturn(Collections.singletonList(stay));
        Mockito.when(this.bookingRepository.findAll()).thenReturn(Collections.singletonList(booking));
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        // when
        URI uri = URI.create("http://localhost:"+port+"/rest/categories?arrivalDate="+arrivalDate+"&departureDate="+departureDate);


        AvailableCategoryDTO[] availableCategoryDTOsActual = this.restTemplate.getForObject(uri, AvailableCategoryDTO[].class);

        // then
        for (int i = 0; i < availableCategoryDTOsActual.length; i++) {
            assertEquals(availableCategoryDTOsExpected.get(i), availableCategoryDTOsActual[i]);
        };
    }

    private AvailableCategoryDTO buildAvailableCategoryDto(Category category, int availableRoomsCount) {
        String categoryNameBase64 = Base64.getUrlEncoder().encodeToString(category.getName().getBytes());
        String resourceUrl = "/assets/images/category/" +categoryNameBase64+ ".jpg";

        return AvailableCategoryDTO.builder()
                .withName(category.getName())
                .withDescription(category.getDescription())
                .withAvailableRoomsCount(availableRoomsCount)
                .withPrice(category.getFullBoardPrice())
                .withImageUrl(resourceUrl)
                .build();
    }

    private List<Category> createCategoriesDummy() throws RoomAlreadyExistsException {
        Price p1 = Price.of(BigDecimal.valueOf(150), Currency.getInstance("EUR"));
        Category category1 = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, p1, p1);
        category1.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));
        category1.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));

        Price p2 = Price.of(BigDecimal.valueOf(200), Currency.getInstance("EUR"));
        Category category2 = CategoryFactory.createCategory(new CategoryId("2"), "Business Casual DZ", "A casual accommodation for business guests.", 2, p2, p2);
        category2.createRoom(new Room(new RoomNumber("200"), RoomState.AVAILABLE));
        category2.createRoom(new Room(new RoomNumber("201"), RoomState.AVAILABLE));

        return Arrays.asList(category1, category2);
    }

    private Stay createStayDummy() throws RoomAlreadyExistsException, CreateStayException {
        //Booking
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();

        createCategoriesDummy().forEach(category -> selectCategoriesRoomCount.put(category, 2));

        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Franz Huber", "1234 5678 9012 3456", "05/24", "123", String.valueOf(PaymentType.CASH));

        return StayFactory.createStayForWalkIn(
                new StayId("S100000"),
                arrivalDate,
                departureDate,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation
        );
    }

    private Booking createBookingDummy() throws CreateBookingException, RoomAlreadyExistsException {
        Price p = Price.of(BigDecimal.ZERO, Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"),"Business Casual EZ", "A casual accommodation for business guests.", 1, p, p);
        category.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));

        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2L);
        LocalTime arrivalTime = LocalTime.now();
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put(category, 1);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));

        return BookingFactory.createBooking(
                new BookingNo("B100000"),
                arrivalDate,
                departureDate,
                arrivalTime,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation
        );
    }
}


