package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.converters.CategoryConverter;
import at.fhv.hotelmanagement.application.dto.*;
import at.fhv.hotelmanagement.domain.model.PriceCurrencyMismatchException;
import at.fhv.hotelmanagement.domain.model.booking.*;
import at.fhv.hotelmanagement.domain.model.category.*;
import at.fhv.hotelmanagement.domain.model.guest.*;
import at.fhv.hotelmanagement.domain.model.stay.*;
import at.fhv.hotelmanagement.domain.repositories.CategoryRepository;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.repositories.StayRepository;
import at.fhv.hotelmanagement.domain.model.Price;
import at.fhv.hotelmanagement.domain.repositories.BookingRepository;
import at.fhv.hotelmanagement.view.forms.InvoiceRecipientForm;
import at.fhv.hotelmanagement.view.forms.StayForm;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    @Test
    void given_stayform_with_booking_when_createstayforbooking_then_booking_closed() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, RoomAssignmentException {
        //given
        Booking booking = createBookingDummy();
        List<Category> categories = createCategoriesDummy();
        Guest guest = createGuestDummy();

        BookingDTO bookingDTO = BookingDTO.builder()
                .withBookingEntity(booking)
                .withDetails(BookingDetailsDTO.builder()
                        .withBookingEntity(booking)
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                        .build())
                .build();
        StayForm stayForm = new StayForm();
        stayForm.addBooking(bookingDTO);

        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));

        //when.
        this.stayService.createStayForBooking(booking.getBookingNo().getNo(), stayForm);

        //then
        assertEquals(BookingState.CLOSED, booking.getBookingState());
    }


    @Test
    void given_stayform_with_booking_and_nocategoryinrepository_when_createstayforbooking_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException {
        //given
        Booking booking = createBookingDummy();
        Guest guest = createGuestDummy();
        BookingDTO bookingDTO = BookingDTO.builder()
                                        .withBookingEntity(booking)
                                        .withDetails(BookingDetailsDTO.builder()
                                                .withBookingEntity(booking)
                                                .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                                                .build())
                                        .build();
        StayForm stayForm = new StayForm();
        stayForm.addBooking(bookingDTO);

        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));


        //when..then
        assertThrows(NoSuchElementException.class, () -> {
                        this.stayService.createStayForBooking(booking.getBookingNo().getNo(), stayForm);
        },      "NoSuchElementException was expected");
    }

    @Test
    void given_stayform_with_booking_and_guest_with25percentdiscount_when_createstayforbooking_then_booking_closed() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, RoomAssignmentException {
        //given
        Booking booking = createBookingDummy();
        Guest guest = createGuestWithOrganizationDummy(BigDecimal.valueOf(0.25));
        List<Category> categories = createCategoriesDummy();
        BookingDTO bookingDTO = BookingDTO.builder()
                .withBookingEntity(booking)
                .withDetails(BookingDetailsDTO.builder()
                        .withBookingEntity(booking)
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                        .build())
                .build();
        StayForm stayForm = new StayForm();
        stayForm.addBooking(bookingDTO);

        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));

        //when
        this.stayService.createStayForBooking(booking.getBookingNo().getNo(), stayForm);

        //then
        assertEquals(BookingState.CLOSED, booking.getBookingState());
    }

    @Test
    void given_stayform_with_booking_and_guest_with150percentdiscount_when_createstayforbooking_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, RoomAssignmentException {
        //given
        Booking booking = createBookingDummy();
        Guest guest = createGuestWithOrganizationDummy(BigDecimal.valueOf(1.5));
        List<Category> categories = createCategoriesDummy();
        BookingDTO bookingDTO = BookingDTO.builder()
                .withBookingEntity(booking)
                .withDetails(BookingDetailsDTO.builder()
                        .withBookingEntity(booking)
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                        .build())
                .build();
        StayForm stayForm = new StayForm();
        stayForm.addBooking(bookingDTO);

        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));

        //when..then
        assertThrows(CreateGuestException.class, () -> {
            this.stayService.createStayForBooking(booking.getBookingNo().getNo(), stayForm);
        }, "CreateGuestException was expected");
    }

    @Test
    void given_stayform_and_guest_withminus20percentdiscount_when_createstayforbooking_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, RoomAssignmentException {
        //given
        Booking booking = createBookingDummy();
        Guest guest = createGuestWithOrganizationDummy(BigDecimal.valueOf(-0.2));
        List<Category> categories = createCategoriesDummy();
        BookingDTO bookingDTO = BookingDTO.builder()
                .withBookingEntity(booking)
                .withDetails(BookingDetailsDTO.builder()
                        .withBookingEntity(booking)
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                        .build())
                .build();
        StayForm stayForm = new StayForm();
        stayForm.addBooking(bookingDTO);

        Mockito.when(this.bookingRepository.findByNo(booking.getBookingNo())).thenReturn(Optional.of(booking));
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));

        //when..then
        assertThrows(CreateGuestException.class, () -> {
            this.stayService.createStayForBooking(booking.getBookingNo().getNo(), stayForm);
        }, "CreateGuestException was expected");
    }

    @Test
    void given_stayform_when_createstayforwalkin_then_not_throws() throws RoomAlreadyExistsException {
        //given
        StayForm stayForm = initializeStayForm();
        List<Category> categories = createCategoriesDummy();

        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));

        //when..then
        assertDoesNotThrow(() -> {
            this.stayService.createStayForWalkIn(stayForm);
        });
    }

    @Test
    void given_stayform_and_nocategoryinrepository_when_createstayforwalkin_then_throws() {
        //given
        StayForm stayForm = initializeStayForm();

        //when..then
        assertThrows(NoSuchElementException.class, () -> {
            this.stayService.createStayForWalkIn(stayForm);
        }, "NoSuchElementException was expected");
    }

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
        BigDecimal discount = BigDecimal.valueOf(10);
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();
        InvoiceRecipient invoiceRecipient = createInvoiceRecipientWithFormDummy(invoiceRecipientForm);

        createCategoriesDummy().forEach(category -> selectedLineItemProductsCount.put(category, 2));

        InvoiceNo invoiceNo = stay.finalizeInvoice("1", selectedLineItemProductsCount, discount, invoiceRecipient);
        Guest guest = createGuestDummy();

        List<InvoiceDTO> invoiceDtosExpected = new ArrayList<>();
        for (Invoice inv : stay.getInvoices()) {
            invoiceDtosExpected.add(InvoiceDTO.builder()
                    .withStayId(stay.getStayId())
                    .withInvoiceRecipientDTO(InvoiceRecipientDTO.builder().withInvoiceRecipientEntity(invoiceRecipient).build())
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
        BigDecimal discount = BigDecimal.valueOf(10);
        Guest guest = createGuestDummy();
        InvoiceRecipient invoiceRecipient = createGuestAsInvoiceRecipient(guest);

        createCategoriesDummy().forEach(i -> selectedLineItemProductsCount1.put(i, 1));
        createCategoriesDummy().forEach(i -> selectedLineItemProductsCount2.put(i, 1));

        InvoiceNo invoiceNo1 = stay.finalizeInvoice("1", selectedLineItemProductsCount1, discount, invoiceRecipient);
        InvoiceNo invoiceNo2 = stay.finalizeInvoice("2", selectedLineItemProductsCount2, discount, invoiceRecipient);

        List<InvoiceDTO> invoiceDtosExpected = new ArrayList<>();
        for (Invoice inv : stay.getInvoices()) {
            invoiceDtosExpected.add(InvoiceDTO.builder()
                    .withStayId(stay.getStayId())
                    .withInvoiceRecipientDTO(InvoiceRecipientDTO.builder().withInvoiceRecipientEntity(invoiceRecipient).build())
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
    void given_previewinvoice_with_billableLineItems_when_chargeStayPreview_then_returnequals() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException, GenerateInvoiceException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        List<Category> categories = createCategoriesDummy();
        InvoiceRecipient invoiceRecipient = createGuestAsInvoiceRecipient(guest);

        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));

        Map<Category, Integer> billableLineItemCounts = CategoryConverter.convertToSelectedCategoriesRoomCount(stay.billableLineItemCounts());
        BigDecimal discount = BigDecimal.valueOf(0);

        Invoice previewInvoice = stay.generateInvoice(billableLineItemCounts, discount, invoiceRecipient);
        InvoiceDTO previewInvoiceDtoExpected = InvoiceDTO.builder()
                .withInvoiceEntity(previewInvoice)
                .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                .withStayId(stay.getStayId())
                .withInvoiceRecipientDTO(InvoiceRecipientDTO.builder().withInvoiceRecipientEntity(invoiceRecipient).build())
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
    void given_previewinvoice_with_selectedLineItems_when_chargeStayPreview_then_returnequals() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException, GenerateInvoiceException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        Category category = createCategoriesDummy().get(0);
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();
        InvoiceRecipient invoiceRecipient = createInvoiceRecipientWithFormDummy(invoiceRecipientForm);

        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category, 1);
        BigDecimal discount = BigDecimal.valueOf(0);

        Invoice previewInvoice = stay.generateInvoice(selectedLineItemProductsCount, discount, invoiceRecipient);
        InvoiceDTO previewInvoiceDtoExpected = InvoiceDTO.builder()
                .withInvoiceEntity(previewInvoice)
                .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                .withStayId(stay.getStayId())
                .withInvoiceRecipientDTO(InvoiceRecipientDTO.builder().withInvoiceRecipientEntity(invoiceRecipient).build())
                .withLineItemsDTO(buildLineItemsDto(previewInvoice.getLineItems()))
                .build();

        Mockito.when(this.categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));
        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));

        //when
        InvoiceDTO actualPreviewInvoiceDto = this.stayService.chargeStayPreview(stay.getStayId().getId(), CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedLineItemProductsCount), invoiceRecipientForm);

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
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            InvoiceDTO previewinvoiceDto = this.stayService.chargeStayPreview(stayId.getId(), selectedLineItemProductsCount, invoiceRecipientForm);
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_stayinrepository_and_noguestinrepository_and_selectedLineItems_when_chargeStayPreview_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();
        Category category = createCategoriesDummy().get(0);
        Map<String, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category.getName(), 1);
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            InvoiceDTO previewinvoiceDto = this.stayService.chargeStayPreview(stay.getStayId().getId(), selectedLineItemProductsCount, invoiceRecipientForm);
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_invoiceNo_when_chargeStay_then_invoiceNoequals() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException, EntityNotFoundException, GenerateInvoiceException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        Category category = createCategoriesDummy().get(0);
        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category, 1);
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();

        InvoiceNo expectedInvoiceNo = new InvoiceNo("2020011");

        Mockito.when(this.categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));
        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.stayRepository.nextInvoiceSeq()).thenReturn("1");

        //when
        String actualInvoiceNo = this.stayService.chargeStay(stay.getStayId().getId(), CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedLineItemProductsCount), invoiceRecipientForm);

        //then
        assertEquals(expectedInvoiceNo.getNo(), actualInvoiceNo);
    }

    @Test
    void given_chargedStay_when_chargeStay_then_throw() throws PriceCurrencyMismatchException, CreateBookingException, CreateStayException, RoomAlreadyExistsException, CreateGuestException {
        //given
        Stay stay = createStayDummy();
        Guest guest = createGuestDummy();
        List<Category> categories = createCategoriesDummy();
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();
        InvoiceRecipient invoiceRecipient = createInvoiceRecipientWithFormDummy(invoiceRecipientForm);

        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        categories.forEach(category -> selectedLineItemProductsCount.put(category, 2)); //Select ALL line items

        InvoiceNo invoiceNo = stay.finalizeInvoice("1", selectedLineItemProductsCount, BigDecimal.valueOf(0), invoiceRecipient);

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(stay.getGuestId())).thenReturn(Optional.of(guest));
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));
        Mockito.when(this.stayRepository.nextInvoiceSeq()).thenReturn("1");

        //when..then
        assertThrows(IllegalStateException.class, () ->
                        this.stayService.chargeStay(
                                stay.getStayId().getId(),
                                CategoryConverter.convertToSelectedCategoryNamesRoomCount(selectedLineItemProductsCount), invoiceRecipientForm),
                "IllegalStateException was expected");
    }

    @Test
    void given_nostayinrepository_when_chargeStay_then_throws() throws RoomAlreadyExistsException {
        //given
        StayId stayId = new StayId("1");
        Category category = createCategoriesDummy().get(0);
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();

        Map<String, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category.getName(), 1);

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            String invoiceNo = this.stayService.chargeStay(stayId.getId(), selectedLineItemProductsCount, invoiceRecipientForm);
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_stayinrepository_and_noguestinrepository_when_chargeStay_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();
        Category category = createCategoriesDummy().get(0);
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();

        Map<String, Integer> selectedLineItemProductsCount = new HashMap<>();
        selectedLineItemProductsCount.put(category.getName(), 1);

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(EntityNotFoundException.class, () -> {
            String invoiceNo = this.stayService.chargeStay(stay.getStayId().getId(), selectedLineItemProductsCount, invoiceRecipientForm);
        }, "EntityNotFoundException was expected");
    }

    @Test
    void given_chargedstay_when_checkout_then_checkedOut() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, BillingOpenException, PriceCurrencyMismatchException, EntityNotFoundException {
        //given
        Stay stay = createStayDummy();
        List<Category> categories = createCategoriesDummy();
        BigDecimal discount = BigDecimal.valueOf(10);
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();
        InvoiceRecipient invoiceRecipient = createInvoiceRecipientWithFormDummy(invoiceRecipientForm);
        List<RoomNumber> roomNumbers = Arrays.asList(new RoomNumber("200"), new RoomNumber("201"), new RoomNumber("100"), new RoomNumber("101"));

        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        categories.forEach(category -> selectedLineItemProductsCount.put(category, 2)); //Select ALL line items

        stay.finalizeInvoice("1", selectedLineItemProductsCount, discount, invoiceRecipient);

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));
        Mockito.when(this.categoryRepository.findByName(categories.get(0).getName())).thenReturn(Optional.of(categories.get(0)));
        Mockito.when(this.categoryRepository.findByName(categories.get(1).getName())).thenReturn(Optional.of(categories.get(1)));
        Mockito.when(this.categoryRepository.findRoomNumbersByStayId(stay.getStayId())).thenReturn(roomNumbers);

        //when
        this.stayService.checkoutStay(stay.getStayId().getId());

        //then
        assertEquals(StayState.CHECKED_OUT, stay.getStayState());
        assertEquals(LocalDateTime.now(), stay.getCheckedOutAt().orElseThrow());
    }


    @Test
    void given_unbilledstay_when_checkout_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException {
        //given
        Stay stay = createStayDummy();

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(BillingOpenException.class, () ->
                this.stayService.checkoutStay(stay.getStayId().getId()),
                "BillingOpenException was expected");
    }

    @Test
    void given_nostayinrepository_when_checkoutStay_then_throws() {
        //given
        StayId stayId = new StayId("1");

        //when..then
        assertThrows(EntityNotFoundException.class, () ->
                this.stayService.checkoutStay(stayId.getId()),
                "EntityNotFoundException was expected");
    }

    @Test
    void given_stayinrepository_and_nocategoriesinrepository_when_checkoutStay_then_throws() throws CreateBookingException, CreateStayException, RoomAlreadyExistsException, BillingOpenException, EntityNotFoundException, PriceCurrencyMismatchException {
        //given
        Stay stay = createStayDummy();
        List<Category> categories = createCategoriesDummy();
        Map<Category, Integer> selectedLineItemProductsCount = new HashMap<>();
        categories.forEach(category -> selectedLineItemProductsCount.put(category, 2)); //Select ALL line items
        BigDecimal discount = BigDecimal.valueOf(10);
        InvoiceRecipientForm invoiceRecipientForm = initializeInvoiceRecipientForm();
        InvoiceRecipient invoiceRecipient = createInvoiceRecipientWithFormDummy(invoiceRecipientForm);

        stay.finalizeInvoice("1", selectedLineItemProductsCount, discount, invoiceRecipient);

        Mockito.when(this.stayRepository.findById(stay.getStayId())).thenReturn(Optional.of(stay));

        //when..then
        assertThrows(NoSuchElementException.class, () ->
                        this.stayService.checkoutStay(stay.getStayId().getId()),
                "NoSuchElementException was expected");
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
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();

        createCategoriesDummy().forEach(category -> selectCategoriesRoomCount.put(category, 2));

        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Franz Huber", "1234 5678 9012 3456", "05/24", "123", String.valueOf(PaymentType.CASH));

        Booking booking = createBookingDummy();

        return StayFactory.createStayForBooking(
                new StayId("S100000"),
                booking,
                new BookingNo("B100000"),
                arrivalDate,
                departureDate,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation
        );
    }

    private Booking createBookingDummy() throws CreateBookingException, RoomAlreadyExistsException {
        //Booking
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        LocalTime arrivalTime = LocalTime.now();
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();

        createCategoriesDummy().forEach(category -> selectCategoriesRoomCount.put(category, 2));

        GuestId guestId = new GuestId("1");
        PaymentInformation paymentInformation = new PaymentInformation("Franz Huber", "1234 5678 9012 3456", "05/24", "123", String.valueOf(PaymentType.CASH));

        return BookingFactory.createBooking(
                new BookingNo("B100000"),
                arrivalDate,
                departureDate,
                arrivalTime,
                numberOfPersons,
                selectCategoriesRoomCount,
                guestId,
                paymentInformation);
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

    private Guest createGuestWithOrganizationDummy(BigDecimal discount) throws CreateGuestException {
        Address address = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Organization organization = new Organization("FHV", discount);
        return GuestFactory.createGuest(
                new GuestId("1"),
                organization,
                String.valueOf(Salutation.MS),
                "Fritz",
                "Mayer",
                LocalDate.now().minusYears(18L),
                address,
                ""
        );
    }

    private StayForm initializeStayForm() {
        StayForm stayForm = new StayForm();
        //Payment Information
        stayForm.setCardHolderName("Franz Huber");
        stayForm.setCardNumber("1234 5678 9012 3456");
        stayForm.setCardValidThru("05/24");
        stayForm.setCardCvc("123");
        stayForm.setPaymentType("CASH");
        //Guest Information
        stayForm.setSalutation("MR");
        stayForm.setFirstName("Fritz");
        stayForm.setLastName("Mayer");
        stayForm.setDateOfBirth(LocalDate.now().minusYears(18L));
        stayForm.setStreet("Musterstrasse 1");
        stayForm.setZipcode("6850");
        stayForm.setCity("Dornbirn");
        stayForm.setCountry("AT");
        stayForm.setSpecialNotes("");
        stayForm.setIsOrganization(false);
        //Stay Information
        stayForm.setDepartureDate(LocalDate.now().plusDays(2));
        stayForm.setNumberOfPersons(1);
        Map<String, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put("Business Casual EZ", 1);
        stayForm.setSelectedCategoriesRoomCount(selectCategoriesRoomCount);
        return stayForm;
    }

    private InvoiceRecipient createGuestAsInvoiceRecipient(Guest guest) {
        Address address = new Address(
                guest.getAddress().getStreet(),
                guest.getAddress().getZipcode(),
                guest.getAddress().getCity(),
                guest.getAddress().getCountry().toString());

        return new InvoiceRecipient(guest.getFirstName(), guest.getLastName(), address);
    }

    private InvoiceRecipientForm initializeInvoiceRecipientForm() {
        InvoiceRecipientForm invoiceRecipientForm = new InvoiceRecipientForm();
        invoiceRecipientForm.setFirstName("Sarah");
        invoiceRecipientForm.setLastName("Müller");
        invoiceRecipientForm.setStreet("Musterstrasse 2");
        invoiceRecipientForm.setZipcode("6890");
        invoiceRecipientForm.setCity("Lustenau");
        invoiceRecipientForm.setCountry("AT");
        return invoiceRecipientForm;
    }

    private InvoiceRecipient createInvoiceRecipientWithFormDummy(InvoiceRecipientForm invoiceRecipientForm) {
        Address address = new Address(
                invoiceRecipientForm.getStreet(),
                invoiceRecipientForm.getZipcode(),
                invoiceRecipientForm.getCity(),
                invoiceRecipientForm.getCountry());

        return new InvoiceRecipient(invoiceRecipientForm.getFirstName(), invoiceRecipientForm.getLastName(), address);
    }

}