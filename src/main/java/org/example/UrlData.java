package org.example;

import java.util.concurrent.atomic.AtomicInteger;

class UrlData {
    private final String longUrl;
    private final String userId;
    private final AtomicInteger clickCount = new AtomicInteger(0);
    private final long creationTime = System.currentTimeMillis();

    UrlData(String longUrl, String userId) {
        this.longUrl = longUrl;
        this.userId = userId;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getUserId() {
        return userId;
    }

    public AtomicInteger getClickCount() {
        return clickCount;
    }

    public long getCreationTime() {
        return creationTime;
    }
}

