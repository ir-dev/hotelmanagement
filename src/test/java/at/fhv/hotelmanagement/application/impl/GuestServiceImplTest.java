package at.fhv.hotelmanagement.application.impl;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.application.api.GuestService;
import at.fhv.hotelmanagement.application.dto.GuestDTO;
import at.fhv.hotelmanagement.domain.repositories.GuestRepository;
import at.fhv.hotelmanagement.domain.model.guest.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GuestServiceImplTest extends AbstractTest {

    @Autowired
    private GuestService guestService;

    @MockBean
    private GuestRepository guestRepository;

    private static Integer nextDummyGuestIdentity = 1;

    @Test
    void given_emptyrepository_when_fetchingallguests_then_empty() {
        //given
        Mockito.when(this.guestRepository.findAll()).thenReturn(Collections.emptyList());
        //when
        List<GuestDTO> guestsDto = this.guestService.allGuests();
        //then
        assertTrue(guestsDto.isEmpty());
    }

    @Test
    void given_2guestsinrepository_when_fetchallguests_then_returnequalguests() throws CreateGuestException {
        //given
        List<Guest> guests = Arrays.asList(
                createGuestDummy(),
                createGuestDummy()
        );

        List<GuestDTO> guestsDtoExpected = new ArrayList<>();
        for (Guest guest : guests) {
            GuestDTO guestDto = GuestDTO.builder().withGuestEntity(guest).build();
            guestsDtoExpected.add(guestDto);
        }

        Mockito.when(this.guestRepository.findAll()).thenReturn(guests);

        //when
        List<GuestDTO> guestsDtoActual = this.guestService.allGuests();

        //then
        for (GuestDTO g : guestsDtoActual) {
            assertTrue(guestsDtoExpected.contains(g));
        }
    }

    @Test
    void given_guestinrepository_when_guestbyguestId_then_return() throws CreateGuestException {
        //given
        Guest guest = createGuestDummy();

        GuestDTO expectedGuestDTO = GuestDTO.builder()
                .withGuestEntity(guest)
                .build();

        Mockito.when(this.guestRepository.findById(guest.getGuestId())).thenReturn(Optional.of(guest));

        //when
        GuestDTO actualGuestDTO = this.guestService.guestByGuestId(guest.getGuestId().getId()).orElseThrow();

        //then
        assertEquals(expectedGuestDTO, actualGuestDTO);
    }

    @Test
    void given_guestId_when_guestbyguestId_then_returnEmpty() {
        //given
        GuestId guestId = new GuestId("1");
        Mockito.when(this.guestRepository.findById(guestId)).thenReturn(Optional.empty());

        //when
        Optional<GuestDTO> guestDto = this.guestService.guestByGuestId(guestId.getId());

        //then
        assertTrue(guestDto.isEmpty());
    }


    private Guest createGuestDummy() throws CreateGuestException {
        Address address = new Address("Musterstrasse 1", "6850", "Dornbirn", String.valueOf(Country.AT));
        return GuestFactory.createGuest(
                nextDummyGuestIdentity(),
                null, String.valueOf(Salutation.MR),
                "Fritz",
                "Mayer",
                LocalDate.now().minusYears(18L),
                address,
                ""
        );
    }

    private GuestId nextDummyGuestIdentity() {
        return new GuestId((nextDummyGuestIdentity++).toString());
    }


}