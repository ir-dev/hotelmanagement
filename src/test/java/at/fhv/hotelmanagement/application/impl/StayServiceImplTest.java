package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.*;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.model.stay.*;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.model.booking.Booking;
import at.fhv.hotelmanagement.domain.model.booking.BookingFactory;
import at.fhv.hotelmanagement.domain.model.booking.BookingNo;
import at.fhv.hotelmanagement.domain.model.booking.CreateBookingException;
import at.fhv.hotelmanagement.domain.model.category.Category;
import at.fhv.hotelmanagement.domain.model.category.CategoryFactory;
import at.fhv.hotelmanagement.domain.model.category.CategoryId;
import at.fhv.hotelmanagement.domain.model.category.RoomAlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.category.Room;
import at.fhv.hotelmanagement.domain.model.category.RoomNumber;
import at.fhv.hotelmanagement.domain.model.category.RoomState;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
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
public class StayServiceImplTest extends AbstractTest {

    @Autowired
    private StayService stayService;

    @MockBean
    private StayRepository stayRepository;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private GuestRepository guestRepository;

    @MockBean
    private CategoryRepository categoryRepository;


    private static Integer nextDummyBookingIdentity = 1;
    private static Integer nextDummyStayIdentity = 1;

    @Test
    void given_emptyrepository_when_fetchingallstays_then_empty() {
        //given
        Mockito.when(this.stayRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<StayDTO> staysDto = this.stayService.allStays();
        //then
        assertTrue(staysDto.isEmpty());
    }

    @Test
    void given_2staysinrepository_when_fetchallstays_then_returnequalStays() throws RoomAlreadyExistsException, CreateBookingException, CreateStayException, CreateGuestException {
        //given
        List<Stay> stays = Arrays.asList(createStayDummy(), createStayDummy());
        Guest guest = createGuestDummy();

        List<StayDTO> staysDtoExpected = new ArrayList<>();
        for (Stay s : stays) {
            StayDTO stayDTO = buildStayDto(s);
            staysDtoExpected.add(stayDTO);
        }

        Mockito.when(this.guestRepository.findById(any())).thenReturn(Optional.of(guest));
        Mockito.when(this.categoryRepository.findRoomNumbersByStayId(any())).thenReturn(createRoomNumbersDummy());
        Mockito.when(this.stayRepository.findAll()).thenReturn(stays);

        //when
        List<StayDTO> staysDtoActual = this.stayService.allStays();

        //then
        for (StayDTO s : staysDtoActual) {
            assertTrue(staysDtoExpected.contains(s));
        }
    }



    @Test
    void given_stayinrepository_when_bystayId_then_return() throws RoomAlreadyExistsException, CreateBookingException, CreateStayException, CreateGuestException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();

        StayDTO expectedStayDto = buildStayDto(stay);

        Mockito.when(this.guestRepository.findById(any())).thenReturn(Optional.of(guest));
        Mockito.when(this.categoryRepository.findRoomNumbersByStayId(any())).thenReturn(createRoomNumbersDummy());
        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when
        StayDTO actualStayDto = this.stayService.stayByStayId(stay.getStayId().getId()).orElseThrow();

        //then
        assertEquals(expectedStayDto, actualStayDto);
    }

    @Test
    void given_composedinvoice_when_byinvoiceNo_then_returnInvoice() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, PriceCurrencyMismatchException {
        //given
        Stay stay = createStayDummy();
        Invoice invoice = stay.composeInvoice(this.categoryRepository.findAll(), Optional.of(BigDecimal.valueOf(10)));
        Guest guest = createGuestDummy();

        List<InvoiceDTO> invoiceDtosExpected = new ArrayList<>();
        for (Invoice inv : stay.getInvoices()) {
            invoiceDtosExpected.add(InvoiceDTO.builder()
                    .withStayId(stay.getStayId())
                    .withInvoiceEntity(inv)
                    .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                    .withLineItemsDTO(buildLineItemsDto(inv.getLineItems()))
                    .build()
            );
        }

        Mockito.when(this.stayRepository.findByInvoiceNo(new InvoiceNo("1"))).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.stayRepository.findInvoiceByInvoiceNo(new InvoiceNo("1"))).thenReturn(Optional.of(invoice));

        //when
        Optional<InvoiceDTO> actualInvoiceDto = this.stayService.invoiceByInvoiceNo("1");

