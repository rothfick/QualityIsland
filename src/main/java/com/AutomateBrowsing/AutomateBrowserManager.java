package com.AutomateBrowsing;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class AutomateBrowserManager {
    public static void main(String[] args) {
        // Konfiguracja Chrome
        WebDriverManager.chromedriver().setup();
        WebDriver chromeDriver = new ChromeDriver();

        // Konfiguracja Firefox
        //WebDriverManager.firefoxdriver().setup();
        //WebDriver firefoxDriver = new FirefoxDriver();

        // Konfiguracja Edge
        //WebDriverManager.edgedriver().setup();
        //WebDriver edgeDriver = new EdgeDriver();

        //Proste przejscie do strony w przegladrce
        chromeDriver.get("https://www.google.com");

        // Zamknięcie przeglądarek
        chromeDriver.quit();
        //firefoxDriver.quit();
        //edgeDriver.quit();
    }
}
