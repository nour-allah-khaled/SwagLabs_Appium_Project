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

    // Test data getters
    protected String getUserName() {
        return JsonReader.getJsonData("$.userName");
    }

    protected String getPassword() {
        return JsonReader.getJsonData("$.password");
    }

    protected String getEmptyUserName() {
        return JsonReader.getJsonData("$.emptyUsername");
    }

    protected String getEmptyPassword() {
        return JsonReader.getJsonData("$.emptyPassword");
    }

    protected String getInvalidUserName() {
        return JsonReader.getJsonData("$.invalidUsername");
    }

    protected String getInvalidPassword() {
        return JsonReader.getJsonData("$.invalidPassword");
    }
    protected String getProductOne() {
            return JsonReader.getJsonData("$Product.Product1");
    }
    protected String getProductTwo() {
        return JsonReader.getJsonData("$Product.Product2");
    }
    protected String getProductThree() {
        return JsonReader.getJsonData("$Product.Product3");
    }
    protected String getProductFour() {
        return JsonReader.getJsonData("$Product.Product4");
    }
    protected String getProductFive() {
        return JsonReader.getJsonData("$Product.Product5");
    }
    protected String getProductSix() {
        return JsonReader.getJsonData("$Product.Product6");
    }
}
