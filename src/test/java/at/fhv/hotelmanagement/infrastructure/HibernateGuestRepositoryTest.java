package at.fhv.hotelmanagement.infrastructure;

import at.fhv.hotelmanagement.AbstractTest;
import at.fhv.hotelmanagement.domain.model.category.RoomAlreadyExistsException;
import at.fhv.hotelmanagement.domain.model.guest.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class HibernateGuestRepositoryTest extends AbstractTest {

    @Autowired
    HibernateGuestRepository guestRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void given_3guestsinrepository_when_findallguests_then_returnequalsguests() throws CreateGuestException, RoomAlreadyExistsException {
        // given
        List<Guest> guestsExcepted = Arrays.asList(
                createGuestDummy(),
                createGuestDummy(),
                createGuestDummy()
        );
        guestsExcepted.forEach(guest -> {
            this.guestRepository.store(guest);
        });
        this.em.flush();

        // when
        List<Guest> guestsActual = this.guestRepository.findAll();

        // then
        assertEquals(guestsExcepted.size(), guestsActual.size());
        for (Guest g : guestsActual) {
            assertTrue(guestsExcepted.contains(g));
        }
    }

    @Test
    void given_emptyrespository_when_findallguests_then_returnemptyguests() {
        assertTrue(this.guestRepository.findAll().isEmpty());
    }

    @Test
    void given_guest_when_addguesttorepository_then_returnequalsguest() throws CreateGuestException, RoomAlreadyExistsException {
        // given
        Guest guestExpected = createGuestDummy();

        // when
        this.guestRepository.store(guestExpected);
        this.em.flush();
        Guest guestActual = this.guestRepository.findById(guestExpected.getGuestId()).get();

        // then
        assertEquals(guestExpected, guestActual);
        assertEquals(guestExpected.getGuestId(), guestActual.getGuestId());
    }

    private Guest createGuestDummy() throws CreateGuestException, RoomAlreadyExistsException {
        // create value objects for entities
        Address address = new Address(
                "Musterstr. 123",
                "12345",
                "Musterstadt",
                Country.AT.toString()
        );

        // create entity/entities
        return GuestFactory.createGuest(
                this.guestRepository.nextIdentity(),
                null,
                Salutation.MR.toString(),
                "Max",
                "Mustermann",
                LocalDate.now().minusYears(18L),
                address,
                ""
        );
    }
}

