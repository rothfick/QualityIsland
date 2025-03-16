package com.SeleniumShop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class LocalizationTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://www.selenium-shop.pl/");

//       WebElement dropDownListFromNavBar =
//                driver.findElement(By.className("navbar-toggle collapsed"));
//
//        dropDownListFromNavBar.click();

//        WebElement ankieta = driver.findElement(By.partialLinkText("ANKIETA"));
//        ankieta.click();

//        WebElement ankietaByID = driver.findElement(By.id("menu-item-134"));
//        ankietaByID.click();

        WebElement shop = driver.findElement(By.partialLinkText("SKLEP"));
        shop.click();

        Thread.sleep(3000);

        Select select1 = new Select(driver.findElement(By.name("orderby")));
        select1.selectByValue("popularity");

        Thread.sleep(3000);

        driver.quit();
    }
}
