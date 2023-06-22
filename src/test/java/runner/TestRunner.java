package runner;

import org.testng.annotations.Test;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features={"src//test//java//features"},
                 glue = {"stepDefinitions"}
)

@Test
public class TestRunner extends AbstractTestNGCucumberTests{

}
