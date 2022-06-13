package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "./src/test/resources/features",
        tags = "@run",
        glue = {"steps"},
        plugin = { "pretty", "json:target/jsonReports/cucumber-report.json"},
        monochrome = true
)
public class RunTest {}
