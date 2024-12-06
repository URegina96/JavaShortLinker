package org.example;

import java.awt.Desktop;
import java.net.URI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UrlShortLink urlShortLink = new UrlShortLink();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Добро пожаловать в сервис сокращения ссылок!");

        while (true) {
            System.out.println("1. Создать короткую ссылку");
            System.out.println("2. Перейти по короткой ссылке");
            System.out.println("3. Выйти");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Введите длинный URL: ");
                    String longUrl = scanner.next();
                    System.out.print("Введите ваш ID пользователя: ");
                    String userId = scanner.next();
                    System.out.print("Введите лимит переходов: ");
                    int clickLimit = scanner.nextInt();
                    String shortUrl = urlShortLink.shortenUrl(longUrl, userId, clickLimit);
                    System.out.println("Короткая ссылка: " + shortUrl);
                    break;
                case 2:
                    System.out.print("Введите короткую ссылку: ");
                    String inputShortUrl = scanner.next();
                    String originalUrl = urlShortLink.getOriginalUrl(inputShortUrl);
                    if (originalUrl != null) {
                        try {
                            Desktop.getDesktop().browse(new URI(originalUrl));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Ссылка недействительна или лимит переходов исчерпан.");
                    }
                    break;
                case 3:
                    System.out.println("До свидания!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Неверный выбор. Пожалуйста, попробуйте снова.");
            }
        }
    }
}
