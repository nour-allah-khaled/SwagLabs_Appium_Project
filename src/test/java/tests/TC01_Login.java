package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.pages.Page01_Login;
import org.testng.annotations.Test;

public class TC01_Login extends BaseClass {
    @Test
    public void LoginTest(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
    }
    @Test
    public void LoginwithEmptyUsername(){
        new Page01_Login(DriverManager.getDriver()).userName(getEmptyUserName()).passWord(getEmptyPassword())
                .loginBtn().assertEmptyMsg();
    }
    @Test
    public void LoginwithInvalidCredentials(){
        new Page01_Login(DriverManager.getDriver()).userName(getInvalidUserName()).passWord(getInvalidPassword())
                .loginBtn().assertInvalidMsg();
    }
}