package at.fhv.hotelmanagement.domain.infrastructure;

import at.fhv.hotelmanagement.domain.model.*;
import at.fhv.hotelmanagement.domain.model.enums.Country;
import at.fhv.hotelmanagement.domain.model.enums.Salutation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class HibernateGuestRepositoryTest {

    private static final Instant FIXED_CLOCK_INSTANT = Instant.parse("2020-01-01T00:00:00Z");
    private static final ZoneId FIXED_CLOCK_ZONE_ID = ZoneId.of("UTC").normalized();

    @Autowired
    HibernateGuestRepository guestRepository;

    @PersistenceContext
    private EntityManager em;

    private MockedStatic<Clock> mockedStaticClock;

    @BeforeEach
    void tearUp() {
        // setup time context
        // return a fixed clock for systemDefaultZone() which is used by classes like LocalDateTime, LocalDate and LocalTime
        Clock fixedClock = Clock.fixed(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
        this.mockedStaticClock = Mockito.mockStatic(Clock.class);
        this.mockedStaticClock
                .when(Clock::systemDefaultZone)
                .thenReturn(fixedClock);
    }

    @AfterEach
    void tearDown() {
        // reset time context
        this.mockedStaticClock.close();
    }

    private LocalDateTime getTestLocalDateTime() {
        return LocalDateTime.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }

    private LocalDate getTestLocalDate() {
        return LocalDate.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }

    private LocalTime getTestLocalTime() {
        return LocalTime.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }

    @Test
    void given_3guestsinrepository_when_findallguests_then_returnequalsguests() throws CreateGuestException, AlreadyExistsException {
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
    void given_guest_when_addguesttorepository_then_returnequalsguest() throws CreateGuestException, AlreadyExistsException {
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

    private Guest createGuestDummy() throws CreateGuestException, AlreadyExistsException {
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
                Salutation.MISTER.toString(),
                "Max",
                "Mustermann",
                getTestLocalDate().minusYears(18L),
                address,
                ""
        );
    }
}

