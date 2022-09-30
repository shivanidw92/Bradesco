package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import model.Platform;
import org.openqa.selenium.WebDriver;
import report.Report;
import utils.GlobalDriver;

public class BaseSteps {

    protected static WebDriver driver;

    @Before
    public static void setup(Scenario scenario){
        GlobalDriver.set();
        driver = GlobalDriver.get();
        Report.init(driver, Platform.WEB);
    }

    @After
    public static void finish(Scenario scenario) {
        Report.generatePDF(scenario);
        GlobalDriver.close();
    }

    public static void openBrowser(String url){
        driver.manage().window().maximize();
        driver.navigate().to(url);
    }

}
