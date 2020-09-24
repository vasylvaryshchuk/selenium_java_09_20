package selenium;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;



public class TestAdminPanelHeader {

    final String MAIN_PAGE_URL = "http://35.236.6.102/litecart";
    final String ADMIN_PANEL_URL = MAIN_PAGE_URL + "/admin";
    final String USER = "admin";
    final String PASSWORD = "gl_admin";
    final By USERNAME_INPUT = By.cssSelector("[name='username']");
    final By PASSWORD_INPUT = By.cssSelector("[name='password']");
    final By LOGIN_BUTTON = By.cssSelector("[type='submit']");
    final By MENU_ITEM = By.cssSelector("li.app");
    final By SUB_MENU_ITEM = By.cssSelector("li.doc");
    final By PANEL_HEADING = By.className("panel-heading");
    final By PRODUCT_NAME_MAIN_PAGE = By.cssSelector("#box-campaign-products .name");
    final By REGULAR_PRICE_MAIN_PAGE = By.cssSelector("#box-campaign-products .price-wrapper del");
    final By CAMPAIGN_PRICE_MAIN_PAGE = By.cssSelector("#box-campaign-products .price-wrapper strong");
    final By PRODUCT_NAME_ITEM_PAGE = By.cssSelector("#box-product h1");
    final By REGULAR_PRICE_ITEM_PAGE = By.cssSelector(".regular-price");
    final By CAMPAIGN_PRICE_ITEM_PAGE = By.cssSelector(".campaign-price");
    final String RED_COLOR = "#cc0000";
    final String GREY_COLOR = "#333333";
    final String BOLD_FONT_WEIGHT = "700";
    final String TEXT_DECORATION_LINE = "line-through";
    private WebDriver browser;


    @AfterEach
    void tearDown() {
        browser.quit();
        System.out.println("Close browser");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Chrome", "Firefox"})
    void testAdminPanelHeaderPresence(String testBrowser) {
        if (testBrowser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            browser = new ChromeDriver();
        } else if(testBrowser.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            browser = new FirefoxDriver();;
        }
        browser.get(ADMIN_PANEL_URL);
        WebElement usernameInput = browser.findElement(USERNAME_INPUT);
        usernameInput.click();
        usernameInput.sendKeys(USER);
        WebElement passwordInput = browser.findElement(PASSWORD_INPUT);
        passwordInput.sendKeys(PASSWORD);
        browser.findElement(LOGIN_BUTTON).click();

        WebDriverWait wait = new WebDriverWait(browser,5);
        wait.until(ExpectedConditions.elementToBeClickable(MENU_ITEM));
        List<WebElement> menuItems = browser.findElements(MENU_ITEM);

        ArrayList<String> menuTitles = new ArrayList<>();
        for (WebElement menuItem : menuItems) {
            menuTitles.add(menuItem.getText());
        }

        for (String title : menuTitles) {
            By menuItemLocator = By.xpath(String.format("//span[contains(., \"%s\")]", title));
            WebElement menuItem = browser.findElement(menuItemLocator);
            menuItem.click();
            System.out.println("Click: " + title);
            assertTrue(browser.findElement(PANEL_HEADING).isDisplayed());

            List<WebElement> subMenuItems = browser.findElements(SUB_MENU_ITEM);

            if (subMenuItems.size() > 0) {
                ArrayList<String> subMenuTitles = new ArrayList<>();
                for (WebElement subMenuItem : subMenuItems) {
                    subMenuTitles.add(subMenuItem.getText());
                }

                for (String subTitle : subMenuTitles) {
                    System.out.println("  ---  " + subTitle);
                    WebElement menuItemNode = browser.findElement(menuItemLocator);
                    By subMenuItemLocator = By.xpath(String.format("//span[contains(., \"%s\")]", subTitle));
                    menuItemNode.findElement(subMenuItemLocator).click();
                    assert (browser.findElement(PANEL_HEADING).isDisplayed());

                }
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Chrome", "Firefox"})
    void TestProductPropertiesDisplayed(String testBrowser) {
        if (testBrowser.equals("Chrome")) {
            WebDriverManager.chromedriver().setup();
            browser = new ChromeDriver();
        } else if(testBrowser.equals("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            browser = new FirefoxDriver();;
        }
        browser.get(MAIN_PAGE_URL);
        WebElement productNameMain = browser.findElement(PRODUCT_NAME_MAIN_PAGE);
        WebElement regularPriceMain = browser.findElement(REGULAR_PRICE_MAIN_PAGE);
        WebElement campaignPriceMain = browser.findElement(CAMPAIGN_PRICE_MAIN_PAGE);

        String campaignPriceFontWeightMain = campaignPriceMain.getCssValue("font-weight");
        String campaignPriceColorMain = Color.fromString(campaignPriceMain.getCssValue("color")).asHex();
        String regularPriceColorMain = Color.fromString(regularPriceMain.getCssValue("color")).asHex();
        String regularPriceDecorationMain = regularPriceMain.getCssValue("text-decoration-line");

        assertEquals(campaignPriceFontWeightMain, BOLD_FONT_WEIGHT);
        assertEquals(regularPriceDecorationMain, TEXT_DECORATION_LINE);
        assertEquals(campaignPriceColorMain, RED_COLOR);
        assertEquals(regularPriceColorMain, GREY_COLOR);

        productNameMain.click();

        WebElement productNameItem = browser.findElement(PRODUCT_NAME_ITEM_PAGE);
        WebElement regularPriceItem = browser.findElement(REGULAR_PRICE_ITEM_PAGE);
        WebElement campaignPriceItem = browser.findElement(CAMPAIGN_PRICE_ITEM_PAGE);

        String campaignPriceFontWeightItem = campaignPriceItem.getCssValue("font-weight");
        String campaignPriceColorItem = Color.fromString(campaignPriceItem.getCssValue("color")).asHex();
        String regularPriceColorItem = Color.fromString(regularPriceItem.getCssValue("color")).asHex();
        String regularPriceDecorationItem = regularPriceItem.getCssValue("text-decoration-line");

        assertEquals(campaignPriceFontWeightMain, campaignPriceFontWeightItem);
        assertEquals(regularPriceDecorationMain, regularPriceDecorationItem);
        assertEquals(campaignPriceColorMain, campaignPriceColorItem);
        assertEquals(regularPriceColorMain, regularPriceColorItem);

    }
}
