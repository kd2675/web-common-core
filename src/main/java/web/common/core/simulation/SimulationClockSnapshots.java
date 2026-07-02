package web.common.core.simulation;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class SimulationClockSnapshots {

    private static final long SIMULATION_DAY_SECONDS = 86_400L;

    private SimulationClockSnapshots() {
    }

    public static SimulationClockSnapshot calculate(
            LocalDate baseSimulationDate,
            int realSecondsPerSimulationDay,
            long accumulatedRealSeconds,
            boolean running,
            LocalDateTime lastStartedAt,
            LocalDateTime lastHeartbeatAt,
            long staleAfterSeconds,
            LocalDateTime now
    ) {
        LocalDateTime current = truncateToSecond(now);
        LocalDateTime startedAt = truncateToSecond(lastStartedAt);
        LocalDateTime heartbeatAt = truncateToSecond(lastHeartbeatAt);
        long elapsedSeconds = accumulatedRealSeconds;
        boolean stale = false;
        LocalDateTime realDateTime = heartbeatAt == null ? current : heartbeatAt;
        if (running && startedAt != null && heartbeatAt != null) {
            stale = isStale(heartbeatAt, current, staleAfterSeconds);
            realDateTime = stale ? heartbeatAt : current;
            elapsedSeconds += Math.max(0, Duration.between(startedAt, realDateTime).toSeconds());
        }

        int secondsPerDay = Math.max(1, realSecondsPerSimulationDay);
        long dayOffset = Math.floorDiv(elapsedSeconds, secondsPerDay);
        long secondsInDay = Math.floorMod(elapsedSeconds, secondsPerDay);
        long simulationSecondsInDay = Math.floorDiv(secondsInDay * SIMULATION_DAY_SECONDS, secondsPerDay);
        LocalDate simulationDate = baseSimulationDate.plusDays(dayOffset);
        LocalDateTime simulationDateTime = simulationDate.atStartOfDay().plusSeconds(simulationSecondsInDay);
        return new SimulationClockSnapshot(
                simulationDate,
                simulationDateTime,
                simulationDateTime.truncatedTo(ChronoUnit.DAYS),
                realDateTime,
                realDateTime.minusSeconds(secondsInDay),
                secondsPerDay,
                running,
                stale,
                elapsedSeconds,
                startedAt,
                heartbeatAt
        );
    }

    public static LocalDateTime effectiveRealDateTime(
            LocalDateTime lastHeartbeatAt,
            long staleAfterSeconds,
            LocalDateTime now
    ) {
        LocalDateTime current = truncateToSecond(now);
        LocalDateTime heartbeatAt = truncateToSecond(lastHeartbeatAt);
        if (heartbeatAt == null) {
            return current;
        }
        return isStale(heartbeatAt, current, staleAfterSeconds) ? heartbeatAt : current;
    }

    private static boolean isStale(LocalDateTime lastHeartbeatAt, LocalDateTime now, long staleAfterSeconds) {
        return Duration.between(lastHeartbeatAt, now).toSeconds() > staleAfterSeconds;
    }

    private static LocalDateTime truncateToSecond(LocalDateTime value) {
        return value == null ? null : value.truncatedTo(ChronoUnit.SECONDS);
    }
}
