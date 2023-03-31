package features;

import java.util.List;

import environment.JenkinsSandbox;
import net.serenitybdd.integration.jenkins.JenkinsInstance;
import net.serenitybdd.integration.jenkins.environment.rules.ApplicativeTestRule;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@RunWith(SerenityRunner.class)
public abstract class BuildMonitorAcceptanceTest {
    @Managed(driver = "chrome", uniqueSession = true)
    public WebDriver browser;

    @Rule
    public JenkinsInstance jenkins = JenkinsSandbox.configure()
            .beforeStart(jenkinsBeforeStartRules())
            .afterStart(jenkinsAfterStartRules())
            .create();
    @Before
    public void setupChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");

        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        browser = new ChromeDriver(options);
    }

    protected List<? extends ApplicativeTestRule<JenkinsInstance>> jenkinsBeforeStartRules() {
        return List.of();
    }

    protected List<? extends ApplicativeTestRule<JenkinsInstance>> jenkinsAfterStartRules() {
        return List.of();
    }
}
