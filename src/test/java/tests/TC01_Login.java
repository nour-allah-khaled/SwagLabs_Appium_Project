package tests;

import com.swaglabs.pages.Page01_Login;
import org.testng.annotations.Test;

public class TC01_Login extends BaseClass {
    @Test
    public void LoginTest(){
        new Page01_Login(driver).userName("standard_user");
    }
}
