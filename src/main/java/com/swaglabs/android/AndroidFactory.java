package com.swaglabs.android;

import com.swaglabs.assertions.HardAssertion;
import com.swaglabs.assertions.SoftAssertion;
import com.swaglabs.datareader.PropertyReader;
import com.swaglabs.utils.actions.ElementAction;
import com.swaglabs.utils.actions.MobileAndroidAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static com.swaglabs.android.DriverManager.getDriver;

public class AndroidFactory {
    public static AndroidDriver createDriver(String platformName) throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(PropertyReader.getProperty("androidDeviceName"))
                .setApp(PropertyReader.getProperty("androidAppPath"))
                .setAppActivity(PropertyReader.getProperty("androidAppActivity"))
                .setAutomationName(PropertyReader.getProperty("androidAutomationName"))
                .setAppWaitDuration(Duration.ofSeconds(Integer.parseInt(PropertyReader.getProperty("androidAppWaitTime"))))
                .setAutoGrantPermissions(true)
                .setNoReset(false);
        if ("local".equalsIgnoreCase(PropertyReader.getProperty("executionType"))) {
            return new AndroidDriver(DriverManager.getService().getUrl(), options);
        } else {
            return new AndroidDriver(new URL(PropertyReader.getProperty("appiumServerUrl")), options);
        }
    }
}
