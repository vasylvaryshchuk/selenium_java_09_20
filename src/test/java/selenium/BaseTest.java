package selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import selenium.application.Application;

public class BaseTest {

    public static Application app;

    @BeforeAll
    public static void startDriver() {
        app = new Application();
    }

    @AfterAll
    public static void stopDriver() {
        app.quit();
    }

}
