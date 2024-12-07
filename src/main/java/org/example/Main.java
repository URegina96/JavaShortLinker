package org.example;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        UrlShortLink urlShortLink = new UrlShortLink();

        System.out.println("Добро пожаловать в сервис сокращения ссылок!");

        System.out.println("1. Создание короткой ссылки");
        String shortUrl1 = urlShortLink.shortenUrl("https://example.com", "user1", 5);
        System.out.println("Short URL 1: " + shortUrl1);

        System.out.println("2. Переход по короткой ссылке");
        String originalUrl1 = urlShortLink.getOriginalUrl(shortUrl1);
        System.out.println("Original URL 1: " + originalUrl1);

        System.out.println("3. Ограничение по количеству переходов");
        for (int i = 0; i < 6; i++) {
            String result = urlShortLink.getOriginalUrl(shortUrl1);
            if (result == null) {
                System.out.println("Short URL 1 is no longer available.");
                break;
            }
        }

        System.out.println("4. Удаление ссылок по истечении времени жизни");
        Thread.sleep(TimeUnit.MINUTES.toMillis(6));
        urlShortLink.cleanupExpiredUrls();
        String expiredUrl = urlShortLink.getOriginalUrl(shortUrl1);
        if (expiredUrl == null) {
            System.out.println("Short URL 1 has been expired and removed.");
        }
    }
}
