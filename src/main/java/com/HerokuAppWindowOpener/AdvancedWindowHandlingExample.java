package com.HerokuAppWindowOpener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AdvancedWindowHandlingExample {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://the-internet.herokuapp.com/windows");

        //pobranie uchwytu dla pierwszego (glownego okna)
        String mainWindowHandle = driver.getWindowHandle();
        System.out.println("Main window: " + mainWindowHandle);

        //odnalezienie elementu klikalnego ktory uruchamia nowe okno
        WebElement clickHere = driver.findElement(By.linkText("Click Here"));
        clickHere.click();

        //pobranie uchwytu dla drugiego okna
        String secondWindow = getNewWindowHandle(driver, mainWindowHandle);

        //switcher na main okno
        driver.switchTo().window(mainWindowHandle);

        //uruchomienie trzeciego okna
        clickHere.click();

        //pobranie uchwytu dla trzeciego okna
        String thirdWindow = getNewWindowHandle(driver, new String[]{mainWindowHandle, secondWindow});

        //przelacz na 1. okno
        driver.switchTo().window(mainWindowHandle);

        //przelacz na 3. okno
        driver.switchTo().window(thirdWindow);

        //przelacz na 2. okno
        driver.switchTo().window(secondWindow);

        //przelacz na 3. okno
        driver.switchTo().window(thirdWindow);
    }

    //metoda pomocnicza do znajdowania okna
    private static String getNewWindowHandle(WebDriver driver, String currentWindow) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows){
            if(!window.equals(currentWindow)){
                return window;
            }
        }
        return null;
    }

    //Przeciazona metoda dla wielu istniejacych okien
    private static String getNewWindowHandle(WebDriver driver, String[] existingWindows) {
        Set<String> allWindows = driver.getWindowHandles();
        Set<String> exsistWindows = new HashSet<String>(Arrays.asList(existingWindows));

        for(String window : allWindows){
            if(!exsistWindows.contains(window)){
                return window;
            }
        }
        return null;
    }
}
