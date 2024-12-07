package org.example;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class UrlShortLink {
    private final Map<String, UrlData> urlMap = new ConcurrentHashMap<>();
    private final SecureRandom random = new SecureRandom();
    private final long linkLifetime = TimeUnit.MINUTES.toMillis(5);

    public String shortenUrl(String longUrl, UUID userId, int clickLimit) {
        String shortUrl;
        do {
            shortUrl = generateShortUrl();
        } while (urlMap.containsKey(shortUrl));

        urlMap.put(shortUrl, new UrlData(longUrl, userId, clickLimit));
        return shortUrl;
    }

    private String generateShortUrl() {
        byte[] bytes = new byte[9];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public String getOriginalUrl(String shortUrl) {
        UrlData urlData = urlMap.get(shortUrl);
        if (urlData != null) {
            if (!urlData.isClickLimitReached()) {
                urlData.getClickCount().incrementAndGet();
                return urlData.getLongUrl();
            } else {
                System.out.println("Лимит переходов по ссылке достигнут.");
            }
        }
        return null;
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
