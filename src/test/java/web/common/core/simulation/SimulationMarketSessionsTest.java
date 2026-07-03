package web.common.core.simulation;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationMarketSessionsTest {

    private static final LocalTime OPEN_TIME = LocalTime.of(6, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(18, 0);

    @Test
    void resolve_beforeOpen_returnsPreOpen() {
        SimulationMarketSession session = SimulationMarketSessions.resolve(
                LocalDateTime.of(2026, 7, 2, 5, 59, 59),
                OPEN_TIME,
                CLOSE_TIME
        );

        assertThat(session).isEqualTo(SimulationMarketSession.PRE_OPEN);
    }

    @Test
    void resolve_atOpen_returnsRegular() {
        SimulationMarketSession session = SimulationMarketSessions.resolve(
                LocalDateTime.of(2026, 7, 2, 6, 0),
                OPEN_TIME,
                CLOSE_TIME
        );

        assertThat(session).isEqualTo(SimulationMarketSession.REGULAR);
    }

    @Test
    void resolve_beforeClose_returnsRegular() {
        SimulationMarketSession session = SimulationMarketSessions.resolve(
                LocalDateTime.of(2026, 7, 2, 17, 59, 59),
                OPEN_TIME,
                CLOSE_TIME
        );

        assertThat(session).isEqualTo(SimulationMarketSession.REGULAR);
    }

    @Test
    void resolve_atClose_returnsAfterClose() {
        SimulationMarketSession session = SimulationMarketSessions.resolve(
                LocalDateTime.of(2026, 7, 2, 18, 0),
                OPEN_TIME,
                CLOSE_TIME
        );

        assertThat(session).isEqualTo(SimulationMarketSession.AFTER_CLOSE);
    }
}
