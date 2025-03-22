package com.AlertsAndCookiesExercise;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

public class AlertsAndCookiesTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        // Automatyczna konfiguracja ChromeDriver
        WebDriverManager.chromedriver().setup();

        // Inicjalizacja WebDrivera z opcjami
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        // Inicjalizacja WebDrivera
        driver = new ChromeDriver(options);

        // Inicjalizacja explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void testJavaScriptAlerts() {
        // 1. Nawiguj do strony z alertami JavaScript
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        // 2. Kliknij przycisk "Click for JS Alert" i obsłuż alert
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        // Oczekaj na pojawienie się alertu
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Pobierz tekst alertu i zaakceptuj
        String alertText = alert.getText();
        System.out.println("Tekst alertu: " + alertText);
        alert.accept();

        // Zweryfikuj komunikat potwierdzający
        String resultText = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(resultText, "You successfully clicked an alert", "Komunikat potwierdzający jest nieprawidłowy");
        System.out.println("Test alertu JS - zaliczony");

        // 3. Kliknij przycisk "Click for JS Confirm" i zaakceptuj alert
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        // Oczekaj na pojawienie się alertu
        alert = wait.until(ExpectedConditions.alertIsPresent());

        // Pobierz tekst alertu i zaakceptuj
        alertText = alert.getText();
        System.out.println("Tekst alertu potwierdzającego: " + alertText);
        alert.accept();

        // Zweryfikuj komunikat potwierdzający
        resultText = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(resultText, "You clicked: Ok", "Komunikat potwierdzający jest nieprawidłowy");
        System.out.println("Test alertu Confirm - zaliczony");

        // 4. Kliknij przycisk "Click for JS Prompt", wprowadź tekst i zaakceptuj
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();

        // Oczekaj na pojawienie się alertu
        alert = wait.until(ExpectedConditions.alertIsPresent());

        // Pobierz tekst alertu, wprowadź własny tekst i zaakceptuj
        alertText = alert.getText();
        System.out.println("Tekst alertu z promptem: " + alertText);

        String inputText = "Test Selenium " + new Date().getTime();
        alert.sendKeys(inputText);
        alert.accept();

        // Zweryfikuj komunikat potwierdzający zawierający wprowadzony tekst
        resultText = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(resultText, "You entered: " + inputText, "Komunikat potwierdzający z wprowadzonym tekstem jest nieprawidłowy");
        System.out.println("Test alertu Prompt - zaliczony");
    }

    @Test
    public void testCookieManagement() {
        try {
            // 1. Nawiguj do strony, która obsługuje cookies
            driver.get("https://automationexercise.com/");

            // Poczekaj na załadowanie strony
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/']")));
            System.out.println("Strona została załadowana");

            // 2. Utwórz nowe cookie z czasem wygaśnięcia (za 1 dzień)
            String cookieName = "seleniumTestCookie";
            String cookieValue = "value" + System.currentTimeMillis();

            // Utwórz datę wygaśnięcia za 1 dzień
            Date expiryDate = new Date();
            expiryDate.setTime(expiryDate.getTime() + (1000 * 60 * 60 * 24));

            // 3. Dodaj cookie do przeglądarki
            Cookie cookie = new Cookie.Builder(cookieName, cookieValue)
                    .path("/")
                    .expiresOn(expiryDate)
                    .build();

            driver.manage().addCookie(cookie);
            System.out.println("Dodano cookie: " + cookieName + " = " + cookieValue);

            // 4. Odśwież stronę, aby upewnić się, że cookie zostało zastosowane
            driver.navigate().refresh();

            // 5. Odczytaj wartość cookie i zweryfikuj
            Cookie retrievedCookie = driver.manage().getCookieNamed(cookieName);
            Assert.assertNotNull(retrievedCookie, "Cookie nie zostało utworzone");
            Assert.assertEquals(retrievedCookie.getValue(), cookieValue, "Wartość cookie jest nieprawidłowa");
            System.out.println("Cookie zostało poprawnie odczytane: " + retrievedCookie.getName() + " = " + retrievedCookie.getValue());

            // Wyświetl wszystkie cookie
            Set<Cookie> cookies = driver.manage().getCookies();
            System.out.println("Liczba cookies: " + cookies.size());

            // 6. Usuń nasze cookie
            driver.manage().deleteCookie(retrievedCookie);
            System.out.println("Usunięto cookie: " + cookieName);

            // Zweryfikuj, że cookie zostało usunięte
            Cookie deletedCookie = driver.manage().getCookieNamed(cookieName);
            Assert.assertNull(deletedCookie, "Cookie nie zostało usunięte");
            System.out.println("Test usunięcia cookie - zaliczony");

        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas testu cookie: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test zakończony niepowodzeniem");
        }
    }

    @Test
    public void testWindowHandling() {
        try {
            // 1. Nawiguj do strony, która otwiera nowe okno
            driver.get("https://the-internet.herokuapp.com/windows");

            // Zapisz uchwyt do oryginalnego okna
            String originalWindowHandle = driver.getWindowHandle();
            System.out.println("Uchwyt oryginalnego okna: " + originalWindowHandle);

            // 2. Kliknij na link, który otwiera nowe okno
            WebElement clickHereLink = driver.findElement(By.linkText("Click Here"));
            clickHereLink.click();

            // 3. Poczekaj na otwarcie nowego okna
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));
            System.out.println("Nowe okno zostało otwarte");

            // 4. Przełącz się do nowego okna
            Set<String> windowHandles = driver.getWindowHandles();
            String newWindowHandle = null;

            for (String handle : windowHandles) {
                if (!handle.equals(originalWindowHandle)) {
                    newWindowHandle = handle;
                    break;
                }
            }

            // Przełącz się do nowego okna
            driver.switchTo().window(newWindowHandle);
            System.out.println("Przełączono do nowego okna: " + newWindowHandle);

            // 5. Sprawdź zawartość nowego okna
            WebElement newWindowText = driver.findElement(By.tagName("h3"));
            Assert.assertEquals(newWindowText.getText(), "New Window", "Tekst w nowym oknie jest nieprawidłowy");
            System.out.println("Tekst w nowym oknie: " + newWindowText.getText());

            // 6. Przełącz się z powrotem do oryginalnego okna
            driver.switchTo().window(originalWindowHandle);
            System.out.println("Przełączono z powrotem do oryginalnego okna");

            // 7. Sprawdź zawartość oryginalnego okna
            WebElement originalWindowText = driver.findElement(By.tagName("h3"));
            Assert.assertEquals(originalWindowText.getText(), "Opening a new window", "Tekst w oryginalnym oknie jest nieprawidłowy");
            System.out.println("Tekst w oryginalnym oknie: " + originalWindowText.getText());

            System.out.println("Test obsługi wielu okien - zaliczony");

        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas testu window handling: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test zakończony niepowodzeniem");
        }
    }

    @Test
    public void testAlertWithTimeout() {
        try {
            // 1. Nawiguj do strony z alertem z opóźnieniem
            driver.get("https://demoqa.com/alerts");

            // 2. Poczekaj na załadowanie strony
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("timerAlertButton")));
            System.out.println("Strona z alertami została załadowana");

            // 3. Kliknij przycisk, który wywoła alert po 5 sekundach
            WebElement timerAlertButton = driver.findElement(By.id("timerAlertButton"));
            timerAlertButton.click();
            System.out.println("Kliknięto przycisk wywołujący alert z opóźnieniem");

            // 4. Oczekuj na pojawienie się alertu (z dłuższym timeoutem)
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(6));
            Alert alert = longWait.until(ExpectedConditions.alertIsPresent());
            System.out.println("Alert z opóźnieniem pojawił się");

            // 5. Pobierz tekst alertu i zaakceptuj
            String alertText = alert.getText();
            System.out.println("Tekst alertu z opóźnieniem: " + alertText);
            alert.accept();
            System.out.println("Alert został zaakceptowany");

            System.out.println("Test alertu z opóźnieniem - zaliczony");

        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas testu alertu z opóźnieniem: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test zakończony niepowodzeniem");
        }
    }

    @Test
    public void testLoginAndSessionCookies() {
        try {
            // 1. Nawiguj do strony logowania
            driver.get("https://practicetestautomation.com/practice-test-login/");

            // 2. Poczekaj na załadowanie formularza logowania
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            System.out.println("Strona logowania została załadowana");

            // 3. Wprowadź dane logowania
            driver.findElement(By.id("username")).sendKeys("student");
            driver.findElement(By.id("password")).sendKeys("Password123");
            driver.findElement(By.id("submit")).click();

            // 4. Poczekaj na zalogowanie i przekierowanie
            wait.until(ExpectedConditions.urlContains("logged-in-successfully"));
            System.out.println("Zalogowano pomyślnie");

            // 5. Sprawdź, czy jesteś zalogowany
            WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Log out")));
            Assert.assertTrue(logoutButton.isDisplayed(), "Przycisk wylogowania nie jest widoczny");

            // 6. Pobierz i wyświetl cookies sesji
            Set<Cookie> sessionCookies = driver.manage().getCookies();
            System.out.println("Cookies po zalogowaniu (" + sessionCookies.size() + "):");
            for (Cookie c : sessionCookies) {
                System.out.println(c.getName() + ": " + c.getValue() + " (wygasa: " + c.getExpiry() + ")");
            }

            // 7. Zapisz najważniejsze cookie sesji (jeśli istnieje)
            // W tym przypadku używamy WordPress cookie, które zazwyczaj zawiera "wordpress" w nazwie
            Cookie sessionCookie = null;
            for (Cookie c : sessionCookies) {
                if (c.getName().toLowerCase().contains("wordpress")) {
                    sessionCookie = c;
                    System.out.println("Znaleziono cookie sesji WordPress: " + c.getName());
                    break;
                }
            }

            // 8. Wyloguj się
            logoutButton.click();

            // 9. Poczekaj na powrót do strony logowania
            wait.until(ExpectedConditions.urlContains("practice-test-login"));
            System.out.println("Wylogowano pomyślnie");

            // 10. Sprawdź cookies po wylogowaniu
            Set<Cookie> afterLogoutCookies = driver.manage().getCookies();
            System.out.println("Cookies po wylogowaniu (" + afterLogoutCookies.size() + "):");
            for (Cookie c : afterLogoutCookies) {
                System.out.println(c.getName() + ": " + c.getValue());
            }

            // Test uznajemy za zaliczony, gdy udało się zalogować i wylogować
            System.out.println("Test logowania i cookies sesji - zaliczony");

        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas testu logowania i cookies: " + e.getMessage());
            e.printStackTrace();
            Assert.fail("Test zakończony niepowodzeniem");
        }
    }

    @AfterMethod
    public void tearDown() {
        // Zamknięcie przeglądarki
        if (driver != null) {
            driver.quit();
            System.out.println("Przeglądarka została zamknięta");
        }
    }
}
