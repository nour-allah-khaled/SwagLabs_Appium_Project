package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class BaseClass {
    public AndroidDriver driver;
    public AppiumDriverLocalService service;
    public WebDriverWait wait;
    @BeforeClass
    public void configureAppium() throws URISyntaxException, MalformedURLException {
        service = new AppiumServiceBuilder().withAppiumJS(new File("C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js")).
                withIPAddress("127.0.0.1").usingPort(4723).withArgument(() -> "--allow-cors").build();
        service.start();
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setDeviceName("TestEmulatorApp");
        options.setAppPackage("com.swaglabsmobileapp");
        options.setAppActivity("com.swaglabsmobileapp.MainActivity");

        driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }
    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }
}
