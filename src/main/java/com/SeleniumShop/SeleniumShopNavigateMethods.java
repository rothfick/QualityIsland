package com.SeleniumShop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumShopNavigateMethods {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver chromeDriver = new ChromeDriver();

        chromeDriver.get("http://www.selenium-shop.pl");

        Thread.sleep(3000);
        chromeDriver.navigate().to("https://www.wikipedia.org");

        Thread.sleep(3000);

        chromeDriver.navigate().back();

        Thread.sleep(3000);

        chromeDriver.navigate().forward();

        Thread.sleep(3000);

        chromeDriver.navigate().refresh();
        Thread.sleep(3000);
        chromeDriver.quit();


    }
}
