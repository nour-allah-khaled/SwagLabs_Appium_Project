package com.swaglabs.android;

import com.swaglabs.assertions.HardAssertion;
import com.swaglabs.assertions.SoftAssertion;
import com.swaglabs.datareader.PropertyReader;
import com.swaglabs.utils.actions.ElementAction;
import com.swaglabs.utils.actions.MobileAndroidAction;
import com.swaglabs.utils.logs.LogsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    private static AppiumDriverLocalService service;

    public static AndroidDriver getDriver() {
       if (driver.get() == null) {
              throw new IllegalStateException("Driver not initialized.");
       }
         return driver.get();
    }
    public static AppiumDriverLocalService getService() {
        if(service == null) {
            throw new IllegalStateException("Appium Service not initialized.");
        }
        return service;
    }
    public static void startDriver(String platformName){
        try {
            if ("local".equalsIgnoreCase(PropertyReader.getProperty("executionType"))) {
                startAppiumService();
            }
            AndroidDriver newDriver = new AndroidFactory().createDriver(platformName);
            driver.set(newDriver);
            LogsManager.info("Driver initialized for platform: " + platformName);
        }catch (Exception e){
            LogsManager.error("Error initializing driver: ", e.getMessage());
            throw new RuntimeException("Failed to initialize driver.", e);
        }
    }
    public static void startAppiumService(){
        try{
            Map<String,String> environment = new HashMap<>();
            environment.put("PATH",System.getenv("PATH"));
            service = new AppiumServiceBuilder().
            withAppiumJS(new File(getAppiumPath())).
                    withIPAddress(getServiceIpAddress())
                    .usingPort(getServicePort()).
                    withArgument(GeneralServerFlag.LOG_LEVEL,"warn").
                    withArgument(GeneralServerFlag.RELAXED_SECURITY)
                    .withEnvironment(environment).build();
            service.start();
            LogsManager.info("Appium service started.");
        }catch (Exception e){
            LogsManager.error("Error starting Appium service: ", e.getMessage());
            throw new RuntimeException("Failed to start Appium service.", e);
        }
    }
    private  static String getAppiumPath() {
        return "C:\\Users\\Dell\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    }
    private static String getServiceIpAddress() {
        String url = PropertyReader.getProperty("appiumServerUrl");
        return url.split(":")[1].substring(2);
    }
    private static int getServicePort() {
        String url = PropertyReader.getProperty("appiumServerUrl");
        return Integer.parseInt(url.split(":")[2]);
    }

    public static void quitDriverAndService() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                LogsManager.info("Driver quit successfully");
            } catch (Exception e) {
                LogsManager.error("Failed to quit driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }

        if (service != null && service.isRunning()) {
            try {
                service.stop();
                LogsManager.info("Appium server stopped successfully");
            } catch (Exception e) {
                LogsManager.error("Failed to stop Appium server: " + e.getMessage());
            }
        }
    }
    public static MobileAndroidAction mobileAction(){
        return new MobileAndroidAction(getDriver());
    }
    public static HardAssertion hardAssertion(){
        return new HardAssertion(getDriver());
    }
    public static SoftAssertion softAssertion(){
        return new SoftAssertion(getDriver());
    }
    public static ElementAction getElementAction() {
        return new ElementAction(getDriver());
    }
}
