package com.SeleniumDev;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TextInputExample {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");


        WebElement input1 = driver.findElement(By.name("my-text"));

        input1.sendKeys("W Quality Island mamy kursantow debesciakow, top of top");

        Thread.sleep(2000);

        input1.clear();

        Thread.sleep(2000);

        input1.sendKeys("Selenium is the best tool for web automation");

        Thread.sleep(2000);

        driver.quit();
    }
}
