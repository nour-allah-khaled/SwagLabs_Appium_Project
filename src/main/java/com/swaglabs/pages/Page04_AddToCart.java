package com.swaglabs.pages;
import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class Page04_AddToCart {
    private AndroidDriver driver;
    private WaitManager wait;
    private final By ContinueShoppingBtn = AppiumBy.accessibilityId("test-CONTINUE SHOPPING");
    private final By CheckoutBtn = AppiumBy.accessibilityId("test-CHECKOUT");
    private final By BackHomeBtn = AppiumBy.accessibilityId("test-BACK TO PRODUCTS");
    private final By CheckoutPage = AppiumBy.xpath("//android.widget.TextView[@text=\"CHECKOUT: INFORMATION\"]");
    public Page04_AddToCart(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WaitManager(driver);
    }
    @Step("Clicking on Continue Shopping button")
    public Page04_AddToCart clickContinueShopping() {
        DriverManager.getElementAction().scrollToAndClick(ContinueShoppingBtn);
        wait.waitForElementVisible(BackHomeBtn);
        return this;
    }
    @Step("Clicking on Checkout button")
    public Page04_AddToCart clickCheckout() {
        DriverManager.getElementAction().scrollToAndClick(CheckoutBtn);
        wait.waitForElementVisible(CheckoutPage);
        return this;
    }
    //Assertion methods
    public Page02_Home assertToHome(){
        String title = driver.findElement(BackHomeBtn).getText();
        DriverManager.softAssertion().assertEquals(title,"PRODUCTS","Failed to navigate back to Home Page");
        return new Page02_Home(driver);
    }
    public Page05_CheckOut assertToCheckoutPage(){
        String title = driver.findElement(CheckoutPage).getText();
        DriverManager.softAssertion().assertEquals(title,"CHECKOUT: INFORMATION","Failed to navigate to Checkout Page");
        return new Page05_CheckOut(driver);
    }
}
