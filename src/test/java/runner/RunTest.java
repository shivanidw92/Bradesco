package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "./src/test/resources/features",
        glue = {"steps"},
        tags = "@runP",
        monochrome = true,
        plugin = {
            "pretty",
            "junit:results.xml",
            "com.hpe.alm.octane.OctaneGherkinFormatter:gherkin-results/OctaneGherkinResults.xml",
            "plugin.StepDetailsPlugin"
        }
)
public class RunTest {}
