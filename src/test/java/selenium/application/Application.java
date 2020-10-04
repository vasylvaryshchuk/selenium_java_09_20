package selenium.application;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.pages.LitecartMainPage;
import selenium.pages.CartPage;


public class Application {

    public WebDriver driver;
    public WebDriverWait wait;
    public String baseUrl = "http://35.236.6.102/litecart";
    public final LitecartMainPage litecartMainPage;
    public final CartPage cartPage;


    public Application() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5);

        litecartMainPage = new LitecartMainPage(driver, baseUrl);
        cartPage = new CartPage(driver, baseUrl);
    }

    public void quit() {
            driver.quit();
    }

}