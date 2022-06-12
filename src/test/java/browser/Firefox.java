package browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Firefox implements IBrowser {

    @Override
    public WebDriver getDriver() {
        String driverPath = getDriverPathByOS("geckdriver");
        System.setProperty("webdriver.firefox.driver", driverPath);

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("start-maximized");

        return new FirefoxDriver(firefoxOptions);
    }
}
