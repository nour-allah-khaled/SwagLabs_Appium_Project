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
    private final By getInvalidMsg = AppiumBy.xpath("//android.widget.TextView[@text=\"Username and password do not match any user in this service.\"]\n");
    private final By getEmptyMsg = AppiumBy.xpath("//android.widget.TextView[@text=\"Username is required\"]");
    private final By GetHomePage = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");
    public Page01_Login(AndroidDriver driver) {
        this.driver=driver;
    }
    public Page01_Login userName(String username) {
        DriverManager.getElementAction().clicking(this.username);
        DriverManager.getElementAction().sendkeys(this.username, username);
        return this;
    }
    public Page01_Login passWord(String password) {
        DriverManager.getElementAction().clicking(this.password);
        DriverManager.getElementAction().sendkeys(this.password, password);
        return this;
    }
    public Page01_Login loginBtn() {
        DriverManager.getElementAction().clicking(this.loginBtn);
        return this;
    }
    public Page02_Home clickUsernam() {
        DriverManager.getElementAction().scrollToElement(this.ClickUsernam);
        DriverManager.getElementAction().clicking(this.loginBtn);
        return new Page02_Home(driver);
    }
    //Assertion methods can be added here
    public Page02_Home assertLogin(){
        String HomePage = DriverManager.getElementAction().getText(this.GetHomePage);
        DriverManager.hardAssertion().assertEquals(HomePage,"PRODUCTS","Login not successful");
        return new Page02_Home(driver);
    }
    public Page01_Login assertInvalidMsg()
    {
        String actual=DriverManager.getElementAction().getText(this.getInvalidMsg);
        DriverManager.hardAssertion().assertEquals(actual,"Username and password do not match any user in this service.","Invalid message not matched");
        return this;
    }
    public Page01_Login assertEmptyMsg()
    {
        String actual=DriverManager.getElementAction().getText(this.getEmptyMsg);
        DriverManager.hardAssertion().assertEquals(actual,"Username is required","Empty message not matched");
        return this;
    }

}
