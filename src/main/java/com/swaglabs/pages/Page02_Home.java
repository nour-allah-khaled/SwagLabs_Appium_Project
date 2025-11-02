package com.swaglabs.pages;

import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Page02_Home {
    private final AndroidDriver driver;
    private final WaitManager wait;
    // Menu & Logout
    private final By KebabMenu = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView");
    private final By logoutBtn = AppiumBy.xpath("//android.widget.TextView[@text=\"LOGOUT\"]");
    private final By LoginPage = AppiumBy.xpath("//android.widget.ScrollView[@content-desc=\"test-Login\"]/android.view.ViewGroup/android.widget.ImageView[1]");
    // Cart
    private final By cartIcon = AppiumBy.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView");
    private final By cartBadge = AppiumBy.xpath("//android.widget.TextView[@text!='']");
    private final By addToCartPage = AppiumBy.androidUIAutomator("new UiSelector().text(\"YOUR CART\")");
    private Integer previousBadgeCount = null;
    private int lastRemovedCount = 0;
    // Products
    private final By products = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='test-Item']");
    private final By productName = AppiumBy.xpath(".//android.widget.TextView[@content-desc='test-Item title']");
    private final By productPrice = AppiumBy.xpath(".//android.widget.TextView[@content-desc='test-Price']");
    private final By removeFromCartBtn = AppiumBy.xpath(".//android.view.ViewGroup[@content-desc='test-REMOVE']");
    private final By specificProduct = AppiumBy.xpath("//android.widget.TextView[@content-desc=\"test-Item title\" and @text=\"Sauce Labs Backpack\"]");
    private final By productDetails = AppiumBy.androidUIAutomator("new UiSelector().text(\"Sauce Labs Backpack\")");
    // Filters
    private final By filterBtn = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(5)");
    private final By filterbyNameAZ = AppiumBy.xpath("//android.widget.TextView[@text=\"Name (A to Z)\"]");
    private final By filterbyNameZA = AppiumBy.xpath("//android.widget.TextView[@text=\"Name (Z to A)\"]");
    private final By filterbyPriceLowToHigh = AppiumBy.xpath("//android.widget.TextView[@text=\"Price (low to high)\"]");
    private final By filterbyPriceHighToLow = AppiumBy.xpath("//android.widget.TextView[@text=\"Price (high to low)\"]");

    public Page02_Home(AndroidDriver driver) {
        this.driver = driver;
        this.wait=new WaitManager(driver);
    }
    @Step("Clicking on Kebab Menu")
    public Page02_Home ClickOnKebabMenu(){
        driver.findElement(KebabMenu).click();
        return this;
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
        wait.waitForProductListToLoad(productName);
        LogsManager.info("Filtered products by: " + filterOption);
        return this;
    }
    public List<String> getProductNamesList() {
        List<String> productNames = new ArrayList<>();
        try {
            List<WebElement> titleElements = driver.findElements(productName);
            if (titleElements.isEmpty()) {
                LogsManager.info("No product names found initially, attempting to scroll to find them");
                DriverManager.getElementAction().scrollUntilVisible(productName);
                titleElements = driver.findElements(productName);
            }
            for (WebElement titleElement : titleElements) {
                try {
                    String titleText = titleElement.getText();
                    if (!titleText.isEmpty()) {
                        productNames.add(titleText);
                    }
                } catch (Exception e) {
                    LogsManager.warn("Error getting text from title element: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            LogsManager.error("Error in getProductNamesList: " + e.getMessage());
        }
        
        LogsManager.info("Product names found: " + productNames);
        return productNames;
    }
    public List<Double> getProductPriceList() {
        List<Double> prices = new ArrayList<>();
        try {
            List<WebElement> priceElements = driver.findElements(productPrice); // Use direct locator

            for (WebElement priceElement : priceElements) {
                String priceText = priceElement.getText().replace("$", "").trim();
                if (!priceText.isEmpty()) {
                    prices.add(Double.parseDouble(priceText));
                }
            }
        } catch (Exception e) {
            LogsManager.error("Error getting product price list: " + e.getMessage());
        }
        LogsManager.info("Product prices list: " + prices);
        return prices;
    }
    @Step("Adding selected products to cart: {productNames}")
    public Page02_Home AddAllProductsToCart(String... productNames) {
        List<String> productsToAdd = new ArrayList<>(Arrays.asList(productNames));
        List<String> addedProducts = new ArrayList<>();
        int maxScrollAttempts = 10;
        int scrollCount = 0;
        LogsManager.info("Starting to add products to cart: " + productsToAdd);
        while (!productsToAdd.isEmpty() && scrollCount < maxScrollAttempts) {
            scrollCount++;
            LogsManager.info("Scroll attempt " + scrollCount + " - Products remaining: " + productsToAdd);
            List<String> visibleProducts = getCurrentlyVisibleProducts();
            LogsManager.info("Currently visible products: " + visibleProducts);
            List<String> productsToRemove = new ArrayList<>();
            for (String productName : productsToAdd) {
                if (visibleProducts.contains(productName)) {
                    try {
                        By addToCartLocator = AppiumBy.xpath("//android.widget.TextView[@content-desc='test-Item title' and @text='" + productName + "']/following-sibling::android.view.ViewGroup[@content-desc='test-ADD TO CART']");
                        WebElement addBtn = driver.findElement(addToCartLocator);
                        
                        if (addBtn.isDisplayed()) {
                            addBtn.click();
                            LogsManager.info("Successfully added product to cart: " + productName);
                            addedProducts.add(productName);
                            productsToRemove.add(productName);
                        } else {
                            LogsManager.info("Add to cart button not visible for: " + productName);
                        }
                    } catch (NoSuchElementException e) {
                        LogsManager.info("Add to cart button not found for: " + productName);
                    } catch (Exception e) {
                        LogsManager.warn("Error adding product to cart: " + productName + " - " + e.getMessage());
                    }
                }
            }
            productsToAdd.removeAll(productsToRemove);
            if (!productsToAdd.isEmpty() && scrollCount < maxScrollAttempts) {
                LogsManager.info("Scrolling down to find more products...");
                DriverManager.mobileAction().swipeDown();
                wait.fluentWait().until(driver -> {
                    try {
                        return !driver.findElements(products).isEmpty();
                    } catch (Exception ex) {
                        return false;
                    }
                });
            }
        }
        LogsManager.info("Successfully added products: " + addedProducts);
        if (!productsToAdd.isEmpty()) {
            LogsManager.warn("Could not add these products after " + maxScrollAttempts + " scroll attempts: " + productsToAdd);
        }
        return this;
    }
    
    @Step("Getting currently visible products on screen")
    private List<String> getCurrentlyVisibleProducts() {
        List<String> visibleProducts = new ArrayList<>();
        try {
            List<WebElement> titleElements = driver.findElements(productName);
            for (WebElement titleElement : titleElements) {
                try {
                    String titleText = titleElement.getText();
                    if (!titleText.isEmpty()) {
                        visibleProducts.add(titleText);
                    }
                } catch (Exception e) {
                    LogsManager.warn("Error getting text from title element: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            LogsManager.error("Error in getCurrentlyVisibleProducts: " + e.getMessage());
        }
        return visibleProducts;
    }
    @Step("Removing selected products from cart: {productNames}")
    public Page02_Home removeSelectedProductsFromCart(String... productNames) {
        previousBadgeCount = getCurrentCartBadgeCount();
        lastRemovedCount = 0;
        wait.waitForElementVisible(products);
        List<WebElement> productElements = driver.findElements(products);
        if (productElements.isEmpty()) {
            LogsManager.info("No products found initially, attempting to scroll to find products for removal");
            DriverManager.getElementAction().scrollUntilVisible(products);
            productElements = driver.findElements(products);
        }
        for (String name : productNames) {
            boolean found = false;
            for (WebElement product : productElements) {
                try {
                    WebElement titleElement = product.findElement(productName);
                    String titleText = titleElement.getText();
                    if (titleText.equals(name)) {
                        WebElement removeBtn = product.findElement(removeFromCartBtn);
                        if (removeBtn.isDisplayed()) {
                            removeBtn.click();
                            LogsManager.info("Removed product from cart: " + titleText);
                            found = true;
                            lastRemovedCount++;
                            break;
                        }
                    }
                } catch (NoSuchElementException ignored) {}
            }
            if (!found) {
                LogsManager.warn("Product not found on page (Remove): " + name);
            }
        }
        return this;
    }
    @Step("Clicking on Logout button")
    public Page02_Home ClickOnLogoutBtn(){
        driver.findElement(logoutBtn).click();
        return this;
    }
    @Step("Clicking on Cart icon")
    public Page02_Home ClickOnCartIcon(){
        driver.findElement(cartIcon).click();
        return this;
    }
    @Step("Clicking on a specific product title to navigate to its details page")
    public Page02_Home ClickOnSpecificProductTitle() {
        driver.findElement(specificProduct).click();
        wait.waitForElementVisible(productDetails);
        return this;
    }
    //Assertion Methods
    public Page01_Login assertToLogout(){
        boolean isLoginPageDisplayed = driver.findElement(LoginPage).isDisplayed();
        DriverManager.hardAssertion().assertTrue(isLoginPageDisplayed, "Logout not successful - Login page not displayed");
        return new Page01_Login(driver);
    }
    public Page02_Home assertCartBadgeCount() {
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
}
