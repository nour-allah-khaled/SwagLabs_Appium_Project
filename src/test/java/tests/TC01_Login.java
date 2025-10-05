package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.datareader.JsonReader;
import com.swaglabs.pages.Page01_Login;
import org.testng.annotations.Test;

public class TC01_Login extends BaseClass {
    private final String UserName = JsonReader.getJsonData("userName");
    private final String password = JsonReader.getJsonData("password");
    @Test
    public void LoginTest(){
        // Get test data using the inherited jsonReader from BaseClass
        new Page01_Login(DriverManager.getDriver()).userName(UserName)
                .passWord(password).loginBtn().assertLogin();
    }
}