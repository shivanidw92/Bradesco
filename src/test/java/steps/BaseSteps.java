package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import report.Screenshot;
import utils.GlobalDriver;

public class BaseSteps {

    protected static WebDriver driver;

    @Before
    public static void setup(Scenario scenario){
        GlobalDriver.set();
        driver = GlobalDriver.get();
    }

    @After
    public static void finish(Scenario scenario) {
        scenario.embed(Screenshot.getByteScreenshot(driver), "image/png");
//        scenario.attach(Screenshot.getByteScreenshot(driver), "image/png", scenario.getName());
        GlobalDriver.close();
    }

    @AfterStep
    public static void afterStep(Scenario scenario) {
        scenario.embed(Screenshot.getByteScreenshot(driver), "image/png");
//        scenario.attach(Screenshot.getByteScreenshot(driver), "image/png", "");
    }

    public static void openBrowser(String url){
        driver.manage().window().maximize();
        driver.navigate().to(url);
    }

}
