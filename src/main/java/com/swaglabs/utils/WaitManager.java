package com.swaglabs.utils;

import com.swaglabs.datareader.PropertyReader;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WaitManager {
    private final AndroidDriver driver;
    public WaitManager(AndroidDriver driver) {
        this.driver = driver;
    }
    public FluentWait<AndroidDriver> fluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("androidAppWaitTime"))))
                .pollingEvery(Duration.ofMillis(100))
                .ignoreAll(Exceptionn());
    }
    public void waitForElementVisible(By locator) {
        LogsManager.info("Waiting for element to be visible: " + locator);
        fluentWait().until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return !elements.isEmpty() && elements.get(0).isDisplayed();
        });
        LogsManager.info("Element is now visible: " + locator);
    }
    public void waitForProductListToLoad(By locator) {
        LogsManager.info("Waiting for product list to load: " + locator);
        fluentWait().until(driver -> {
            List<WebElement> elements = driver.findElements(locator);
            return !elements.isEmpty() && elements.get(0).isDisplayed();
        });
        LogsManager.info("Product list loaded successfully: " + locator);
    }

    public void waitForElementClickable(By locator) {
        LogsManager.info("Waiting for element to be clickable: " + locator);
        fluentWait().until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                return element.isDisplayed() && element.isEnabled();
            } catch (Exception e) {
                return false;
            }
        });
        LogsManager.info("Element is now clickable: " + locator);
    }

    private ArrayList<Class<? extends Exception>> Exceptionn() {
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<>();
        exceptions.add(org.openqa.selenium.NoSuchElementException.class);
        exceptions.add(org.openqa.selenium.StaleElementReferenceException.class);
        exceptions.add(org.openqa.selenium.ElementNotInteractableException.class);
        exceptions.add(org.openqa.selenium.ElementClickInterceptedException.class);
        return exceptions;
    }

    public boolean waitForElementNotVisible(By locator) {
        LogsManager.info("Waiting for element to be invisible or removed: " + locator);
        try {
            WebElement badge = driver.findElement(locator);
            String text = badge.getText();
            return text == null || text.trim().isEmpty() || text.trim().equals("0");
        } catch (Exception e) {
            return true;
        }
    }
}
