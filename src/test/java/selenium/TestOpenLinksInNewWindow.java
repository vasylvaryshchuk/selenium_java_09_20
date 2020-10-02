package selenium;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.*;


public class TestOpenLinksInNewWindow {

    final String MAIN_PAGE_URL = "http://35.236.6.102/litecart";
    final String ADMIN_PANEL_URL = MAIN_PAGE_URL + "/admin";
    final String USER = "admin";
    final String PASSWORD = "gl_admin";
    final By USERNAME_INPUT = By.cssSelector("[name='username']");
    final By PASSWORD_INPUT = By.cssSelector("[name='password']");
    final By LOGIN_BUTTON = By.cssSelector("[type='submit']");
    final By COUNTRIES_TAB = By.xpath("//span[contains(., \"Countries\")]");
    final By SPAIN_TAB = By.xpath("//a[contains(., \"Spain\")]");
    final By EDIT_COUNTRY_HEADER = By.xpath("//div[contains(., \"Edit Country\")]");
    final By ARROW_ICON = By.xpath("//i[@class=\"fa fa-external-link\"]/ancestor::a");

    WebDriver browserChrome;

    public void loginToAdminPanel(WebDriver driver, String username, String password) {
        driver.get(ADMIN_PANEL_URL);
        WebElement usernameInput = driver.findElement(USERNAME_INPUT);
        usernameInput.click();
        usernameInput.sendKeys(USER);
        WebElement passwordInput = driver.findElement(PASSWORD_INPUT);
        passwordInput.sendKeys(PASSWORD);
        driver.findElement(LOGIN_BUTTON).click();
    }

    public void switchToNewWindow(WebDriver driver){
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
    }

    @BeforeEach
    void setUp() {
        System.out.println("Browser setup");
        WebDriverManager.chromedriver().setup();
        browserChrome = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        System.out.println("Close browser");
        browserChrome.quit();
    }

    @Test
    void openLinksInNewWindows() {
        loginToAdminPanel(browserChrome, USER, PASSWORD);
        WebDriverWait wait = new WebDriverWait(browserChrome, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("box-apps-menu"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(COUNTRIES_TAB)).click();
        wait.until(ExpectedConditions.elementToBeClickable(SPAIN_TAB)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(EDIT_COUNTRY_HEADER));
        // Store the current window handle
        String parentWindowHandle = browserChrome.getWindowHandle();
        List<WebElement> arrowIcons = browserChrome.findElements(ARROW_ICON);
        for (WebElement element : arrowIcons) {
            String externalUrl = element.getAttribute("href");
            element.click();
            // Switch to new window opened
            switchToNewWindow(browserChrome);
            String currentUrl = browserChrome.getCurrentUrl();
            assertEquals(currentUrl, externalUrl);
            // Close the new window
            browserChrome.close();
            // Switch back to first window
            browserChrome.switchTo().window(parentWindowHandle);

        }
    }
}
