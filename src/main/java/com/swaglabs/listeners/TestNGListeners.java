package com.swaglabs.listeners;
import com.swaglabs.android.DriverManager;
import com.swaglabs.assertions.SoftAssertion;
import com.swaglabs.datareader.PropertyReader;
import com.swaglabs.media.ScreenShotMedia;
import com.swaglabs.utils.AllureUtil;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.*;
import java.io.File;
import static com.swaglabs.utils.logs.LogsManager.LOGS_PATH;

public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestListener {
    public void onExecutionStart() {
        AllureUtil.cleanAllureResults();
        LogsManager.info("Cleaned Allure results");
        AllureUtil.cleanLogfile(new File(LOGS_PATH));
        LogsManager.info("Cleaned Logfile");
        LogsManager.info("Starting execution");
        PropertyReader.loadProperties();
        LogsManager.info("Loaded properties");
    }
    public void onExecutionFinish() {
        LogsManager.info("Test Execution Finished");
        try {
            Runtime.getRuntime().exec("cmd.exe /c start generate-allureReport.bat");
        }
        catch (Exception e) {
            LogsManager.error("Error while generating Allure report: " + e.getMessage());
        }
    }
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        AndroidDriver driver = null;
        if (method.isTestMethod()) {
              driver = DriverManager.getDriver(); //driver.getDriver();
                ScreenShotMedia.Screen_shot(driver, "ScreenShot-" + testResult.getName());
                SoftAssertion.assertAll();
                if (testResult.getStatus() == ITestResult.FAILURE) {
                    LogsManager.info("Test Execution Failed");
                    ScreenShotMedia.Screen_shot(driver, "failed-" + testResult.getName());
                }
                switch (testResult.getStatus()) {
                    case ITestResult.SUCCESS -> {
                        LogsManager.info("Test " + testResult.getName() + " passed.");
                    }
                    case ITestResult.FAILURE -> {
                        LogsManager.error("Test " + testResult.getName() + " failed.");
                    }
                    case ITestResult.SKIP -> {
                        LogsManager.warn("Test " + testResult.getName() + " skipped.");
                    }
                }
                AllureUtil.attachLogs();
        }
    }
    public void onTestStart(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " started");
    }
    public void onTestSuccess(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " passed");
    }
    public void onTestSkipped(ITestResult result) {
        LogsManager.info("Test Case " + result.getName() + " skipped");

    }
}