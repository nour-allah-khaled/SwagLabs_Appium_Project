package com.swaglabs.pages;

import com.swaglabs.utils.actions.ElementAction;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import com.swaglabs.android.DriverManager;

public class Page01_Login {
    private AndroidDriver driver;
    private final By username = AppiumBy.accessibilityId("test-Username");
    private final By password = AppiumBy.accessibilityId("test-Password");
    private final By loginBtn = AppiumBy.accessibilityId("test-LOGIN");
    private final By ClickUsernam = AppiumBy.xpath("//android.widget.TextView[@text=\"standard_user\"]\n");
    public Page01_Login(AndroidDriver driver) {
        this.driver=driver;
    }
    public Page01_Login userName(String username) {
        DriverManager.getElementAction().sendkeys(this.username, username);
        return this;
    }
    public Page01_Login passWord(String password) {
        DriverManager.getElementAction().sendkeys(this.password, password);
        return this;
    }
    public Page02_Home loginBtn() {
        DriverManager.getElementAction().clicking(this.loginBtn);
        return new Page02_Home(driver);
    }
    public Page02_Home clickUsernam() {
        DriverManager.getElementAction().scrollToElement(this.ClickUsernam);
        DriverManager.getElementAction().clicking(this.loginBtn);
        return new Page02_Home(driver);
    }
    //Assertion methods can be added here

}
