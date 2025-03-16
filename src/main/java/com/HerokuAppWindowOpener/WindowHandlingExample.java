package com.HerokuAppWindowOpener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class WindowHandlingExample {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://the-internet.herokuapp.com/windows");

        String windowHandle = driver.getWindowHandle();
        System.out.println("Main window: " + windowHandle);

        WebElement link = driver.findElement(By.linkText("Click Here"));
        link.click();

        driver.switchTo().window(windowHandle);

        link.click();

        driver.switchTo().window(windowHandle);

        link.click();
        driver.switchTo().window(windowHandle);

        link.click();
        driver.switchTo().window(windowHandle);

        link.click();
        Set<String> allWindowHandles = driver.getWindowHandles();
        System.out.println("All window handles: " + allWindowHandles.size());

        for (String winHandle : allWindowHandles) {
            if (!winHandle.equals(windowHandle)) {
                driver.switchTo().window(winHandle);
                System.out.println("Current window: " + winHandle);
                System.out.println("Title of current window: " + driver.getTitle());
                //ZAWSZE ZAMYKAJ OKNO -> driver.close()
//                driver.close();
            }
        }
    }
}
