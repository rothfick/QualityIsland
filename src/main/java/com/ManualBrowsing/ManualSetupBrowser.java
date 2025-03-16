package com.ManualBrowsing;

import com.sun.jdi.event.ThreadDeathEvent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Sleeper;

public class ManualSetupBrowser {
    public static void main(String[] args) throws InterruptedException {

        //Konfiguracja sterownika chromeDriver
        System.setProperty("webdriver.chrome.driver",
                "/Users/robsonic/Documents/Selenium/SeleniumTraining/src/main/resources/chromedriver");

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get("https://www.google.com");

        Thread.sleep(3000);

        driver.quit();

    }
}
