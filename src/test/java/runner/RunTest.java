package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import utils.TestRule;
import org.junit.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "./src/test/resources/features",
        tags = "@run",
        glue = {"steps"},
        plugin = { "summary", "pretty", "json:target/jsonReports/cucumber-report.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        monochrome = true
)
public class RunTest {

    @ClassRule
    public static TestRule testRule = new TestRule();

}
