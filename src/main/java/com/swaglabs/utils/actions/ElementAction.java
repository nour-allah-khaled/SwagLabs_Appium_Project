package com.swaglabs.utils.actions;

import com.swaglabs.utils.WaitManager;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.swaglabs.utils.actions.MobileAndroidAction;
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
        wait.fluentWait().until(
                d -> {
                    try {
                        WebElement element = driver.findElement(locator);
                        element.clear();
                        element.sendKeys(text);
                        LogsManager.info("Typed text '" + text + "' into element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
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
    public WebElement scrollToElement(By locator) {
        int maxScrolls = 5;
        for (int i = 0; i < maxScrolls; i++) {
            try {
                WebElement element = driver.findElement(locator);
                if (element.isDisplayed()) {
                    LogsManager.info("Scrolled to element: " + locator);
                    return element;
                }
            } catch (Exception ignored) {
            }
            mobileAction = new MobileAndroidAction(driver);
            mobileAction.swipeDown();
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