package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.BookingsService;
import at.fhv.hotelmanagement.application.api.StayService;
import at.fhv.hotelmanagement.application.dto.ChargedCategoryDTO;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.application.dto.InvoiceDTO;
import at.fhv.hotelmanagement.application.dto.StayDTO;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateBookingRepository;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateCategoryRepository;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateGuestRepository;
import at.fhv.hotelmanagement.domain.infrastructure.HibernateStayRepository;
import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.PaymentType;
import at.fhv.hotelmanagement.domain.model.enums.RoomState;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("test")
public class StayServiceImplTest {

    @Autowired
    private StayService stayService;

    @MockBean
    private HibernateStayRepository stayRepository;

    @MockBean
    private HibernateBookingRepository bookingRepository;

    @MockBean
    private HibernateGuestRepository guestRepository;

    @MockBean
    private HibernateCategoryRepository categoryRepository;



    @Test
    void given_stay_when_chargeStay_then_returninvoice() throws AlreadyExistsException, CreateBookingException, CreateStayException, CreateGuestException {
        //given
            //Category
        Category category = CategoryFactory.createCategory("Business Casual EZ", "A casual accommodation for business guests.", 1);
        category.determinePrice(new Price(120,140));
        category.createRoom(new Room(new RoomNumber("100"), RoomState.AVAILABLE));

            //Guest
        GuestId guestId = new GuestId("1");
        Address address = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        Guest guest = GuestFactory.createGuest(guestId, null, String.valueOf(Salutation.MISTER), "Fritz", "Mayer", LocalDate.of(1979, 12, 24), address, "");

            //Booking
        LocalDate arrivalDate = LocalDate.now();
        LocalDate departureDate = LocalDate.now().plusDays(2);
        LocalTime arrivalTime = LocalTime.of(11, 30);
        Integer numberOfPersons = 1;
        Map<Category, Integer> selectCategoriesRoomCount = new HashMap<>();
        selectCategoriesRoomCount.put(category, 1);
        PaymentInformation paymentInformation = new PaymentInformation("Hans-Peter Mayer", "5432 9876 5678 1234", "12/21", "123", String.valueOf(PaymentType.INVOICE));
        Mockito.when(this.bookingRepository.nextIdentity()).thenReturn(new BookingNo(java.util.UUID.randomUUID().toString().toUpperCase()));
        BookingNo bookingNo = this.bookingRepository.nextIdentity();
        Booking booking = BookingFactory.createBooking(bookingNo, arrivalDate, departureDate, arrivalTime, numberOfPersons, selectCategoriesRoomCount, guestId, paymentInformation);

            //Stay
        Mockito.when(this.stayRepository.nextIdentity()).thenReturn(new StayId(java.util.UUID.randomUUID().toString().toUpperCase()));
        StayId stayId = this.stayRepository.nextIdentity();
        Stay stay = StayFactory.createStayForBooking(stayId, booking, bookingNo, arrivalDate, departureDate, numberOfPersons, selectCategoriesRoomCount, guestId, paymentInformation);


        Map<ChargedCategoryDTO, Integer> selectedCategoriesRoomCountDto = new HashMap<>();

        Map<String, Integer> selectedCategoriesRoomCount = stay.getSelectedCategoriesRoomCount();
        for (Map.Entry<String, Integer> selectedCategoryNameRoomCount : selectedCategoriesRoomCount.entrySet()) {
            selectedCategoriesRoomCountDto.put(ChargedCategoryDTO.builder().withCategory(category).build(), selectedCategoryNameRoomCount.getValue());
        }

        //Invoice
        Invoice invoice = stay.getInvoice();
        InvoiceDTO expectedInvoiceDto = InvoiceDTO.builder()
                        .withInvoiceId(invoice.getInvoiceId())
                        .withContractDate(invoice.getContractDate())
                        .withGuestDTO(GuestDTO.builder().withGuestEntity(guest).build())
                        .withSelectedCategoriesRoomCount(selectedCategoriesRoomCountDto)
                        .build();

        Mockito.when(this.stayRepository.findById(stayId)).thenReturn(Optional.of(stay));
        Mockito.when(this.guestRepository.findById(guestId)).thenReturn(Optional.of(guest));
        Mockito.when(this.categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));


        //when
        InvoiceDTO actualInvoiceDto = stayService.chargeStay(stayId.getId()).get();

        //then
        assertEquals(expectedInvoiceDto, actualInvoiceDto);
    }

}