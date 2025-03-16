package com.SeleniumShop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class FindMultipleElements {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://www.selenium-shop.pl/");

        Thread.sleep(3000);

        WebElement shop = driver.findElement(By.linkText("SKLEP"));
        shop.click();
        Thread.sleep(3000);

        List<WebElement> koszulki = driver.findElements(By.partialLinkText("KOSZULKA"));
        for (WebElement koszulka : koszulki) {
            System.out.println(koszulka.getText());
        }
        System.out.println("Znaleziono koszulek: " +  koszulki.size());

        List<WebElement> pilki = driver.findElements(By.partialLinkText("PIŁKA"));
        System.out.println("Znaleziono pilki: " +  pilki.size());

        for (WebElement pilkiElement : pilki) {
            System.out.println(pilkiElement.getText());
        }

        driver.quit();
    }
}
