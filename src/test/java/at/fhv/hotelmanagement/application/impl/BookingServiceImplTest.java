package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingDetailsDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.view.forms.BookingForm;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class BookingServiceImplTest extends AbstractTest {

    @Autowired
    private BookingsService bookingsService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private GuestRepository guestRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void given_emptyrepository_when_fetchingallbookings_then_empty() {
        //given
        Mockito.when(this.bookingRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<BookingDTO> bookingsDto = this.bookingsService.allBookings();
        //then
        assertTrue(bookingsDto.isEmpty());
    }

    @Test
    void given_2bookingsinrepository_when_fetchallbookings_then_returnequalbookings() throws CreateGuestException, RoomAlreadyExistsException, CreateBookingException {
        //given
        List<Booking> bookings = Arrays.asList(
                createBookingDummy(),
                createBookingDummy()
        );
        Guest guest = createGuestDummy();

        Mockito.when(this.bookingRepository.findAll()).thenReturn(bookings);
        Mockito.when(this.guestRepository.findById(any())).thenReturn(Optional.of(guest));

        List<BookingDTO> bookingsDtoExpected = new ArrayList<>();
        for (Booking booking : bookings) {
            BookingDTO bookingDto = BookingDTO.builder()
                    .withBookingEntity(booking)
                    .withDetails(BookingDetailsDTO.builder()
                            .withBookingEntity(booking)
                            .withGuestDTO(GuestDTO.builder().withGuestEntity(createGuestDummy()).build())
                            .build())
                    .build();
            bookingsDtoExpected.add(bookingDto);
        }

        //when
        List<BookingDTO> bookingsDtoActual = this.bookingsService.allBookings();

        //then
        for (BookingDTO b : bookingsDtoActual) {
            assertTrue(bookingsDtoExpected.contains(b));
        }
    }


    @Test
    void given_bookinginrepository_when_bookingbybookingNo_then_return() throws CreateGuestException, CreateBookingException, RoomAlreadyExistsException {
        //given
        Booking booking = createBookingDummy();
        Guest guest = createGuestDummy();

        BookingDTO expectedBookingDTO = BookingDTO.builder()
                .withBookingEntity(booking)
                .withDetails(BookingDetailsDTO.builder()
                        .withBookingEntity(booking)
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                        .build())
                .build();

        Mockito.when(this.guestRepository.findById(guest.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));

        //when
        BookingDTO actualBookingDTO = this.bookingsService.bookingByBookingNo(booking.getBookingNo().getNo()).orElseThrow();

        //then
        assertEquals(expectedBookingDTO, actualBookingDTO);
    }

    @Test
    void given_bookingNo_when_bookingbybookingNo_then_returnEmpty() {
        //given
        BookingNo bookingNo = new BookingNo("1");
        Mockito.when(this.bookingRepository.findByNo(bookingNo)).thenReturn(Optional.empty());

        //when
        Optional<BookingDTO> bookingDto = this.bookingsService.bookingByBookingNo(bookingNo.getNo());

        //then
        assertTrue(bookingDto.isEmpty());
    }


    @Test
    void given_bookinginrepository_when_bookingdetailsbybookingNo_then_return() throws CreateGuestException, CreateBookingException, RoomAlreadyExistsException {
        //given
        Booking booking = createBookingDummy();
        Guest guest = createGuestDummy();

        BookingDetailsDTO expectedBookingDetailsDTO = BookingDetailsDTO.builder()
                .withBookingEntity(booking)
                .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                .build();

        Mockito.when(this.guestRepository.findById(guest.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));

        //when
        BookingDetailsDTO actualBookingDetailsDTO = this.bookingsService.bookingDetailsByBookingNo(booking.getBookingNo().getNo()).orElseThrow();

        //then
        assertEquals(expectedBookingDetailsDTO, actualBookingDetailsDTO);
    }

    @Test
    void given_bookingNo_when_bookingdetailsbybookingNo_then_returnEmpty() {
        //given
        BookingNo bookingNo = new BookingNo("1");
        Mockito.when(this.bookingRepository.findByNo(bookingNo)).thenReturn(Optional.empty());

        //when
        Optional<BookingDetailsDTO> bookingDetailsDto = this.bookingsService.bookingDetailsByBookingNo(bookingNo.getNo());

        //then
        assertTrue(bookingDetailsDto.isEmpty());
    }

    @Test
    void given_bookinginrepository_with_empty_guestId_when_bookingbybookingNo_then_throw() throws CreateBookingException, RoomAlreadyExistsException {
        //given
        Booking booking = createBookingDummy();

        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));
        Mockito.when(this.guestRepository.findById(booking.getGuestId())).thenReturn(Optional.empty());

        //when..then
        assertThrows(NoSuchElementException.class, () -> {
            BookingDTO bookingDto = this.bookingsService.bookingByBookingNo(booking.getBookingNo().getNo()).orElseThrow();
        }, "NoSuchElementException was expected");
    }

    @Test
    void given_bookingform_when_createBooking_then_not_throws() throws RoomAlreadyExistsException {
        //given
        BookingForm bookingForm = initializeBookingForm();
        Category category = createCategoryDummy();

        Mockito.when(this.categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        //when..then
        assertDoesNotThrow(() -> {
            this.bookingsService.createBooking(bookingForm);
        });
    }

    @Test
    void given_bookingform_with25percentdiscount_when_createBooking_then_throws() throws RoomAlreadyExistsException {
        //given
        Category category = createCategoryDummy();
        BookingForm bookingForm = initializeBookingForm();
        bookingForm.setIsOrganization(true);
        bookingForm.setOrganizationName("FHV");
        bookingForm.setDiscountRate(BigDecimal.valueOf(25));

        Mockito.when(this.categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        //when..then
        assertDoesNotThrow(() -> {
            this.bookingsService.createBooking(bookingForm);
        });
    }

    @Test
    void given_bookingform_with150percentdiscount_when_createBooking_then_throws() {
        //given
        BookingForm bookingForm = initializeBookingForm();
        bookingForm.setIsOrganization(true);
        bookingForm.setOrganizationName("FHV");
        bookingForm.setDiscountRate(BigDecimal.valueOf(150));

        //when..then
        assertThrows(CreateGuestException.class, () -> {
            this.bookingsService.createBooking(bookingForm);
        }, "CreateGuestException was expected");
    }

    @Test
    void given_bookingform_withminus20percentdiscount_when_createBooking_then_throws() {
        //given
        BookingForm bookingForm = initializeBookingForm();
        bookingForm.setIsOrganization(true);
        bookingForm.setOrganizationName("FHV");
        bookingForm.setDiscountRate(BigDecimal.valueOf(-20));

        //when..then
        assertThrows(CreateGuestException.class, () -> {
            this.bookingsService.createBooking(bookingForm);
        }, "CreateGuestException was expected");
    }

    @Test
    void given_bookingform_and_nocategoryinrepository_when_createBooking_then_throws() {
        //given
        BookingForm bookingForm = initializeBookingForm();

        //when..then
        assertThrows(NoSuchElementException.class, () -> {
            this.bookingsService.createBooking(bookingForm);
        }, "NoSuchElementException was expected");
    }


    private Guest createGuestDummy() throws CreateGuestException {
        Address address = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        return GuestFactory.createGuest(
                new GuestId("1"),
                null,
                String.valueOf(Salutation.MS),
                "Fritz",
                "Mayer",
                LocalDate.now().minusYears(18L),
                address,
                ""
        );
    }

    private Category createCategoryDummy() throws RoomAlreadyExistsException {
        Price p1 = Price.of(BigDecimal.valueOf(150), Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, p1, p1);
        category.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));
        category.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));
        return category;
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