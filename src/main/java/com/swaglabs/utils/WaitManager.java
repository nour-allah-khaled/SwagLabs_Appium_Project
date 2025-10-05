package com.swaglabs.utils;

import com.swaglabs.datareader.PropertyReader;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;

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
    private ArrayList<Class<? extends Exception>> Exceptionn() {
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<>();
        exceptions.add(org.openqa.selenium.NoSuchElementException.class);
        exceptions.add(org.openqa.selenium.StaleElementReferenceException.class);
        exceptions.add(org.openqa.selenium.ElementNotInteractableException.class);
        exceptions.add(org.openqa.selenium.ElementClickInterceptedException.class);
        return exceptions;
    }
}
