package browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleChrome implements IBrowser {
    @Override
    public WebDriver getDriver() {
        String driverPath = getDriverPathByOS("chromedriver13");
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.addArguments("start-maximized");

        return new ChromeDriver(chromeOpts);
    }
}
