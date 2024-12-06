package org.example;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UrlShortLink {
    private final Map<String, UrlData> urlMap = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();
    private final long linkLifetime = TimeUnit.MINUTES.toMillis(5);

    public String shortenUrl(String longUrl, String userId) {
        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlMap.containsKey(shortUrl));

        urlMap.put(shortUrl, new UrlData(longUrl, userId));
        return shortUrl;
    }

    private String generateShortUrl() {
        byte[] bytes = new byte[9];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public void cleanupExpiredUrls() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, UrlData>> iterator = urlMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, UrlData> entry = iterator.next();
            if ((currentTime - entry.getValue().getCreationTime()) > linkLifetime) {
                iterator.remove();
            }
        }
    }
}