        //then
        assertTrue(invoiceDtosExpected.contains(actualInvoiceDto.orElseThrow()));
    }


    @Test
    void given_invoice_when_chargeStayPreview_then_returnequals() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        List<Category> categories = createCategoriesDummy();

        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);
        Invoice invoice = stay.generateInvoice(this.categoryRepository.findAll(), Optional.of(BigDecimal.valueOf(0)));
        InvoiceDTO invoiceDtoExpected = InvoiceDTO.builder()
                .withInvoiceEntity(invoice)
                .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                .withStayId(stay.getStayId())
                .withLineItemsDTO(buildLineItemsDto(invoice.getLineItems()))
                .build();


        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));

        //when
        InvoiceDTO actualInvoiceDto = this.stayService.chargeStayPreview(stay.getStayId().getId());

        //then
        assertEquals(invoiceDtoExpected, actualInvoiceDto);
    }

    @Test
    void given_invoice_when_chargeStay_then_invoiceNoequals() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        List<Category> categories = createCategoriesDummy();

        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        Invoice invoice = stay.generateInvoice(this.categoryRepository.findAll(), Optional.of(BigDecimal.valueOf(0)));

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));

        //when
        String actualInvoiceNo = this.stayService.chargeStay(stay.getStayId().getId());

        //then
        assertEquals(invoice.getInvoiceNo().getNo(), actualInvoiceNo);
    }


    @Test
    void given_composedinvoice_when_chargeStay_then_throw() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        List<Category> categories = createCategoriesDummy();

        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);

        Invoice invoice = stay.composeInvoice(this.categoryRepository.findAll(), Optional.of(BigDecimal.valueOf(0)));

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));

        //when..then
        assertThrows(IllegalStateException.class, () -> {
            String actualInvoiceNo = this.stayService.chargeStay(stay.getStayId().getId());
        }, "IllegalStateException was expected");
    }


    @Test
    void given_billedstay_when_checkout_then_checkedOut() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, BillingOpenException, PriceCurrencyMismatchException {
        //given
        Stay stay = createStayDummy();
        List<Category> categories = createCategoriesDummy();

        Mockito.when(this.categoryRepository.findAll()).thenReturn(categories);
        stay.composeInvoice(this.categoryRepository.findAll(), Optional.of(BigDecimal.valueOf(10)));

        //when
        stay.checkout();

        //then
        assertEquals(stay.getStayState(), StayState.CHECKED_OUT);
    }

    @Test
    void given_unbilledstay_when_checkout_then_throw() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();

        //when..then
        assertThrows(BillingOpenException.class, stay::checkout, "BillingOpenException was expected");
    }


    private StayDTO buildStayDto(Stay stay) throws CreateGuestException {
        return StayDTO.builder()
                .withStayEntity(stay)
                .withDetails(buildStayDetailsDto(stay))
                .build();
    }

    private StayDetailsDTO buildStayDetailsDto(Stay stay) throws CreateGuestException {
        return StayDetailsDTO.builder()
                .withStayEntity(stay)
                .withGuestDTO(buildGuestDto(createGuestDummy()))
                .withRoomNumbers(createRoomNumbersDummy())
                .build();
    }

    private GuestDTO buildGuestDto(Guest guest) {
        return GuestDTO.builder()
                .withGuestEntity(guest)
                .build();
    }

    private Set<InvoiceLineDTO> buildLineItemsDto(Set<InvoiceLine> lineItems) {
        Set<InvoiceLineDTO> lineItemsDto = new HashSet<>();

        for (InvoiceLine lineItem : lineItems) {
            lineItemsDto.add(InvoiceLineDTO.builder().withInvoiceLineEntity(lineItem).build());
        }

        return lineItemsDto;
    }

    private List<RoomNumber> createRoomNumbersDummy() {
        return Arrays.asList(
                new RoomNumber("101"),
                new RoomNumber("102"),
                new RoomNumber("103")
        );
    }

    private List<Category> createCategoriesDummy() throws RoomAlreadyExistsException {
        Price p1 = Price.of(BigDecimal.valueOf(150), Currency.getInstance("EUR"));
        Category category1 = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, p1, p1);
        category1.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));

        Price p2 = Price.of(BigDecimal.valueOf(200), Currency.getInstance("EUR"));
        Category category2 = CategoryFactory.createCategory(new CategoryId("2"), "Business Casual DZ", "A casual accommodation for business guests.", 1, p2, p2);
        category2.createRoom(new Room(new RoomNumber("200"), RoomState.AVAILABLE));

        return Arrays.asList(category1, category2);
    }


    private Stay createStayDummy() throws CreateBookingException, RoomAlreadyExistsException, CreateStayException {
        //Category
        Price p = Price.of(BigDecimal.valueOf(200), Currency.getInstance("EUR"));
        Category category = CategoryFactory.createCategory(new CategoryId("1"), "Business Casual EZ", "A casual accommodation for business guests.", 1, p, p);
        category.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));

        //Booking
        LocalDate arrivalDate = getContextLocalDate();
        LocalDate departureDate = getContextLocalDate().plusDays(2);
        LocalTime arrivalTime = getContextLocalTime();
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put(category, 1);
        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Franz Huber", "1234 5678 9012 3456", "05/24", "123", String.valueOf(PaymentType.CASH));

        BookingNo bookingNo = this.bookingRepository.nextIdentity();
        Booking booking = BookingFactory.createBooking(
                nextDummyBookingIdentity(),
                arrivalDate,
                departureDate,
                arrivalTime,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation);

        return StayFactory.createStayForBooking(
                nextDummyStayIdentity(),
                booking,
                bookingNo,
                arrivalDate,
                departureDate,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation
        );
    }

    private Guest createGuestDummy() throws CreateGuestException {
        Address address = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        return GuestFactory.createGuest(
                new GuestId("1"),
                null, String.valueOf(Salutation.MISTER),
                "Fritz",
                "Mayer",
                getContextLocalDate().minusYears(18L),
                address,
                ""
        );
    }

    private StayId nextDummyStayIdentity() {
        return new StayId((nextDummyStayIdentity++).toString());
    }

    private BookingNo nextDummyBookingIdentity() {
        return new BookingNo((nextDummyBookingIdentity++).toString());
    }

}