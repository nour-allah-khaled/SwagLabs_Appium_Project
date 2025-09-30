package com.swaglabs.utils.actions;

import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.time.Duration;


public class MobileAndroidAction {
    private final AndroidDriver driver;
    public MobileAndroidAction(AndroidDriver driver) {
        this.driver = driver;
    }
    // Tap action
    public void tap(By locator) {
        WebElement element = driver.findElement(locator);
        new TouchAction<>((PerformsTouchActions) driver)
                .tap(ElementOption.element(element))
                .perform();
        LogsManager.info("Tapped on element: " + locator);
    }

    // swipe down
    public void swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.3);
        new TouchAction<>((PerformsTouchActions) driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
        LogsManager.info("Swipe down performed");
    }
    // swipe up
    public void swipeUp() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.3);
        int endY = (int) (size.height * 0.8);
        new TouchAction<>((PerformsTouchActions) driver)
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(startX, endY))
                .release()
                .perform();
        LogsManager.info("Swipe up performed");
    }
}
