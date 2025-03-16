package com.HerokuAppWindowOpener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class NWindowExample {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://the-internet.herokuapp.com/windows");

        String mainWindowHandle = driver.getWindowHandle();
        System.out.println("Main window: " + mainWindowHandle);

        //odnalezienie elementu klikalnego ktory uruchamia nowe okno
        WebElement clickHere = driver.findElement(By.linkText("Click Here"));
        clickHere.click();

        driver.switchTo().window(mainWindowHandle);

        clickHere.click();

        driver.switchTo().window(mainWindowHandle);

        Set<String> allWindowHandles = driver.getWindowHandles();
        String[] windows = allWindowHandles.toArray(new String[0]);

        if(windows.length > 2){
            driver.switchTo().window(windows[1]);
            System.out.println("Drugie okno");
            driver.switchTo().window(windows[2]);
            System.out.println("Trzecie okno");
            driver.switchTo().window(windows[0]);
            System.out.println("Pierwsze okno");
        }

        driver.quit();
    }
}
