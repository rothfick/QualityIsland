package com.SeleniumDev;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DropdownExample {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement dropdown = driver.findElement(By.name("my-select"));

        Select selectDropdown = new Select(dropdown);

        selectDropdown.selectByIndex(0);

        selectDropdown.selectByValue("2");

        selectDropdown.selectByVisibleText("Three");

        WebElement selectedOption = selectDropdown.getFirstSelectedOption();
        System.out.println(selectedOption.getText());

        List<WebElement> allOptions = selectDropdown.getOptions();
        System.out.println("Wszystkie opcje:");
        for (WebElement option : allOptions) {
            System.out.println("= " + option.getText());
        }

        driver.quit();
    }
}
