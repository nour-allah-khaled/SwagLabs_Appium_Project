package com.swaglabs.pages;

import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class Page05_CheckOut {
    private AndroidDriver driver;
    private WaitManager wait;
    private final By firstnameField = AppiumBy.androidUIAutomator("new UiSelector().text(\"First Name\")");
    private final By lastnameField = AppiumBy.androidUIAutomator("new UiSelector().text(\"Last Name\")");
    private final By postalCodeField = AppiumBy.androidUIAutomator("new UiSelector().text(\"Zip/Postal Code\")");
    private final By cancelBtn = AppiumBy.androidUIAutomator("new UiSelector().description(\"test-CANCEL\")");
    private final By continueBtn = AppiumBy.androidUIAutomator("new UiSelector().description(\"test-CONTINUE\")");
    private final By OverviewPage = AppiumBy.androidUIAutomator("new UiSelector().text(\"CHECKOUT: OVERVIEW\")");
    private final By GetHomePage = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");
    private final By emptyFirstnameMsg =AppiumBy.androidUIAutomator("new UiSelector().text(\"First Name is required\")");
    private final By emptyLastnameMsg =AppiumBy.androidUIAutomator("new UiSelector().text(\"Last Name is required\")");
    private final By emptyPostalCodeMsg =AppiumBy.androidUIAutomator("new UiSelector().text(\"Postal Code is required\")");
    public Page05_CheckOut(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WaitManager(driver);
    }
    @Step("Entering first name: {firstName}")
    public Page05_CheckOut enterFirstName(String firstName) {
        DriverManager.getElementAction().sendkeys(this.firstnameField, firstName);
        return this;
    }
    @Step("Entering last name: {lastName}")
    public Page05_CheckOut enterLastName(String lastName) {
        DriverManager.getElementAction().sendkeys(this.lastnameField, lastName);
        return this;
    }
    @Step("Entering postal code: {postalCode}")
    public Page05_CheckOut enterPostalCode(String postalCode) {
        DriverManager.getElementAction().sendkeys(this.postalCodeField, postalCode);
        return this;
    }
    @Step("Clicking on Cancel button")
    public Page05_CheckOut clickCancel() {
        wait.waitForElementVisible(cancelBtn);
        DriverManager.getElementAction().scrollToAndClick(this.cancelBtn);
        return this;
    }
    @Step("Clicking on Continue button")
    public Page05_CheckOut clickContinue() {
        wait.waitForElementVisible(continueBtn);
        DriverManager.getElementAction().scrollToAndClick(this.continueBtn);
        return this;
    }
    // Assertion methods
    public Page06_Overview assertToOverview()
    {
        String overviewPage = DriverManager.getElementAction().getText(this.OverviewPage);
        DriverManager.softAssertion().assertEquals(overviewPage,"CHECKOUT: OVERVIEW","Navigation to Overview page failed");
        return new Page06_Overview(driver);
    }
    public Page02_Home assertToHome(){
        String HomePage = DriverManager.getElementAction().getText(this.GetHomePage);
        DriverManager.softAssertion().assertEquals(HomePage,"PRODUCTS","Navigation back to Home page failed");
        return new Page02_Home(driver);
    }
    public Page05_CheckOut assertEmptyFirstnameMsg()
    {
        String actual=DriverManager.getElementAction().getText(this.emptyFirstnameMsg);
        DriverManager.softAssertion().assertEquals(actual,"First Name is required","Empty First Name message not matched");
        return this;
    }
    public Page05_CheckOut assertEmptyLastnameMsg()
    {
        String actual=DriverManager.getElementAction().getText(this.emptyLastnameMsg);
        DriverManager.softAssertion().assertEquals(actual,"Last Name is required","Empty Last Name message not matched");
        return this;
    }
    public Page05_CheckOut assertEmptyPostalCodeMsg()
    {
        String actual=DriverManager.getElementAction().getText(this.emptyPostalCodeMsg);
        DriverManager.softAssertion().assertEquals(actual,"Postal Code is required","Empty Postal Code message not matched");
        return this;
    }
}
