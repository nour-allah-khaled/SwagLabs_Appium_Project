package com.swaglabs.pages;

import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class Page07_Finish {
    private  AndroidDriver driver;
    private WaitManager wait;
    private final By backHomeBtn = AppiumBy.accessibilityId("test-BACK HOME");
    private final By GetHomePage = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");

    public Page07_Finish(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WaitManager(driver);
    }
    public Page07_Finish clickBackHome() {
        DriverManager.getElementAction().scrollToAndClick(backHomeBtn);
        return this;
    }
    // Assertion methods
    public Page02_Home assertToHome(){
        String title = driver.findElement(GetHomePage).getText();
        DriverManager.softAssertion().assertEquals(title,"PRODUCTS","Failed to navigate back to Home Page");
        return new Page02_Home(driver);
    }
}
