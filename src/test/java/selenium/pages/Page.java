package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Page {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl;

    public Page(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        wait = new WebDriverWait(driver, 10);
    }

    public void goToMainPage() {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.logotype")));
    }

    public boolean isElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

}
