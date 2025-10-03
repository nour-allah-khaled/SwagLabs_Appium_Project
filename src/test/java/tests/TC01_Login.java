package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.datareader.JsonReader;
import com.swaglabs.pages.Page01_Login;
import org.testng.annotations.Test;

public class TC01_Login extends BaseClass {
    private static final String UserName = JsonReader.getJsonData("userName");
    private static final String Password = JsonReader.getJsonData("password");
    @Test
    public void LoginTest(){
        new Page01_Login(DriverManager.getDriver()).userName(UserName).
                passWord(Password).loginBtn();
    }
}
