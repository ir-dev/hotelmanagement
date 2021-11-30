package at.fhv.hotelmanagement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import java.time.*;

import static org.mockito.Mockito.*;

public abstract class AbstractTest {
    public static final Instant FIXED_CLOCK_INSTANT = Instant.parse("2020-01-01T00:00:00Z"); // Z means UTC
    public static final ZoneId FIXED_CLOCK_ZONE_ID = ZoneId.of("UTC").normalized();

    private MockedStatic<Clock> mockedStaticClock;

    @BeforeEach
    protected void tearUp() {
        // setup time context
        // return a fixed clock for systemDefaultZone() which is used by many java.time classes like LocalDateTime, LocalDate and LocalTime
        Clock fixedClock = Clock.fixed(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
        this.mockedStaticClock = mockStatic(Clock.class);
        this.mockedStaticClock
                .when(Clock::systemDefaultZone)
                .thenReturn(fixedClock);
    }

    @AfterEach
    protected void tearDown() {
        // reset time context
        this.mockedStaticClock.close();
    }

    protected LocalDateTime getContextLocalDateTime() {
        return LocalDateTime.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }

    protected LocalDate getContextLocalDate() {
        return LocalDate.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }

    protected LocalTime getContextLocalTime() {
        return LocalTime.ofInstant(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID);
    }
}
