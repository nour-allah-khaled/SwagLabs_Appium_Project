package com.swaglabs.pages;

import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Page03_ProductDetails {
    private AndroidDriver driver;
    private WaitManager wait;
    private final By CartBtn = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView");
    private final By cartBadge = AppiumBy.xpath("//android.widget.TextView[@text!='']");
    private final By BackHomeBtn = AppiumBy.accessibilityId("test-BACK TO PRODUCTS");
    private final By AddToCartBtn = AppiumBy.accessibilityId("test-ADD TO CART");
    private final By RemoveBtn = AppiumBy.accessibilityId("test-REMOVE");
    private Integer previousBadgeCount = null;
    private int lastRemovedCount = 0;
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
        } catch (Exception e) {
            LogsManager.info("Cart badge not present, returning 0");
            return "0";
        }
    }
    private int getCurrentCartBadgeCount() {
        String badgeText = getCartBadgeNumber();
        try {
            return badgeText == null || badgeText.isEmpty() ? 0 : Integer.parseInt(badgeText);
        } catch (NumberFormatException e) {
            LogsManager.warn("Unable to parse cart badge text '" + badgeText + "': " + e.getMessage());
            return 0;
        }
    }
    @Step("Clicking on Add to Cart button")
    public Page03_ProductDetails clickAddToCart() {
        previousBadgeCount = getCurrentCartBadgeCount();
        DriverManager.getElementAction().scrollToElement(AddToCartBtn);
        DriverManager.getElementAction().clicking(AddToCartBtn);
        return this;
    }
    @Step("Clicking on Remove button")
    public Page03_ProductDetails clickRemoveFromCart() {
        previousBadgeCount = getCurrentCartBadgeCount();
        DriverManager.getElementAction().scrollToElement(RemoveBtn);
        DriverManager.getElementAction().clicking(RemoveBtn);
        int currentCount = getCurrentCartBadgeCount();
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
    public Page03_ProductDetails assertCartBadgeCount() {
        if (previousBadgeCount == null) {
            LogsManager.warn("Previous badge count not recorded.");
        }
        int currentCount = getCurrentCartBadgeCount();
        int expectedCount = previousBadgeCount != null ? (previousBadgeCount - lastRemovedCount) : currentCount;
        DriverManager.hardAssertion().assertEquals(currentCount, expectedCount, "Cart badge did not change as expected after removal");
        LogsManager.info("Cart badge changed correctly from " + previousBadgeCount + " to " + currentCount + " (removed " + lastRemovedCount + ")");
        previousBadgeCount = null;
        lastRemovedCount = 0;
        return this;
    }
}
