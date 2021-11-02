package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.application.api.ViewBookingsService;
import at.fhv.hotelmanagement.application.dto.BookingDTO;
import at.fhv.hotelmanagement.application.dto.BookingStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class ViewBookingServiceImpl implements ViewBookingsService {
    @Override
    public List<BookingDTO> getAll() {
        return Arrays.asList(BookingDTO.builder()
                                .withBookingNr("5678")
                                .withGuestName("Harald")
                                .withArrival(LocalDate.of(2021, 11, 1 ))
                                .withDeparture(LocalDate.of(2021, 11, 2 ))
                                .withBookingStatus(BookingStatus.Pending)
                                .withNrOfRooms(4)
                                .build(),
                                    BookingDTO.builder()
                                        .withBookingNr("5679")
                                        .withGuestName("Susanne")
                                        .withArrival(LocalDate.of(2021, 11, 1 ))
                                        .withDeparture(LocalDate.of(2021, 11, 2 ))
                                        .withBookingStatus(BookingStatus.Closed)
                                        .withNrOfRooms(4)
                                        .build());
    }

    @Override
    public BookingDTO getById(String bookingId) {
        return BookingDTO.builder()
                .withBookingNr("5678")
                .withGuestName("Harald")
                .withArrival(LocalDate.of(2021, 11, 1 ))
                .withDeparture(LocalDate.of(2021, 11, 2 ))
                .withBookingStatus(BookingStatus.Pending)
                .withNrOfRooms(4)
                .build();
    }
}
