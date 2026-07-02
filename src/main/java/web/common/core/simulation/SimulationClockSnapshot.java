package web.common.core.simulation;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SimulationClockSnapshot(
        LocalDate simulationDate,
        LocalDateTime simulationDateTime,
        LocalDateTime simulationDayStart,
        LocalDateTime realDateTime,
        LocalDateTime realDayStart,
        int realSecondsPerSimulationDay,
        boolean running,
        boolean stale,
        long accumulatedRealSeconds,
        LocalDateTime lastStartedAt,
        LocalDateTime lastHeartbeatAt
) {
}
