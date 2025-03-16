package com.HerokuAppDynamicLoading;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.function.Function;

public class SeleniumWaitsDemo {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://the-internet.herokuapp.com/dynamic_loading");

        //demonstrateProblemWithoutWaits(driver);
        //demonstrateImplictWaits(driver);
        //demonstrateExplicitWaits(driver);
        //demonstrateFluentWaits(driver);
        demonstrateExpectedConditions(driver);

        driver.quit();
    }

    private static void demonstrateProblemWithoutWaits(WebDriver driver) {
        System.out.println("Demonstrate problem without waits");

        try {
            //przechodzimy do example 1
            driver.findElement(By.linkText("Example 1: Element on page that is hidden")).click();

            //klikamy przycisk start
            driver.findElement(By.cssSelector("#start > button")).click();

            //Probujemy natychmiast odczytac tekst
            String text = driver.findElement(By.cssSelector("#finish h4")).getText();
            System.out.println("Text: " + text);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());

            driver.navigate().to("https://the-internet.herokuapp.com/dynamic_loading");
        }
    }

    private static void demonstrateImplictWaits(WebDriver driver) {
        System.out.println("Demonstrate implicit waits");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        try {
            //przechodzimy do example 2
            driver.findElement(By.partialLinkText("Example 2: Element rendered after the fact")).click();

            //klikamy przycisk start
            driver.findElement(By.cssSelector("#start > button")).click();

            //Implicit wait powinien zaczekac az element bedzie dostepny w DOM
            //example 2! : jest tworzony dynamicznie
            String text = driver.findElement(By.cssSelector("#finish h4")).getText();
            System.out.println("Text: " + text);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        //wylaczamy implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        driver.navigate().to("https://the-internet.herokuapp.com/dynamic_loading");
    }

    private static void demonstrateExplicitWaits(WebDriver driver) {
        System.out.println("Demonstrate explicit waits");

        // -> example 1
        driver.findElement(By.linkText("Example 1: Element on page that is hidden")).click();

        // -> start
        driver.findElement(By.cssSelector("#start > button")).click();

        //tworzymy obiekt wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //czekamy az element bedzie widoczny - max 10 sekund
        WebElement finish =  wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#finish h4")));

        // -> wyswietl tekst po oczekiwaniu
        System.out.println("Finished Element: " + finish.getText());

        driver.navigate().to("https://the-internet.herokuapp.com/dynamic_loading");

    }

    private static void demonstrateFluentWaits(WebDriver driver) {
        System.out.println("Demonstrate fluent waits");

        //-> example 2
        driver.findElement(By.cssSelector("#content > div > a:nth-child(8)")).click();

        // klik -> start
        driver.findElement(By.cssSelector("#start > button")).click();

        //Tworzenie fluent waita
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        //definiujemy warunek oczekiwania
        WebElement finish = fluentWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                WebElement element = driver.findElement(By.cssSelector("#finish h4"));
                if(element.isDisplayed()){
                    return element;
                } return null;
            }
        });

        // -> tekst na konsole
        System.out.println("tekst: " +  finish.getText());

        driver.navigate().to("https://the-internet.herokuapp.com/dynamic_loading");
    }

    private static void demonstrateExpectedConditions(WebDriver driver) {
        System.out.println("Demonstrate expected conditions");
        // -> strona z alertami
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");
        // utworzenie waita
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //klikamy przycisk js alert
        driver.findElement(By.cssSelector("#content > div > ul > li:nth-child(1) > button")).click();

        // -> wait na alert i accept that
        wait.until(ExpectedConditions.alertIsPresent());
        // switch to alert dialog i accept
        driver.switchTo().alert().accept();

        //check result
        boolean text = wait.until(ExpectedConditions.textToBe(
                By.id("result"),
                "You successfully clicked an alert"
        ));

        System.out.println("text is displayed after accept the alert: " + text);

        //p-> example 2
        driver.navigate().to("https://the-internet.herokuapp.com/dynamic_controls");

        //klik -> remove
        driver.findElement(By.cssSelector("#checkbox-example > button")).click();

        //czekamy az checkocb zniknie
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("checkbox")));

        //czekamy na komunikat its gone
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));

        System.out.println("message is displayed after checkbox: " + message.getText());








    }
}
