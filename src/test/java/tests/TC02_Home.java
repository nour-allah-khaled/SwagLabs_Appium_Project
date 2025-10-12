package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.datareader.JsonReader;
import com.swaglabs.pages.Page01_Login;
import com.swaglabs.pages.Page02_Home;
import com.swaglabs.utils.logs.LogsManager;
import org.testng.annotations.Test;

public class TC02_Home extends BaseClass{
    @Test
    public void LoginToAddToCart(){
        // Your test steps here
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        LogsManager.info("User logged in successfully" + "by enter username: " + getUserName() + " and password: " + getPassword());
        new Page02_Home(DriverManager.getDriver()).filterProducts("Name (A to Z)").assertNamesSortedAToZ()
                .AddAllProductsToCart(getProductOne(), getProductTwo(), getProductThree(),getProductFour()).ClickOnCartIcon();
        LogsManager.info("All products added to the cart successfully and navigate to the cart page");
    }

}
