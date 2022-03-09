package gamma02.gammasmultiblocklib.MultiblockDetection;

import gamma02.gammasmultiblocklib.lib.MultiblockController;

/**
 * <h1>Multiblock Detection Ticker Type</h1><br>
 *
 * This class is used to provide the minimum length of time (in ticks or milliseconds) that passes between each attempt
 * of the {@link DetectionThread} to detect the structure or validity of a multiblock. It's pretty simple, just make
 * your own implimentation of {@link MultiblockController#getDetectionTickerType()} and make sure it's not null, and
 * has a valid (non negative real) value.
 *
 * @see DetectionThread
 * @see MultiblockController
 */
public class MultiblockDetectionTickerType {

    /**
     * This decides weather the multiblock detection thread counts ticks or seconds for detection.
     */
    private boolean countsWithTicks;

    /**
     * This is the container for the amount of time that is stored here.
     */
    private long time = 0;

    /**
     * This is the amount of time in ticks or milliseconds that passes between checks of the multiblock.
     */
    private long durationBetweenChecks;

    private MultiblockDetectionTickerType(boolean ticksOrSeconds, long time){
        this.countsWithTicks = ticksOrSeconds;
        this.durationBetweenChecks = time;
    }

    /**
     * Gets an instance of this with seconds
     * @param time the amount of time to pass between checks in milliseconds
     * @return an instance of this with seconds
     */
    public static MultiblockDetectionTickerType getWithSeconds(long time){
        return new MultiblockDetectionTickerType(false, time);
    }

    /**
     * Gets an instance of this with ticks
     * @param ticks the amount of ticks to pass between checks
     * @return an instance of this with ticks
     */
    public static MultiblockDetectionTickerType getWithTicks(long ticks){
        return new MultiblockDetectionTickerType(true, ticks);
    }

    public static MultiblockDetectionTickerType ONE_TICK = new MultiblockDetectionTickerType(true, 1);


    public boolean isCountsWithTicks() {
        return countsWithTicks;
    }

    public void setCountsWithTicks(boolean countsWithTicks) {
        this.countsWithTicks = countsWithTicks;
    }

    public long getTime() {
        return time;
    }

    void setTime(long time) {
        this.time = time;
    }

    public long getDurationBetweenChecks() {
        return durationBetweenChecks;
    }

    public void setDurationBetweenChecks(long durationBetweenChecks) {
        this.durationBetweenChecks = durationBetweenChecks;
    }
}
