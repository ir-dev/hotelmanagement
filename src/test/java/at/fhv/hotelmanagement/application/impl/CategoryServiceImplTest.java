package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.api.CategoryService;
import at.fhv.hotelmanagement.application.dto.AvailableCategoryDTO;
import at.fhv.hotelmanagement.application.dto.CategoryDTO;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.guest.Address;
import at.fhv.hotelmanagement.domain.model.guest.GuestId;
import at.fhv.hotelmanagement.domain.model.guest.PaymentInformation;
import at.fhv.hotelmanagement.domain.model.stay.*;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryFactory;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.RoomAlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CategoryServiceImplTest extends AbstractTest {

    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private StayRepository stayRepository;

    @Test
    void given_emptyrepositories_when_fetchallavailablecategoriesforbooking_then_empty() {
        //given
        Mockito.when(this.stayRepository.findAll()).thenReturn(Collections.emptyList());
        Mockito.when(this.bookingRepository.findAll()).thenReturn(Collections.emptyList());
        Mockito.when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<AvailableCategoryDTO> categoriesDto = this.categoryService.availableCategoriesForBooking(LocalDate.now(), LocalDate.now().plusDays(2));
        //then
        assertTrue(categoriesDto.isEmpty());
    }

    @Test
    void given_emptyrepository_when_fetchallavailablecategories_then_empty() {
        //given
        Mockito.when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<AvailableCategoryDTO> categoriesDto = this.categoryService.availableCategories(LocalDate.now(), LocalDate.now().plusDays(2));
        //then
        assertTrue(categoriesDto.isEmpty());
    }

    @Test
    void given_emptyrepository_when_fetchallcategories_then_empty() {
        //given
        Mockito.when(this.categoryRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<CategoryDTO> categoriesDto = this.categoryService.allCategories();
        //then
        assertTrue(categoriesDto.isEmpty());
    }

    @Test
    void given_3availablecategorieswithbookingsandstays_when_fetchavailablecategoriesforbooking_then_returnequalsnonreservedoverbookablecategories() throws RoomAlreadyExistsException, CreateBookingException, CreateStayException, BillingOpenException, PriceCurrencyMismatchException {
        //given
        LocalDateTime ldtNow = LocalDateTime.now();
        LocalDate ldNow = LocalDate.now();
        LocalDate ldP5D = ldtNow.plusDays(5L).toLocalDate();
        LocalDate ldP10D = ldtNow.plusDays(10L).toLocalDate();
        LocalTime ltP10D = ldtNow.plusDays(10L).toLocalTime();
        LocalDate ldP15D = ldtNow.plusDays(15L).toLocalDate();
        LocalTime ltP15D = ldtNow.plusDays(15L).toLocalTime();
        LocalDate ldP20D = ldtNow.plusDays(20L).toLocalDate();
        LocalTime ltP20D = ldtNow.plusDays(20L).toLocalTime();
        //scenario for availableCategoriesForBooking(ldP10D, ldP15D);

        Price price = Price.of(BigDecimal.valueOf(150), Currency.getInstance("EUR"));
        Category category1 = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, price, price);
        category1.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));
        category1.createRoom(new Room(new RoomNumber("102"), RoomState.AVAILABLE));
        category1.createRoom(new Room(new RoomNumber("103"), RoomState.AVAILABLE));
        category1.createRoom(new Room(new RoomNumber("104"), RoomState.AVAILABLE));
        category1.createRoom(new Room(new RoomNumber("105"), RoomState.MAINTENANCE));
        category1.createRoom(new Room(new RoomNumber("106"), RoomState.CLEANING));
        Category category2 = CategoryFactory.createCategory(new CategoryId("2"), "Business Casual DZ", "A casual accommodation for business guests.", 2, price, price);
        category2.createRoom(new Room(new RoomNumber("201"), RoomState.AVAILABLE));
        category2.createRoom(new Room(new RoomNumber("202"), RoomState.AVAILABLE));
        Category category3 = CategoryFactory.createCategory(new CategoryId("3"), "Family Glamping", "Glamourous Camping for a medium size family.", 5, price, price);
        category3.createRoom(new Room(new RoomNumber("301"), RoomState.AVAILABLE));
        category3.createRoom(new Room(new RoomNumber("302"), RoomState.AVAILABLE));
        Category category4 = CategoryFactory.createCategory(new CategoryId("4"), "Tree House", "Tree house for the very courageous", 2, price, price);
        Map<Category, Integer> scrcBooking = new HashMap<>();
        scrcBooking.put(category1, 1);
        scrcBooking.put(category2, 1);
        scrcBooking.put(category3, 1);
        PaymentInformation paymentInformation = new PaymentInformation("Jon Doe", "1234 5678 9876 5432 1000", "12/22", "123", "CREDITCARD");
        Booking booking1 = BookingFactory.createBooking(
                new BookingNo("1"),
                ldP10D,
                ldP15D,
                ltP15D,
                1,
                scrcBooking,
                new GuestId("123"),
                paymentInformation
        );
        // closed booking
        Booking booking2 = BookingFactory.createBooking(
                new BookingNo("2"),
                ldP10D,
                ldP15D,
                ltP15D,
                1,
                scrcBooking,
                new GuestId("123"),
                paymentInformation
        );
        booking2.close();
        // bookings with non intersecting booking period (1x at period begin and 1x at period end)
        Booking booking3 = BookingFactory.createBooking(
                new BookingNo("3"),
                ldP5D,
                ldP10D,
                ltP10D,
                1,
                scrcBooking,
                new GuestId("123"),
                paymentInformation
        );
        Booking booking4 = BookingFactory.createBooking(
                new BookingNo("4"),
                ldP15D,
                ldP20D,
                ltP20D,
                1,
                scrcBooking,
                new GuestId("123"),
                paymentInformation
        );
        Map<Category, Integer> scrcStay = new HashMap<>();
        scrcStay.put(category1, 2);
        scrcStay.put(category2, 1);
        Stay stay1 = StayFactory.createStayForWalkIn(
                new StayId("1"),
                ldNow,
                ldP15D,
                1,
                scrcStay,
                new GuestId("123"),
                paymentInformation
        );
        // non-checked in stay
        Stay stay2 = StayFactory.createStayForWalkIn(
                new StayId("2"),
                ldNow,
                ldP15D,
                1,
                scrcStay,
                new GuestId("123"),
                paymentInformation
        );
        stay2.finalizeInvoice(
                "Dummy1",
                scrcStay,
                BigDecimal.valueOf(0),
                new InvoiceRecipient("Hans", "Peter", new Address("Sägerstrasse 123", "1234", "Dornbirn", "AT")));
        stay2.checkout();
        // stays with non intersecting stay period (1x at period begin and 1x at period end)
        // stay3 is a past stay and stay4 is a future stay (to allow this we do re-mock the datetime here)
        setTestClockPlus(Period.ofDays(5));
        Stay stay3 = StayFactory.createStayForWalkIn(
                new StayId("3"),
                ldP5D,
                ldP10D,
                1,
                scrcStay,
                new GuestId("123"),
                paymentInformation
        );
        setTestClockPlus(Period.ofDays(15));
        Stay stay4 = StayFactory.createStayForWalkIn(
                new StayId("3"),
                ldP15D,
                ldP20D,
                1,
                scrcStay,
                new GuestId("123"),
                paymentInformation
        );
        setTestClock();
        List<Stay> stays = List.of(stay1, stay2, stay3, stay4);
        List<Booking> bookings = List.of(booking1, booking2, booking3, booking4);
        List<Category> categories = List.of(category1, category2, category3, category4);
        Mockito.when(this.stayRepository.findAll()).thenReturn(stays);
        Mockito.when(this.bookingRepository.findAll()).thenReturn(bookings);
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        List<AvailableCategoryDTO> availableCategoriesDtoExpected = new ArrayList<>();
        availableCategoriesDtoExpected.add(
                AvailableCategoryDTO.builder()
                    .withName(category1.getName())
                    .withDescription(category1.getDescription())
                    .withAvailableRoomsCount(3)
                    .build()
        );
        // expected missing avail.cat.dto for 2nd cat. because has no unbooked and available rooms
        availableCategoriesDtoExpected.add(
                AvailableCategoryDTO.builder()
                        .withName(category3.getName())
                        .withDescription(category3.getDescription())
                        .withAvailableRoomsCount(1)
                        .build()
        );

        //when
        List<AvailableCategoryDTO> availableCategoriesDtoActual = this.categoryService.availableCategoriesForBooking(ldP10D, ldP15D);

        //then
        for (AvailableCategoryDTO c : availableCategoriesDtoActual) {
            assertTrue(availableCategoriesDtoExpected.contains(c));
        }
    }

    @Test
    void given_2availablecategories_when_fetchavailablecategories_then_returnequalcategories() throws RoomAlreadyExistsException {
        //given
        List<Category> categories = createCategoriesDummy();
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        List<AvailableCategoryDTO> availableCategoriesDtoExpected = new ArrayList<>();
        for (Category c : categories) {
            AvailableCategoryDTO availableCategoryDto = AvailableCategoryDTO.builder()
                    .withName(c.getName())
                    .withDescription(c.getDescription())
                    .withAvailableRoomsCount(c.getAvailableRoomsCount(LocalDate.now(), LocalDate.now().plusDays(2)))
                    .build();
            availableCategoriesDtoExpected.add(availableCategoryDto);
        }

        //when
        List<AvailableCategoryDTO> availableCategoriesDtoActual = this.categoryService.availableCategories(LocalDate.now(), LocalDate.now().plusDays(2));

        //then
        for (AvailableCategoryDTO c : availableCategoriesDtoActual) {
            assertTrue(availableCategoriesDtoExpected.contains(c));
        }
    }

    @Test
    void given_2categories_when_fetchallcategories_then_returnequalcategories() throws RoomAlreadyExistsException {
        //given
        List<Category> categories = createCategoriesDummy();
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDTO> categoriesDtoExpected = new ArrayList<>();
        for (Category c : categories) {
            CategoryDTO categoryDto = CategoryDTO.builder()
                    .withName(c.getName())
                    .withRooms(c.getAllRoomNumbersWithRoomStates())
                    .build();
            categoriesDtoExpected.add(categoryDto);
        }

        //when
        List<CategoryDTO> categoriesDtoActual = this.categoryService.allCategories();

        //then
        for (CategoryDTO c : categoriesDtoActual) {
            assertTrue(categoriesDtoExpected.contains(c));
        }
    }

    @Test
    void given_category_without_availablerooms_when_fetchavailablecategories_then_returnempty() {
        //given
        Price p1 = Price.of(BigDecimal.valueOf(150), Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, p1, p1);
        List<Category> categories = List.of(category);
        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        //when
        List<AvailableCategoryDTO> availableCategoriesDto = this.categoryService.availableCategories(LocalDate.now(), LocalDate.now().plusDays(2));

        //then
        assertTrue(availableCategoriesDto.isEmpty());
    }

    @Test
    void given_category_when_managecategory_then_throwsanddoesnotthrow() throws IllegalArgumentException, RoomAlreadyExistsException {
        //given
        List<Category> categories = createCategoriesDummy();

        // when.category not found.then
        Mockito.when(this.categoryRepository.findByName("noCategory")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> this.categoryService.manageCategory("noCategory", categories.get(0).getRoomByRoomNumber(new RoomNumber("100")).getRoomNumber().getNumber(), "AVAILABLE"));

        // when.room not found.then
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        assertThrows(IllegalArgumentException.class, () -> this.categoryService.manageCategory(categories.get(0).getName(), "0", "CLEANING"));

        // when.everything is fine.then
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        assertDoesNotThrow(() -> this.categoryService.manageCategory(categories.get(0).getName(), categories.get(0).getRoomByRoomNumber(new RoomNumber("100")).getRoomNumber().getNumber(), "CLEANING"));
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
}