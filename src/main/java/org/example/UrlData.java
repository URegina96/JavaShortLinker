package org.example;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

class UrlData {
    private final String longUrl;
    private final UUID userId;
    private final AtomicInteger clickCount = new AtomicInteger(0);
    private final long creationTime = System.currentTimeMillis();
    private final int clickLimit;

    UrlData(String longUrl, UUID userId, int clickLimit) {
        this.longUrl = longUrl;
        this.userId = userId;
        this.clickLimit = clickLimit;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public AtomicInteger getClickCount() {
        return clickCount;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public boolean isClickLimitReached() {
        return clickCount.get() >= clickLimit;
    }
}
