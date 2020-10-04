package selenium;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.WebElement;
import java.util.List;


public class TestAddRemoveFromCartLayered extends BaseTest {

    @Test
    public void addRemoveFromCart() {
        // Add three products to the cart
        for (int i = 0; i < 3; i++) {
            app.litecartMainPage.goToMainPage();
            List<WebElement> popularProducts = app.litecartMainPage.getPopularProducts();
            app.litecartMainPage.choseRandomElement(popularProducts).click();
            app.cartPage.addProductToTheCart();
        }
        app.litecartMainPage.openTheCart();
        // Remove all products from the cart
        app.cartPage.removeAllProductsFromCart();
        assertTrue(app.cartPage.isElementInvisible(app.cartPage.REMOVE_PRODUCT_BUTTON));
    }

}
