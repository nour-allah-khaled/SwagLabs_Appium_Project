package com.swaglabs.pages;

import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class Page03_ProductDetails {
    private AndroidDriver driver;
    private WaitManager wait;
    private final By CartBtn = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView");
    private final By cartBadge = AppiumBy.xpath("//android.widget.TextView[@text!='']");
    private final By BackHomeBtn = AppiumBy.accessibilityId("test-BACK TO PRODUCTS");
    private final By AddToCartBtn = AppiumBy.accessibilityId("test-ADD TO CART");
    private final By RemoveBtn = AppiumBy.accessibilityId("test-REMOVE");
    private final By addToCartPage = AppiumBy.androidUIAutomator("new UiSelector().text(\"YOUR CART\")");
    private final By GetHomePage = AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]");

    public Page03_ProductDetails(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WaitManager(driver);
    }
    public String getCartBadgeNumber() {
        try {
            WebElement badgeElement = driver.findElement(cartBadge);
            String badgeText = badgeElement.getText();
            LogsManager.info("Cart badge number: " + badgeText);
            return badgeText;
        } catch (NoSuchElementException e) {
            return null;
        }
    }
    @Step("Clicking on Add to Cart button")
    public Page03_ProductDetails clickAddToCart() {
        DriverManager.getElementAction().scrollToAndClick(AddToCartBtn);
        return this;
    }
    @Step("Clicking on Remove button")
    public Page03_ProductDetails clickRemoveFromCart() {
        DriverManager.getElementAction().scrollToAndClick(RemoveBtn);
        return this;
    }
    @Step("Clicking on Back to Products button")
    public Page03_ProductDetails clickBackToHome() {
        DriverManager.getElementAction().clicking(BackHomeBtn);
        return this;
    }
    @Step("Clicking on Cart icon to navigate to Cart page")
    public Page03_ProductDetails clickonCartIcon() {
        DriverManager.getElementAction().clicking(CartBtn);
        return this;
    }
    //Assertion methods
    public Page03_ProductDetails assertCartBadgeState(String state) {
        String BadgeNumber = getCartBadgeNumber();
        switch (state.toLowerCase()) {
            case "added" -> {
                wait.waitForElementVisible(cartBadge);
                DriverManager.softAssertion().assertEquals(BadgeNumber, "1", "Product not added to cart properly");
                LogsManager.info("Product added to cart, badge shows '1'");
                return this;
            }
            case "removed" -> {
                wait.waitForElementNotVisible(cartBadge);
                String BadgeNumberAfterRemove = getCartBadgeNumber();
                boolean isBadgeEmptyOrZero = BadgeNumberAfterRemove == null || BadgeNumberAfterRemove.trim().isEmpty() || BadgeNumberAfterRemove.trim().equals("0");
                DriverManager.softAssertion().assertFalse(isBadgeEmptyOrZero, "Product not removed properly");
                LogsManager.info("Product removed from cart, badge is empty or '0'");
                return this;
            }
        }
        return this;
    }
    public Page04_AddToCart assertToAddToCartPage(){
        String actualTitle = DriverManager.getElementAction().getText(addToCartPage);
        DriverManager.softAssertion().assertEquals(actualTitle, "YOUR CART", "Add to Cart page not displayed correctly");
        return new Page04_AddToCart(driver);
    }
    public  Page02_Home assertToHomePage(){
        String homeTitle = DriverManager.getElementAction().getText(GetHomePage);
        DriverManager.softAssertion().assertEquals(homeTitle, "PRODUCTS", "Not navigated back to Home page");
        return new Page02_Home(driver);
    }
}
