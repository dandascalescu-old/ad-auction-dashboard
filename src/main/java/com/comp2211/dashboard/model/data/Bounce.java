package com.comp2211.dashboard.model.data;

public class Bounce {
    public enum BounceType {Pages, Time};

    private BounceType type;
    private byte maxPages;
    private long maxSeconds;
    private boolean allowInf;

    public Bounce(byte maxPages) {
        this.type = BounceType.Pages;
        this.maxPages = maxPages;
    }

    public Bounce(long maxSeconds, boolean allowInf) {
        this.type = BounceType.Time;
        this.maxSeconds = maxSeconds;
        this.allowInf = allowInf;
    }

    public BounceType getType() {
        return type;
    }

    public byte getMaxPages() {
        if (type.equals(BounceType.Pages))
            return maxPages;
        return -1;
    }

    public long getMaxSeconds() {
        if (type.equals(BounceType.Time))
            return maxSeconds;
        return -1L;
    }

    public boolean allowInf() {
        if (type.equals(BounceType.Time))
            return allowInf;
        return false;
    }
}
