package com.smartcodeltd.jenkinsci.plugins.buildmonitor.viewmodel.duration;

// todo: is this still needed? backend could pass the duration in milliseconds to the frontend, which in turn could make
// it human-readable
public class HumanReadableDuration extends Duration {
    private static final long MILLISECOND = 1;
    private static final long SECONDS = 1000 * MILLISECOND;
    private static final long MINUTES = 60 * SECONDS;
    private static final long HOURS = 60 * MINUTES;

    public HumanReadableDuration(long milliseconds) {
        super(milliseconds);
    }

    private long hours() {
        return duration / HOURS;
    }

    private long minutes() {
        return (duration - (hours() * HOURS)) / MINUTES;
    }

    private long seconds() {
        return (duration - (hours() * HOURS) - (minutes() * MINUTES)) / SECONDS;
    }

    @Override
    public String value() {
        String formatted;

        formatted = hours() > 0 ? hours() + "h " : "";
        formatted += minutes() > 0 ? minutes() + "m " : "";
        formatted += seconds() + "s";

        return formatted;
    }

    @Override
    public String toString() {
        return value();
    }
}
