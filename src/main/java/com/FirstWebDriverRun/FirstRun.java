package com.FirstWebDriverRun;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstRun {
    public static void main(String[] args) {
        // Konfiguracja sterownika
        WebDriverManager.chromedriver().setup();

        // Inicjalizacja WebDrivera
        WebDriver driver = new ChromeDriver();

        // Maksymalizacja okna przeglądarki
        driver.manage().window().maximize();

        // Otwarcie strony
        driver.get("http://www.selenium-shop.pl");

        // Pobranie tytułu strony
        String title = driver.getTitle();
        System.out.println("Tytuł strony: " + title);

        // Zamknięcie przeglądarki
        driver.quit();
    }
}
