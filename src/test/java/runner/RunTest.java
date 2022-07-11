package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "./src/test/resources/features",
        glue = {"steps"},
        tags = "@run2",
        monochrome = true,
        plugin = {
//            "pretty",
//            "junit:results.xml",
//            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
            "com.hpe.alm.octane.OctaneGherkinFormatter:gherkin-results/OctaneGherkinResults.xml"
        }
)
public class RunTest {}
