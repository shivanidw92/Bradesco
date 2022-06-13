package steps;

import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;
import utils.GlobalDriver;

public class BaseSteps {

    protected static WebDriver driver;

    @Before
    public static void setup(Scenario scenario){
        GlobalDriver.set();
        driver = GlobalDriver.get();
    }

    @After
    public static void finish(){
        GlobalDriver.close();
    }

    public static void openBrowser(String url){
        driver.manage().window().maximize();
        driver.navigate().to(url);
    }

}
