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
        new Page02_Home(DriverManager.getDriver()).filterProducts("Name (A to Z)").assertNamesSortedAToZ()
                .AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
    }
    @Test
    public void LoginToSortProductPrice()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).filterProducts("Price (high to low)").assertPricesSortedHighToLow()
                .AddAllProductsToCart(getProductFour(),getProductOne()).ClickOnCartIcon().assertToAddToCartPage();
    }
    @Test
    public void LoginToRemoveProductFromCart() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver())
                .AddAllProductsToCart(getProductOne(), getProductTwo()).
               removeSelectedProductsFromCart(getProductOne()).assertCartBadgeCount();
    }
    @Test
    public void LoginToProductDetails(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
    }
    @Test
    public void LoginToLogout(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnKebabMenu().ClickOnLogoutBtn()
                .assertToLogout();
    }
}
