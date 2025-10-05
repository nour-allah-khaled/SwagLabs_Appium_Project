package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.datareader.JsonReader;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {
    protected JsonReader jsonReader;
    
    @BeforeMethod
    public void setup() {
        String platform = System.getProperty("platformName");
        DriverManager.startDriver(platform);
        // Initialize JsonReader for all test classes
        jsonReader = new JsonReader("Test_data");
    }
    
    @AfterMethod
    public void quit() {
        DriverManager.quitDriverAndService();
    }
}
