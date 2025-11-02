package starter.cucumber.fixtures;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;

public class PlaywrightDriverFactory {

    // Thread-safe Playwright instance
    private static final ThreadLocal<Playwright> playwright = ThreadLocal.withInitial(() -> {
        Playwright pw = Playwright.create();
        pw.selectors().setTestIdAttribute("data-test");
        return pw;
    });

    // Thread-safe Browser instance, parameterized by browser type
    private static final ThreadLocal<Browser> browser = ThreadLocal.withInitial(() -> {
        String browserName = System.getProperty("browser",
                        System.getenv().getOrDefault("BROWSER", "chromium"))
                .toLowerCase(Locale.ROOT);

        BrowserType browserType;
        Playwright pw = playwright.get();

        browserType = switch (browserName) {
            case "firefox" -> pw.firefox();
            case "webkit" -> pw.webkit();
            default -> pw.chromium();
        };

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(Boolean.parseBoolean(System.getProperty("headless", "true")))
                .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"));

        return browserType.launch(options);
    });

    private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static final ThreadLocal<Page> page = new ThreadLocal<>();

    @Before(order = 100)
    public void setUpBrowserContext() {
//        browserContext.set(browser.get().newContext(new Browser.NewContextOptions().
//                setRecordVideoDir(Paths.get("target/videos")).
//                setRecordVideoSize(640, 480)));

        browserContext.set(browser.get().newContext());
        page.set(browserContext.get().newPage());
    }

    @After(order = 100)
    public void closeContext() {
        browserContext.get().close();
    }

    @AfterAll
    public static void tearDown() {
        browser.get().close();
        browser.remove();

        playwright.get().close();
        playwright.remove();
    }

    public static Page getPage() {
        return page.get();
    }

    public static BrowserContext getBrowserContext() {
        return browserContext.get();
    }
}
