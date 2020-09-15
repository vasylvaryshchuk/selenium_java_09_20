package selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirstTest {

    final String GOOGLE_URL = "https://www.google.com";
    private WebDriver browserChrome;
    private WebDriver browserFirefox;

    @BeforeEach
    void setUp() {
        System.out.println("Browser setup");
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        browserChrome = new ChromeDriver();
        browserFirefox = new FirefoxDriver();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Close browser");
        browserChrome.quit();
        browserFirefox.quit();
    }

    @Test
    void testDifferentBrowsers() {
        browserChrome.get(GOOGLE_URL);
        browserFirefox.get(GOOGLE_URL);
    }
}
