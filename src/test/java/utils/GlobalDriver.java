package utils;

import browser.Browser;
import org.openqa.selenium.WebDriver;

public class GlobalDriver {

    private static WebDriver driver = null;

    public static void set() {
        if (driver == null) {
            driver = Browser.getWebDriver();
        }
    }

    public static WebDriver get() {
        return driver;
    }

    public static void close() {
        if(driver != null){
            driver.close();
            driver.quit();
            driver = null;
        }
    }
}
