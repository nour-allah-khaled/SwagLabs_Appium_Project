package com.swaglabs.pages;

import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class Page06_Overview {
    private  AndroidDriver driver;
    private WaitManager wait;
    private final By finishBtn = AppiumBy.accessibilityId("test-FINISH");
    private final By cancelBtn = AppiumBy.accessibilityId("test-CANCEL");
    private final By FinishPage = AppiumBy.androidUIAutomator("new UiSelector().text(\"CHECKOUT: COMPLETE!\")");
    private final By GetHomePage = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");
    public Page06_Overview(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WaitManager(driver);
    }
    public Page06_Overview clickFinish() {
        DriverManager.getElementAction().scrollToAndClick(this.finishBtn);
        return this;
    }
    public Page06_Overview clickCancel() {
        DriverManager.getElementAction().scrollToAndClick(this.cancelBtn);

        return this;
    }
    // Assertion methods
    public Page07_Finish assertToFinishPage(){
        String finishPage = DriverManager.getElementAction().getText(this.FinishPage);
        DriverManager.softAssertion().assertEquals(finishPage,"CHECKOUT: COMPLETE!","Navigation to Finish page failed");
        return new Page07_Finish(driver);
    }
    public Page02_Home assertToHome(){
        String title = driver.findElement(GetHomePage).getText();
        DriverManager.softAssertion().assertEquals(title,"PRODUCTS","Failed to navigate back to Home Page");
        return new Page02_Home(driver);
    }
}
