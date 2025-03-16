package com.SeleniumDev;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CheckBoxRadioExample {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement checkbox = driver.findElement(By.id("my-check-1"));

        System.out.println("1. Checkbox zaznaczony przed kliknieciem: " + checkbox.isSelected());

        checkbox.click();

        System.out.println("2. Checkbox zaznaczony przed kliknieciem: " + checkbox.isSelected());

        if(!checkbox.isSelected()){
            checkbox.click();
        }

        System.out.println("3. Checkbox zaznaczony przed kliknieciem: " + checkbox.isSelected());

        WebElement radio = driver.findElement(By.id("my-radio-1"));

        radio.click();

        System.out.println("Radio button1 zaznaczony: " + radio.isSelected());

        WebElement radio2 = driver.findElement(By.id("my-radio-2"));
        System.out.println("Radio button2 zaznaczony: " + radio2.isSelected());

        radio2.click();

        System.out.println("Radio button1 zaznaczony: " + radio.isSelected());
        System.out.println("Radio button2 zaznaczony: " + radio2.isSelected());

        driver.quit();
    }
}
