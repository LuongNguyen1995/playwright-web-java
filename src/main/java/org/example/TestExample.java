package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class TestExample extends BaseTest{
    // Shared between all tests in this class.


    // New instance for each test method.
    BrowserContext context;
    Page page;


    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")));
// Make sure to close, so that videos are saved.

        page = context.newPage();

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    @AfterEach

    void closeContext() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
        context.close();
    }

    @Test
    void verifyLoginWithEmptyData() {
        page.navigate("https://demo.nopcommerce.com/");
        page.locator("a.ico-login").click();
        page.locator("button[class$='login-button']").click();
        assertThat(page.locator("span#Email-error")).hasText("Please enter your email");
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")));
    }


}