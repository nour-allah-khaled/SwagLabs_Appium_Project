package com.swaglabs.pages;

import com.swaglabs.utils.actions.ElementAction;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class Page01_Login {
    private AndroidDriver driver;
    private final By username = AppiumBy.accessibilityId("test-Username");
    private final By password = AppiumBy.accessibilityId("test-Password");
    private final By loginBtn = AppiumBy.accessibilityId("test-LOGIN");
    private final By ClickUsernam = AppiumBy.xpath("//android.widget.TextView[@text=\"standard_user\"]\n");
    public Page01_Login(AndroidDriver driver) {
        this.driver=driver;
    }
    public void userName(String username) {
        new ElementAction(driver).sendkeys(this.username, username);
    }
}
