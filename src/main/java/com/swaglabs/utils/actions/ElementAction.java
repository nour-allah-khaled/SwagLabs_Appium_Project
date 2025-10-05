package com.swaglabs.utils.actions;

import com.swaglabs.android.DriverManager;
import com.swaglabs.utils.WaitManager;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementAction {
    private final AndroidDriver driver;
    private WaitManager wait;
    MobileAndroidAction mobileAction;

    public ElementAction(AndroidDriver driver) {
        this.driver = driver;
        wait = new WaitManager(driver);
    }

    //clicking
    public void clicking(By locator) {
        wait.fluentWait().until(
                d -> {
                    try {
                        WebElement element = driver.findElement(locator);
                        element.click();
                        LogsManager.info("Clicked on element: " + locator.toString());
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }

    //typing
    public void sendkeys(By locator, String text) {
        try {
            WebElement element = wait.fluentWait().until(driver -> {
                WebElement el = driver.findElement(locator);
                return (el.isDisplayed() && el.isEnabled()) ? el : null;
            });
            wait.fluentWait().until(driver -> {
                try {
                    element.click();
                    LogsManager.info("Focused on element: " + locator);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            });
            wait.fluentWait().until(
                    driver -> element.getAttribute("focusable").equals("true"));
            element.clear();
            element.sendKeys(text);
            LogsManager.info("Typed text '" + text + "' into element: " + locator);
        } catch (Exception e) {
            LogsManager.error("Failed to type into element: " + locator, e.getMessage());
            throw new RuntimeException("Unable to type text into: " + locator, e);
        }
    }

    //getting text
    public String getText(By locator) {
        return wait.fluentWait().until(
                d -> {
                    try {
                        WebElement element = driver.findElement(locator);
                        String text = element.getText();
                        LogsManager.info("Got text '" + text + "' from element: " + locator);
                        return !text.isEmpty() ? text : null;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    //find element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //find elements
    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    //scroll to element
    public void scrollToElement(By locator) {
        int maxScrolls = 5;
        for (int i = 0; i < maxScrolls; i++) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    clicking(locator);
                    LogsManager.info("Found and clicked on element: " + locator);
                    return;
                }
            } catch (Exception ignored) {
            }
            DriverManager.mobileAction().swipeDown();
            LogsManager.info("Swipe down attempt " + (i + 1) + " to find: " + locator);
        }
        throw new RuntimeException("Element not found after scrolling: " + locator);
    }

    //Drag and Drop
    public void dragAndDrop(By sourceLocator, By targetLocator) {
        wait.fluentWait().until(
                d -> {
                    try {
                        WebElement source = driver.findElement(sourceLocator);
                        WebElement target = driver.findElement(targetLocator);
                        new TouchAction<>((PerformsTouchActions) driver).
                                longPress(ElementOption.element(source))
                                .moveTo(ElementOption.element(target))
                                .release()
                                .perform();
                        LogsManager.info("Dragged element " + sourceLocator + " and dropped to " + targetLocator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
    }
}