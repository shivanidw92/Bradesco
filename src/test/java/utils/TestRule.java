package utils;

import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;

public class TestRule {

    private static WebDriver driver;

    public TestRule(){
        super();
    }

    @Before
    public void setup(Scenario scenario){}

    @After
    public static void finish(){
        closeDriver();
    }

    protected static WebDriver getDriver(){
        return driver;
    }

    public static void abrirNavegador(String url){
        driver = BrowserUtils.getWebDriver();
        driver.manage().window().maximize();
        driver.navigate().to(url);
    }

    private static void closeDriver(){
        if(driver != null){
            driver.close();
            driver.quit();
            driver = null;
        }
    }
}
