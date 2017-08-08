package com.devanlocker.transitapplication;

/**
 * Created by devan on 8/1/2017.
 */

public class Arrival {
    private int blockId;
    private String runId;
    private int seconds;
    private int minutes;
    private int routeId;
    private boolean isDeparting;

    public Arrival(int blockId, String runId, int seconds, int minutes,
                   int routeId, boolean isDeparting) {
        this.blockId = blockId;
        this.runId = runId;
        this.seconds = seconds;
        this.minutes = minutes;
        this.routeId = routeId;
        this.isDeparting = isDeparting;
    }

    public int getBlockId() {
        return blockId;
    }

    public String getRunId() {
        return runId;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getRouteId() {
        return routeId;
    }

    public boolean getIsDeparting() {
        return isDeparting;
    }
}

