package com.swaglabs.utils.actions;

import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class MobileAndroidAction {
    private final AndroidDriver driver;
    public MobileAndroidAction(AndroidDriver driver) {
        this.driver = driver;
    }
    // Tap action
    public void tap(By locator) {
        WebElement element = driver.findElement(locator);
        Point point = element.getLocation();

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), point.x + 5, point.y + 5))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
        LogsManager.info("Tapped on element: " + locator);
    }

    // swipe down (to see content below)
     public void swipeDown() {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            Map<String, Object> params = new HashMap<>();
            params.put("left", 100);
            params.put("top", 500);
            params.put("width", 800);
            params.put("height", 1200);
            params.put("direction", "up"); // Changed from "down" to "up" to scroll down
            params.put("percent", 0.8);
            js.executeScript("mobile: swipeGesture", params);
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
