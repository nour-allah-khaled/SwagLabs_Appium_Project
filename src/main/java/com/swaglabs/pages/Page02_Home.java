package com.swaglabs.pages;

import com.github.javafaker.App;
import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page02_Home {
    private final AndroidDriver driver;
    private final WaitManager wait;
    private final By cartIcon = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView");
    private final By KebabMenu = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView");
    private final By logoutBtn = AppiumBy.xpath("//android.widget.TextView[@text=\"LOGOUT\"]");
    private final By productTitle = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Item']");
    private final By prodNameTitle = AppiumBy.xpath(".//android.widget.TextView[@content-desc='test-Item title']");
    private final By prodPrice = AppiumBy.xpath(".//android.widget.TextView[@content-desc='test-Price']");
    private final By specificProduct = AppiumBy.xpath("//android.widget.TextView[@content-desc=\"test-Item title\" and @text=\"Sauce Labs Bike Light\"]");
    private final By RemovespecificProduct = AppiumBy.xpath("(//android.view.ViewGroup[@content-desc=\"test-REMOVE\"])[2]");
    private final By LoginPage = AppiumBy.xpath("//android.widget.ScrollView[@content-desc=\"test-Login\"]/android.view.ViewGroup/android.widget.ImageView[1]");
    private final By cartBadge = AppiumBy.xpath("//android.widget.TextView[@text!='']");
    private final By productDetails = AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Backpack\")");
    private final By addToCartPage = AppiumBy.androidUIAutomator("new UiSelector().text(\"YOUR CART\")");
    private final By filterBtn = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(5)");
    private final By filterbyNameAZ = AppiumBy.xpath("//android.widget.TextView[@text=\"Name (A to Z)\"]");
    private final By filterbyNameZA = AppiumBy.xpath("//android.widget.TextView[@text=\"Name (Z to A)\"]");
    private final By filterbyPriceLowToHigh = AppiumBy.xpath("//android.widget.TextView[@text=\"Price (low to high)\"]");
    private final By filterbyPriceHighToLow = AppiumBy.xpath("//android.widget.TextView[@text=\"Price (high to low)\"]");
    private final By filterCancelBtn = AppiumBy.androidUIAutomator("new UiSelector().text(\"Cancel\")");
    public Page02_Home(AndroidDriver driver) {
        this.driver = driver;
        this.wait=new WaitManager(driver);
    }
    @Step("Clicking on Kebab Menu")
    public Page02_Home ClickOnKebabMenu(){
        driver.findElement(KebabMenu).click();
        return this;
    }
    By addToCartBtnForProduct(String productName) {
        return AppiumBy.xpath(
                "//android.widget.TextView[@content-desc='test-Item title' and @text='"
                        + productName + "']/following-sibling::android.view.ViewGroup[@content-desc='test-ADD TO CART']"
        );
    }
    By removeBtnForProduct(String productName) {
        return AppiumBy.xpath(
                "//android.widget.TextView[@content-desc='test-Item title' and @text='"
                        + productName + "']/following-sibling::android.view.ViewGroup[@content-desc='test-REMOVE']"
        );
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
    @Step("Filtering products by: {filterOption}")
    public Page02_Home filterProducts(String filterOption) {
        driver.findElement(filterBtn).click();
        wait.waitForElementVisible(filterbyNameAZ);
        wait.waitForElementVisible(filterbyNameZA);
        wait.waitForElementVisible(filterbyPriceLowToHigh);
        wait.waitForElementVisible(filterbyPriceHighToLow);
        switch (filterOption.toLowerCase()) {
            case "name (a to z)" -> driver.findElement(filterbyNameAZ).click();
            case "name (z to a)" -> driver.findElement(filterbyNameZA).click();
            case "price (low to high)" -> driver.findElement(filterbyPriceLowToHigh).click();
            case "price (high to low)" -> driver.findElement(filterbyPriceHighToLow).click();
            default -> {
                LogsManager.error("Invalid filter option: " + filterOption);
                throw new IllegalArgumentException("Invalid filter option: " + filterOption);
            }
        }
        wait.waitForElementVisible(productTitle);
        LogsManager.info("Filtered products by: " + filterOption);
        return this;
    }
    public List<String> getProductNamesList() {
        List<WebElement> products = driver.findElements(productTitle);
        List<String> productNames = new ArrayList<>();
        for (WebElement product : products) {
            String nameText = product.findElement(prodNameTitle).getText();
            productNames.add(nameText);
        }
        LogsManager.info("Product names: " + productNames);
        return productNames;
    }
    public List<Double> getProductPriceList() {
        List<WebElement> products = driver.findElements(productTitle);
        List<Double> prices = new ArrayList<>();
        for (WebElement product : products) {
            String priceText = product.findElement(prodPrice).getText();
            priceText = priceText.replace("$", "").trim();
            prices.add(Double.parseDouble(priceText));
        }
        LogsManager.info("Product prices: " + prices);
        return prices;
    }
    @Step("Canceling filter operation")
    public Page02_Home cancelFilter() {
        driver.findElement(filterBtn).click();
        driver.findElement(filterCancelBtn).click();
        wait.waitForElementVisible(productTitle);
        LogsManager.info("Filter operation canceled");
        return this;
    }
    @Step("Adding products to cart: {productNames}")
    public Page02_Home AddAllProductsToCart(String... productNames) {
        boolean found = false;
        int maxScrolls = 5;
        int scrolls = 0;
        wait.waitForElementVisible(productTitle);
        for (String productName : productNames) {
            found = false;
            scrolls = 0;
            while (!found && scrolls < maxScrolls) {
                List<WebElement> products = DriverManager.getElementAction().findElements(productTitle);

                for (WebElement product : products) {
                    try {
                        WebElement titleElement = product.findElement(prodNameTitle);
                        if (titleElement.getText().equalsIgnoreCase(productName)) {
                            DriverManager.getElementAction().scrollToElement(addToCartBtnForProduct(productName));
                            DriverManager.getElementAction().clicking(addToCartBtnForProduct(productName));
                            LogsManager.info("Added product to cart: " + productName);
                            found = true;
                            break;
                        }
                    } catch (Exception e) {
                        LogsManager.info("Exception while finding product: " + e.getMessage());
                    }
                }
                if (!found) {
                    DriverManager.mobileAction().swipeDown();
                    scrolls++;
                    LogsManager.info("Scrolling down to find: " + productName);
                }
            }
            if (!found) {
                LogsManager.error("Product not found after scrolling: " + productName);
            }
        }
        LogsManager.info("All selected products added to cart: " + productNames);
        return this;
    }
    @Step("Removing selected products: {productNames}")
    public Page02_Home removeSelectedProductsFromCart(String... productNames) {
        boolean found = false;
        int maxScrolls = 5;
        int scrolls = 0;
        wait.waitForElementVisible(productTitle);
        for (String productName : productNames) {
            found = false;
            scrolls = 0;
            while (!found && scrolls < maxScrolls) {
                List<WebElement> products = DriverManager.getElementAction().findElements(productTitle);
                for (WebElement product : products) {
                    try {
                        WebElement titleElement = product.findElement(prodNameTitle);

                        if (titleElement.getText().equalsIgnoreCase(productName)) {
                            DriverManager.getElementAction().scrollToElement(removeBtnForProduct(productName));
                            DriverManager.getElementAction().clicking(removeBtnForProduct(productName));
                            LogsManager.info("Removed product from cart: " + productName);
                            found = true;
                            break;
                        }
                    } catch (Exception e) {
                        LogsManager.info("Exception while finding product: " + e.getMessage());
                    }
                }
                if (!found) {
                    DriverManager.mobileAction().swipeDown();
                    scrolls++;
                    LogsManager.info("Scrolling down to find: " + productName);
                }
            }
            if (!found) {
                LogsManager.error("Product not found after scrolling: " + productName);
            }
        }
        LogsManager.info("All selected products removed: " + productNames);
        return this;
    }
    @Step("Clicking on Logout button")
    public Page01_Login ClickOnLogoutBtn(){
        driver.findElement(logoutBtn).click();
        return new Page01_Login(driver);
    }
    @Step("Clicking on Cart icon")
    public Page04_AddToCart ClickOnCartIcon(){
        driver.findElement(cartIcon).click();
        return new Page04_AddToCart(driver);
    }
    @Step("Clicking on a specific product title to navigate to its details page")
    public Page03_ProductDetails ClickOnSpecificProductTitle() {
        driver.findElement(specificProduct).click();
        return new Page03_ProductDetails(driver);
    }
    @Step("Removing a specific product from the cart")
    public Page02_Home RemoveSpecificProductFromCart() {
        driver.findElement(RemovespecificProduct).click();
        return this;
    }
    //Assertion Methods
    public Page01_Login assertToLogout(){
        boolean isLoginPageDisplayed = driver.findElement(LoginPage).isDisplayed();
        DriverManager.hardAssertion().assertTrue(isLoginPageDisplayed, "Logout not successful - Login page not displayed");
        return new Page01_Login(driver);
    }
    public Page02_Home assertCartBadgeCount(int previousCount) {
        String badgeText = getCartBadgeNumber();
        int currentCount = badgeText.isEmpty() ? 0 : Integer.parseInt(badgeText);
        int expectedCount = previousCount + 1;
        DriverManager.hardAssertion().assertEquals(currentCount, expectedCount, "Cart badge did not increase by 1 as expected");
        LogsManager.info("Cart badge increased correctly from " + previousCount + " to " + currentCount);
        return this;
    }
    public Page03_ProductDetails assertToProductDetails(){
        String actualProductName = DriverManager.getElementAction().getText(productDetails);
        DriverManager.hardAssertion().assertEquals(actualProductName, "Sauce Labs Backpack", "Product details page not displayed correctly");
        return new Page03_ProductDetails(driver);

    }
    public Page04_AddToCart assertToAddToCartPage(){
        String actualTitle = DriverManager.getElementAction().getText(addToCartPage);
        DriverManager.hardAssertion().assertEquals(actualTitle, "YOUR CART", "Add to Cart page not displayed correctly");
        return new Page04_AddToCart(driver);
    }
    public Page02_Home assertPricesSortedLowToHigh() {
        List<Double> actualPrices = getProductPriceList();
        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);
        DriverManager.hardAssertion().assertEquals(
                actualPrices,
                sortedPrices,
                "Products are not sorted by price (Low to High)"
        );
        LogsManager.info("Verified products are sorted by Price (Low to High)");
        return this;
    }
    public Page02_Home assertPricesSortedHighToLow() {
        List<Double> actualPrices = getProductPriceList();
        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices, Collections.reverseOrder());
        DriverManager.hardAssertion().assertEquals(
                actualPrices,
                sortedPrices,
                "Products are not sorted by price (High to Low)"
        );
        LogsManager.info("Verified products are sorted by Price (High to Low)");
        return this;
    }
    public Page02_Home assertNamesSortedAToZ() {
        List<String> actualNames = getProductNamesList();
        List<String> sortedNames = new ArrayList<>(actualNames);
        Collections.sort(sortedNames);
        DriverManager.hardAssertion().assertEquals(
                actualNames,
                sortedNames,
                "Products are not sorted by Name (A to Z)"
        );
        LogsManager.info("Verified products are sorted by Name (A to Z)");
        return this;
    }
    public Page02_Home assertNamesSortedZToA() {
        List<String> actualNames = getProductNamesList();
        List<String> sortedNames = new ArrayList<>(actualNames);
        Collections.sort(sortedNames, Collections.reverseOrder());
        DriverManager.hardAssertion().assertEquals(
                actualNames,
                sortedNames,
                "Products are not sorted by Name (Z to A)"
        );
        LogsManager.info("Verified products are sorted by Name (Z to A)");
        return this;
    }
}
