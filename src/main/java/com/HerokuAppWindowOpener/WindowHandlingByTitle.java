package com.HerokuAppWindowOpener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class WindowHandlingByTitle {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://pl.wikipedia.org/wiki/Wikipedia:Strona_g%C5%82%C3%B3wna");

        String mainWindowHandle = driver.getWindowHandle();
        System.out.println("Main window: " + mainWindowHandle);

        //odnalezienie elementu klikalnego ktory uruchamia nowe okno
        WebElement categoryWiki = driver.findElement(By.linkText("Kategorie Wikipedii"));
        String urlCategory = categoryWiki.getAttribute("href");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open('" + urlCategory + "')");

        String expectedTitle = "Wikipedia, wolna encyklopedia";
        Set<String> allWindows = driver.getWindowHandles();

        for(String windowHandle : allWindows) {
            driver.switchTo().window(windowHandle);
            if(driver.getTitle().equals(expectedTitle)) {
                System.out.println("Title: " + driver.getTitle());
                break;
            }
        }




    }
}
