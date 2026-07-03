package web.common.core.simulation;

import java.time.LocalDateTime;
import java.time.LocalTime;

public final class SimulationMarketSessions {

    private static final LocalTime DEFAULT_OPEN_TIME = LocalTime.of(6, 0);
    private static final LocalTime DEFAULT_CLOSE_TIME = LocalTime.of(18, 0);

    private SimulationMarketSessions() {
    }

    public static SimulationMarketSession resolve(LocalDateTime simulationDateTime) {
        return resolve(simulationDateTime, DEFAULT_OPEN_TIME, DEFAULT_CLOSE_TIME);
    }

    public static SimulationMarketSession resolve(
            LocalDateTime simulationDateTime,
            LocalTime openTime,
            LocalTime closeTime
    ) {
        if (simulationDateTime == null) {
            return SimulationMarketSession.PRE_OPEN;
        }
        LocalTime currentTime = simulationDateTime.toLocalTime();
        LocalTime normalizedOpenTime = openTime == null ? DEFAULT_OPEN_TIME : openTime;
        LocalTime normalizedCloseTime = closeTime == null ? DEFAULT_CLOSE_TIME : closeTime;
        if (!normalizedOpenTime.isBefore(normalizedCloseTime)) {
            throw new IllegalArgumentException("Simulation market open time must be before close time");
        }
        if (currentTime.isBefore(normalizedOpenTime)) {
            return SimulationMarketSession.PRE_OPEN;
        }
        if (currentTime.isBefore(normalizedCloseTime)) {
            return SimulationMarketSession.REGULAR;
        }
        return SimulationMarketSession.AFTER_CLOSE;
    }

    public static boolean isRegular(LocalDateTime simulationDateTime, LocalTime openTime, LocalTime closeTime) {
        return resolve(simulationDateTime, openTime, closeTime) == SimulationMarketSession.REGULAR;
    }

    public static boolean isAfterClose(LocalDateTime simulationDateTime, LocalTime openTime, LocalTime closeTime) {
        return resolve(simulationDateTime, openTime, closeTime) == SimulationMarketSession.AFTER_CLOSE;
    }
}
