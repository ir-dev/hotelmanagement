package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.converters.CategoryConverter;
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
    void given_stayinrepository_when_staybystayId_then_return() throws RoomAlreadyExistsException, CreateBookingException, CreateStayException, CreateGuestException {
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
    void given_nostayinrepository_when_staybystayId_then_returnEmpty() {
        //given
        StayId stayId = new StayId("1");
        Mockito.when(this.stayRepository.findById(stayId)).thenReturn(Optional.empty());

        //when
        Optional<StayDTO> stayDto = this.stayService.stayByStayId(stayId.getId());

        //then
        assertTrue(stayDto.isEmpty());
    }

    @Test
    void given_invoiceNoinrepository_when_invoicebyinvoiceNo_then_returnInvoice() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, PriceCurrencyMismatchException {
        //given
        Stay stay = createStayDummy();
        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        Optional<BigDecimal> discount = Optional.of(BigDecimal.valueOf(10));

        createCategoriesDummy().forEach(category -> selectedLineItemProductsCount.put(category, 2));

        InvoiceNo invoiceNo = stay.finalizeInvoice("1", selectedLineItemProductsCount, discount);
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

        Mockito.when(this.stayRepository.findByInvoiceNo(invoiceNo)).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.stayRepository.findInvoiceByInvoiceNo(invoiceNo)).thenReturn(stay.getInvoices().stream().filter(invoice1 -> invoice1.getInvoiceNo().equals(invoiceNo)).findFirst());

        //when
        Optional<InvoiceDTO> actualInvoiceDto = this.stayService.invoiceByInvoiceNo(invoiceNo.getNo());

        //then
        assertTrue(invoiceDtosExpected.contains(actualInvoiceDto.orElseThrow()));
    }

    @Test
    void given_invoiceNo_when_invoicebyinvoiceNo_then_returnEmpty() {
        //given
        InvoiceNo invoiceNo = new InvoiceNo("1");

        Mockito.when(this.stayRepository.findInvoiceByInvoiceNo(invoiceNo)).thenReturn(Optional.empty());

        //when
        Optional<InvoiceDTO> invoiceDto = this.stayService.invoiceByInvoiceNo(invoiceNo.getNo());

        //then
        assertTrue(invoiceDto.isEmpty());
    }

    @Test
    void given_stayinrepository_and_noguestinrepository_when_invoicebyinvoiceNo_then_returnEmpty() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();
        InvoiceNo invoiceNo = new InvoiceNo("1");

        Mockito.when(this.stayRepository.findByInvoiceNo(invoiceNo)).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.empty());

        //when
        Optional<InvoiceDTO> invoiceDto = this.stayService.invoiceByInvoiceNo(invoiceNo.getNo());

        //then
        assertTrue(invoiceDto.isEmpty());
    }
    @Test
    void given_stayinrepository_and_noinvoiceNoinrepository_when_invoicebyinvoisceNo_then_returnEmpty() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        InvoiceNo invoiceNo = new InvoiceNo("1");

        Mockito.when(this.stayRepository.findByInvoiceNo(invoiceNo)).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(guest.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.stayRepository.findInvoiceByInvoiceNo(invoiceNo)).thenReturn(Optional.empty());

        //when
        Optional<InvoiceDTO> invoiceDto = this.stayService.invoiceByInvoiceNo(invoiceNo.getNo());

        //then
        assertTrue(invoiceDto.isEmpty());
    }

    @Test
    void given_finalizedinvoices_when_allinvoices_then_returnequals() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, PriceCurrencyMismatchException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        Map<Category, Integer> selectedLineItemProductsCount1 = new HashMap<>();
        Map<Category, Integer> selectedLineItemProductsCount2 = new HashMap<>();
        Optional<BigDecimal> discount = Optional.of(BigDecimal.valueOf(10));
        Guest guest = createGuestDummy();

        createCategoriesDummy().forEach(i -> selectedLineItemProductsCount1.put(i, 1));
        createCategoriesDummy().forEach(i -> selectedLineItemProductsCount2.put(i, 1));

        InvoiceNo invoiceNo1 = stay.finalizeInvoice("1", selectedLineItemProductsCount1, discount);
        InvoiceNo invoiceNo2 = stay.finalizeInvoice("2", selectedLineItemProductsCount2, discount);

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

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));

        List<InvoiceDTO> invoiceDtosActual = this.stayService.allStayInvoices(stay.getStayId().getId());

        for (InvoiceDTO inv : invoiceDtosActual) {
            assertTrue(invoiceDtosExpected.contains(inv));
        }
    }

    @Test
    void given_stayinrepository_and_nostayinvoicesinrepository_when_allStayInvoices_then_returnEmpty() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(guest.getGuestId())).thenReturn(Optional.of(guest));

        //when
        List<InvoiceDTO> invoiceDtos = this.stayService.allStayInvoices(stay.getStayId().getId());

        //then
        assertTrue(invoiceDtos.isEmpty());
    }

    @Test
    void given_nostayinrepository_when_allStayInvoices_then_throws() {
        //given
        StayId stayId = new StayId("1");

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            List<InvoiceDTO> invoiceDtos = this.stayService.allStayInvoices(stayId.getId());
        }, "EntityNotFoundException was expected");

    }

    @Test
    void given_stayinrepository_and_noguestinrepository_when_allStayInvoices_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            List<InvoiceDTO> invoiceDtos = this.stayService.allStayInvoices(stay.getStayId().getId());
        }, "EntityNotFoundException was expected");

    }



    @Test
    void given_previewinvoice_with_billableLineItems_when_chargeStayPreview_then_returnequals() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        List<Category> categories = createCategoriesDummy();

        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));

        Map<Category, Integer> billableLineItemCounts = CategoryConverter.convertToSelectedCategoriesRoomCount(stay.billableLineItemCounts());
        Optional<BigDecimal> discount = Optional.of(BigDecimal.valueOf(0));

        Invoice previewInvoice = stay.generateInvoice(billableLineItemCounts, discount);
        InvoiceDTO previewInvoiceDtoExpected = InvoiceDTO.builder()
                .withInvoiceEntity(previewInvoice)
                .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                .withStayId(stay.getStayId())
                .withLineItemsDTO(buildLineItemsDto(previewInvoice.getLineItems()))
                .build();

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));

        //when
        InvoiceDTO actualPreviewInvoiceDto = this.stayService.chargeStayPreview(stay.getStayId().getId());

        //then
        assertEquals(previewInvoiceDtoExpected, actualPreviewInvoiceDto);
    }

    @Test
    void given_nostayinrepository_when_chargeStayPreview_then_throws() {
        //given
        StayId stayId = new StayId("1");

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            InvoiceDTO previewinvoiceDto = this.stayService.chargeStayPreview(stayId.getId());
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_stayinrepository_and_noguestinrepository_when_chargeStayPreview_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            InvoiceDTO previewinvoiceDto = this.stayService.chargeStayPreview(stay.getStayId().getId());
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_previewinvoice_with_selectedLineItems_when_chargeStayPreview_then_returnequals() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        Category category = createCategoriesDummy().get(0);

        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category, 1);
        Optional<BigDecimal> discount = Optional.of(BigDecimal.valueOf(0));

        Invoice previewInvoice = stay.generateInvoice(selectedLineItemProductsCount, discount);
        InvoiceDTO previewInvoiceDtoExpected = InvoiceDTO.builder()
                .withInvoiceEntity(previewInvoice)
                .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                .withStayId(stay.getStayId())
                .withLineItemsDTO(buildLineItemsDto(previewInvoice.getLineItems()))
                .build();

        Mockito.when(this.categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));
        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));

        //when
        InvoiceDTO actualPreviewInvoiceDto = this.stayService.chargeStayPreview(stay.getStayId().getId(), CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedLineItemProductsCount));

        //then
        assertEquals(previewInvoiceDtoExpected, actualPreviewInvoiceDto);
    }

    @Test
    void given_nostayinrepository_and_selectedLineItems_when_chargeStayPreview_then_throws() throws RoomAlreadyExistsException {
        //given
        StayId stayId = new StayId("1");
        Category category = createCategoriesDummy().get(0);
        Map<String, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category.getName(), 1);

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            InvoiceDTO previewinvoiceDto = this.stayService.chargeStayPreview(stayId.getId(), selectedLineItemProductsCount);
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_stayinrepository_and_noguestinrepository_and_selectedLineItems_when_chargeStayPreview_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();
        Category category = createCategoriesDummy().get(0);
        Map<String, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category.getName(), 1);

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            InvoiceDTO previewinvoiceDto = this.stayService.chargeStayPreview(stay.getStayId().getId(), selectedLineItemProductsCount);
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_invoiceNo_when_chargeStay_then_invoiceNoequals() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        Category category = createCategoriesDummy().get(0);
        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category, 1);

        InvoiceNo expectedInvoiceNo = new InvoiceNo("2020011");

        Mockito.when(this.categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));
        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.stayRepository.nextInvoiceSeq()).thenReturn(Optional.of("1"));

        //when
        String actualInvoiceNo = this.stayService.chargeStay(stay.getStayId().getId(), CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedLineItemProductsCount));

        //then
        assertEquals(expectedInvoiceNo.getNo(), actualInvoiceNo);
    }

    @Test
    void given_chargedStay_when_chargeStay_then_throw() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        List<Category> categories = createCategoriesDummy();

        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        categories.forEach(category -> selectedLineItemProductsCount.put(category, 2)); //Select ALL line items

        InvoiceNo invoiceNo = stay.finalizeInvoice("1", selectedLineItemProductsCount, Optional.of(BigDecimal.valueOf(0)));

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));
        Mockito.when(this.stayRepository.nextInvoiceSeq()).thenReturn(Optional.of("1"));

        //when..then
        assertThrows(IllegalStateException.class, () -> {
            String actualInvoiceNo = this.stayService.chargeStay(stay.getStayId().getId(), CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedLineItemProductsCount));
        }, "IllegalStateException was expected");
    }

    @Test
    void given_nostayinrepository_when_chargeStay_then_throws() throws RoomAlreadyExistsException {
        //given
        StayId stayId = new StayId("1");
        Category category = createCategoriesDummy().get(0);

        Map<String, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category.getName(), 1);

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            String invoiceNo = this.stayService.chargeStay(stayId.getId(), selectedLineItemProductsCount);
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_stayinrepository_and_noguestinrepository_when_chargeStay_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();
        Category category = createCategoriesDummy().get(0);

        Map<String, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category.getName(), 1);

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            String invoiceNo = this.stayService.chargeStay(stay.getStayId().getId(), selectedLineItemProductsCount);
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_chargedstay_when_checkout_then_checkedOut() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, BillingOpenException, PriceCurrencyMismatchException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        List<Category> categories = createCategoriesDummy();
        Optional<BigDecimal> discount = Optional.of(BigDecimal.valueOf(10));

        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        categories.forEach(category -> selectedLineItemProductsCount.put(category, 2));

        stay.finalizeInvoice("1", selectedLineItemProductsCount, discount);

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when
        this.stayService.checkoutStay(stay.getStayId().getId());

        //then
        assertEquals(stay.getStayState(), StayState.CHECKED_OUT);
    }


    @Test
    void given_unbilledstay_when_checkout_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(BillingOpenException.class, () -> {
            this.stayService.checkoutStay(stay.getStayId().getId());
        }, "BillingOpenException was expected");
    }

    @Test
    void given_nostayinrepository_when_checkoutStay_then_throws() {
        //given
        StayId stayId = new StayId("1");

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            this.stayService.checkoutStay(stayId.getId());
        }, "EntityNotFoundException was expected");
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
        category1.createRoom(new Room(new RoomNumber("101"), RoomState.AVAILABLE));

        Price p2 = Price.of(BigDecimal.valueOf(200), Currency.getInstance("EUR"));
        Category category2 = CategoryFactory.createCategory(new CategoryId("2"), "Business Casual DZ", "A casual accommodation for business guests.", 2, p2, p2);
        category2.createRoom(new Room(new RoomNumber("200"), RoomState.AVAILABLE));
        category2.createRoom(new Room(new RoomNumber("201"), RoomState.AVAILABLE));

        return Arrays.asList(category1, category2);
    }


    private Stay createStayDummy() throws CreateBookingException, RoomAlreadyExistsException, CreateStayException {
        //Booking
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        LocalTime arrivalTime = LocalTime.now();
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();

        createCategoriesDummy().forEach(category -> selectCategoriesRoomCount.put(category, 2));

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
                null, String.valueOf(Salutation.MR),
                "Fritz",
                "Mayer",
                LocalDate.now().minusYears(18L),
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