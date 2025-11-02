package starter.cucumber.fixtures;

import com.microsoft.playwright.Tracing;
import io.cucumber.java.*;
import java.nio.file.Paths;

public class PlaywrightTracing {

    @Before
    public void setupTracing() {
        if (Boolean.getBoolean("enableTracing")) {
            PlaywrightDriverFactory.getBrowserContext().tracing().start(
                    new Tracing.StartOptions()
                            .setScreenshots(true)
                            .setSnapshots(true)
                            .setSources(true)
            );
        }
    }

    @After
    public void recordTraces(Scenario scenario) {
        if (Boolean.getBoolean("enableTracing")) {
            String traceName = scenario.getName()
                    .replaceAll("[^a-zA-Z0-9-_]", "_")
                    .toLowerCase();

            PlaywrightDriverFactory.getBrowserContext().tracing().stop(
                    new Tracing.StopOptions()
                            .setPath(Paths.get("target/traces/trace-" + traceName + ".zip"))
            );
        }
    }
}
