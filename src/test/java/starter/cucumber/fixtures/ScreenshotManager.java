package starter.cucumber.fixtures;

import com.microsoft.playwright.Page;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

public class ScreenshotManager {

    public void takeScreenshot(Page page, String name) {
        var screenshot = page.screenshot(
                new Page.ScreenshotOptions().setFullPage(true)
        );
        String safeName = name.replaceAll("[^a-zA-Z0-9-_ ]", "_");
        Allure.addAttachment(safeName, new ByteArrayInputStream(screenshot));
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            Page page = PlaywrightDriverFactory.getPage();
            takeScreenshot(page, "Failed Step - " + scenario.getName());
        }
    }
}
