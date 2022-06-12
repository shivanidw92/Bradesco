package pages;

import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestRule;

import java.time.Duration;


public class BasePage extends TestRule {

    static Duration timeoutInSeconds = Duration.ofSeconds(30);

    public BasePage(){}

    protected void moveToElement(WebElement element){
        Actions action = new Actions(getDriver());
        action.moveToElement(element).build().perform();
    }

    public static void waitVisibilityOf(WebElement element){
        WebDriverWait wait = new WebDriverWait(getDriver(), timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitOf(int secondsNumber){
        new WebDriverWait(getDriver(), Duration.ofSeconds(secondsNumber));
    }

    public static void TakeScreenshot(Scenario scenario){
        final byte [] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "image");
        System.out.println("Capture Screenshot final ;D ...");
    }

}
