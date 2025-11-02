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
                .AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        LogsManager.info("The products sorted by Name from A to Z successfully and added to the cart and navigate to the cart page");
    }
    @Test
    public void LoginToSortProductPrice()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        LogsManager.info("User logged in successfully" + "by enter username: " + getUserName() + " and password: " + getPassword());
        new Page02_Home(DriverManager.getDriver()).filterProducts("Price (high to low)").assertPricesSortedHighToLow()
                .AddAllProductsToCart(getProductFour(),getProductOne()).ClickOnCartIcon().assertToAddToCartPage();
        LogsManager.info("The products sorted by Price from high to low successfully and added to the cart and navigate to the cart page");
    }
    @Test
    public void LoginToRemoveProductFromCart() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        LogsManager.info("Login successful" + " " + getUserName() +" " + getPassword());
        new Page02_Home(DriverManager.getDriver())
                .AddAllProductsToCart(getProductOne(), getProductTwo()).
               removeSelectedProductsFromCart(getProductOne()).assertCartBadgeCount();
        LogsManager.info("Product removed from the cart successfully and the cart badge number is updated");
    }
    @Test
    public void LoginToProductDetails(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        LogsManager.info("Login successful" + getUserName() +" " + getPassword());
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
        LogsManager.info("Navigated to product details page successfully");
    }
    @Test
    public void LoginToLogout(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        LogsManager.info("Login successful" + getUserName() +" " + getPassword());
        new Page02_Home(DriverManager.getDriver()).ClickOnKebabMenu().ClickOnLogoutBtn()
                .assertToLogout();
        LogsManager.info("User logged out successfully and navigated to login page");
    }
}
