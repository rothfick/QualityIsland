package com.XpathCSSDemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class XpathCssDemo {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(13));
    }

    @Test
    public void testWikipediaSelectors() {
        driver.get("https://www.wikipedia.org");

        //XPath examples
        WebElement searchInput = driver.findElement(
                By.xpath("//input[@id='searchInput']"));
        Assert.assertTrue(searchInput.isDisplayed(),
                "Wikipedia search input should be visible");

        List<WebElement> languageLinks = driver.findElements(
                By.xpath("//div[contains(@class,'central-featured')]//a"));
        Assert.assertEquals(languageLinks.size(), 10, "Should have exactly 10 links");
        System.out.println("Wikipedia search results:" + languageLinks.size());
        //CSS Selector Examples
        WebElement logo = driver.findElement(
                By.cssSelector("#www-wikipedia-org > main > div.central-textlogo > img"));
        WebElement logoAlternative = driver.findElement(
                By.cssSelector(".central-featured-logo"));

        List<WebElement> otherProjects = driver.findElements(
                By.cssSelector(".other-projects a"));
        Assert.assertTrue(otherProjects.size() >= 12,
                "Should have at least 12 other Wikimedia projects");
        System.out.println("Wikipedia search results:" + otherProjects.size());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
