package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;


public class LitecartMainPage extends Page {

    final By POPULAR_PRODUCTS = By.cssSelector("#box-popular-products a");
    final By CART = By.id("cart");
    final By LOADER = By.cssSelector("div.loader");

    public LitecartMainPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public List<WebElement> getPopularProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(POPULAR_PRODUCTS));
        List<WebElement> popularProducts = driver.findElements(POPULAR_PRODUCTS);
        return popularProducts;
    }

    public WebElement choseRandomElement(List<WebElement> elements) {
        java.util.Random random = new java.util.Random();
        int randomItem = random.nextInt(elements.size());
        return elements.get(randomItem);
    }

    public void openTheCart() {
        wait.until(ExpectedConditions.elementToBeClickable(CART)).click();
        wait.until((ExpectedConditions.invisibilityOfElementLocated(LOADER)));
    }

}
