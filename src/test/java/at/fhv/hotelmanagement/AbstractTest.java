package at.fhv.hotelmanagement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import java.time.*;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

import static org.mockito.Mockito.*;

public abstract class AbstractTest {
    public static final Instant FIXED_CLOCK_INSTANT = Instant.parse("2020-01-01T00:00:00Z"); // Z means UTC
    public static final ZoneId FIXED_CLOCK_ZONE_ID = ZoneId.of("UTC").normalized();

    private MockedStatic<Clock> mockedStaticClock;

    @BeforeEach
    protected void tearUp() {
        setTestClock();
    }

    protected void setTestClock() {
        resetToSystemClock();

        setClock(Clock.fixed(FIXED_CLOCK_INSTANT, FIXED_CLOCK_ZONE_ID));
    }

    protected void setTestClockPlus(TemporalAmount temporalAmount) {
        resetToSystemClock();

        setClock(Clock.fixed(FIXED_CLOCK_INSTANT.plus(temporalAmount), FIXED_CLOCK_ZONE_ID));
    }

    @AfterEach
    protected void tearDown() {
        resetToSystemClock();
    }

    protected void setClock(Clock clock) {
        Objects.requireNonNull(clock);
        resetToSystemClock();

        // setup time context
        // return a fixed clock for systemDefaultZone() which is used by many java.time classes like LocalDateTime, LocalDate and LocalTime
        this.mockedStaticClock = mockStatic(Clock.class);
        this.mockedStaticClock
                .when(Clock::systemDefaultZone)
                .thenReturn(clock);
    }

    // when it is intended to use other methods of mocked clock class or re-mock then it is required
    // to un-mock clock first (call this method)
    protected void resetToSystemClock() {
        if (this.mockedStaticClock != null && !this.mockedStaticClock.isClosed()) {
            this.mockedStaticClock.close();
        }
    }
}
