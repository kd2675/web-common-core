package web.common.core.simulation;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimulationClockSnapshotsTest {

    @Test
    void calculate_twoHourSimulationDay_mapsElapsedRealSecondsToSimulationDateTime() {
        LocalDateTime now = LocalDateTime.of(2026, 7, 1, 11, 0);

        SimulationClockSnapshot snapshot = SimulationClockSnapshots.calculate(
                LocalDate.of(2026, 7, 1),
                7200,
                7200,
                true,
                LocalDateTime.of(2026, 7, 1, 10, 0),
                now,
                30,
                now
        );

        assertThat(snapshot.simulationDate()).isEqualTo(LocalDate.of(2026, 7, 2));
        assertThat(snapshot.simulationDateTime()).isEqualTo(LocalDateTime.of(2026, 7, 2, 12, 0));
        assertThat(snapshot.realDayStart()).isEqualTo(LocalDateTime.of(2026, 7, 1, 10, 0));
        assertThat(snapshot.realSecondsPerSimulationDay()).isEqualTo(7200);
    }

    @Test
    void calculate_staleRunningClock_freezesAtLastHeartbeat() {
        LocalDateTime lastStartedAt = LocalDateTime.of(2026, 7, 1, 10, 0);
        LocalDateTime lastHeartbeatAt = LocalDateTime.of(2026, 7, 1, 10, 1);

        SimulationClockSnapshot snapshot = SimulationClockSnapshots.calculate(
                LocalDate.of(2026, 7, 1),
                7200,
                0,
                true,
                lastStartedAt,
                lastHeartbeatAt,
                30,
                LocalDateTime.of(2026, 7, 1, 10, 2)
        );

        assertThat(snapshot.stale()).isTrue();
        assertThat(snapshot.realDateTime()).isEqualTo(lastHeartbeatAt);
        assertThat(snapshot.accumulatedRealSeconds()).isEqualTo(60);
    }

    @Test
    void calculate_truncatesRealClockBoundariesToSecond() {
        LocalDateTime now = LocalDateTime.of(2026, 7, 1, 20, 11, 2, 123_456_789);

        SimulationClockSnapshot snapshot = SimulationClockSnapshots.calculate(
                LocalDate.of(2026, 7, 1),
                7200,
                72_662,
                false,
                null,
                now,
                30,
                now
        );

        assertThat(snapshot.realDateTime()).isEqualTo(LocalDateTime.of(2026, 7, 1, 20, 11, 2));
        assertThat(snapshot.realDayStart()).isEqualTo(LocalDateTime.of(2026, 7, 1, 20, 0));
    }
}
