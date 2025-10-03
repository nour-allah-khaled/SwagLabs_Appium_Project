package tests;

import com.swaglabs.android.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {
    @BeforeMethod
    public void setup() {
        String platform = System.getProperty("platformName");
        DriverManager.startDriver(platform);
    }
    @AfterMethod
    public void quit() {
        DriverManager.quitDriverAndService();
    }
}
