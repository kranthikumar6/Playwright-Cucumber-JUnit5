package starter.catalog.pageobjects;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

public class NavBar {

    private final Page page;

    public NavBar(Page page) {
        this.page = page;
    }

    public void openCart() {
        page.getByTestId("nav-cart").click();
    }

    public void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForSelector("xpath=(//img[@class='card-img-top'])[1]");
    }

    public void toTheContactPage() {
        page.navigate("https://practicesoftwaretesting.com/contact");
    }
}
