package selenium;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestAddRemoveFromCart {

    final String MAIN_PAGE_URL = "http://35.236.6.102/litecart";
    final By POPULAR_PRODUCTS = By.cssSelector("#box-popular-products a");
    final By ADD_TO_CART_BUTTON = By.cssSelector("[name=\"add_cart_product\"]");
    final By BADGE_QUANTITY = By.cssSelector(".badge.quantity");
    final By CART = By.id("cart");
    final By REMOVE_PRODUCT_BUTTON = By.cssSelector("[name=\"remove_cart_item\"]");
    final By LOADER = By.cssSelector("div.loader");

    private WebDriver browserChrome;

    private void goToMainPage(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(MAIN_PAGE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.logotype")));
    }

    private void openTheCart(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(CART)).click();
        wait.until((ExpectedConditions.invisibilityOfElementLocated(LOADER)));
    }

    private void addProductToTheCart(WebDriver driver) {
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

    private void removeAllProductsFromCart(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<WebElement> products = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(REMOVE_PRODUCT_BUTTON));
        for (int i = 0; i < products.size(); i++) {
            wait.until(ExpectedConditions.elementToBeClickable(REMOVE_PRODUCT_BUTTON)).click();
            wait.until((ExpectedConditions.invisibilityOfElementLocated(LOADER)));
        }
    }

    private WebElement choseRandomElement(List<WebElement> elements) {
        java.util.Random random = new java.util.Random();
        int randomItem = random.nextInt(elements.size());
        return elements.get(randomItem);
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
    void testAddRemoveFromTheCart() {
        WebDriverWait wait = new WebDriverWait(browserChrome, 10);
        // Add three products to the cart
        for (int i = 0; i < 3; i++) {
            goToMainPage(browserChrome);
            wait.until(ExpectedConditions.elementToBeClickable(POPULAR_PRODUCTS));
            List<WebElement> popularProducts = browserChrome.findElements(POPULAR_PRODUCTS);
            choseRandomElement(popularProducts).click();
            addProductToTheCart(browserChrome);
        }
        openTheCart(browserChrome);
        // Remove all products from the cart
        removeAllProductsFromCart(browserChrome);
        assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(REMOVE_PRODUCT_BUTTON)));
    }

}
