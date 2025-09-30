package com.swaglabs.media;


import com.swaglabs.utils.TimeManager;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;

public class ScreenShotMedia {
    public static final String SCREENSHOT_PATH = "Test_out/ScreenShots/";
    // create method to take full page screenshot
    public static void Screen_shot(AndroidDriver driver, String screenshotName) {
        try {
            if (driver == null) {
                System.out.println("Driver is null, cannot take screenshot.");
                return;
            }
            File screenshotDir = new File(SCREENSHOT_PATH);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotFile = new File(SCREENSHOT_PATH + screenshotName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshot, screenshotFile);
            Allure.addAttachment(screenshotName, Files.newInputStream(screenshotFile.toPath()));
            LogsManager.info("Screenshot taken: " + screenshotFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Could not take screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
