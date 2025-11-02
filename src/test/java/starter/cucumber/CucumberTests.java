package starter.cucumber;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("features")
@ConfigurationParameter(
        key = "cucumber.plugin",
        value = "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm," +
                "pretty," +
                "html:target/cucumber-reports/cucumber.html,"
)
@ConfigurationParameter(key = "cucumber.execution.strict", value = "true")
public class CucumberTests {
}