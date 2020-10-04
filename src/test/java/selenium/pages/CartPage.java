package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;


public class CartPage extends Page {

    final By ADD_TO_CART_BUTTON = By.cssSelector("[name=\"add_cart_product\"]");
    final By BADGE_QUANTITY = By.cssSelector(".badge.quantity");
    public final By REMOVE_PRODUCT_BUTTON = By.cssSelector("[name=\"remove_cart_item\"]");
    final By LOADER = By.cssSelector("div.loader");

    public CartPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    public void addProductToTheCart() {
        int productQuantity;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        if (driver.findElement(BADGE_QUANTITY).getText().isEmpty()) {
            productQuantity = 0;
        } else {
            productQuantity = Integer.parseInt(driver.findElement(BADGE_QUANTITY).getText());
        }
        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(String.format(
                        "//div[@class='badge quantity' and contains(., '%s')]", productQuantity + 1))));
    }

    public void removeAllProductsFromCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(REMOVE_PRODUCT_BUTTON));
        for (int i = 0; i < products.size(); i++) {
            wait.until(ExpectedConditions.elementToBeClickable(REMOVE_PRODUCT_BUTTON)).click();
            wait.until((ExpectedConditions.invisibilityOfElementLocated(LOADER)));
        }
    }

}

