package e2e;

import com.swaglabs.android.DriverManager;
import com.swaglabs.pages.Page01_Login;
import com.swaglabs.pages.Page02_Home;
import com.swaglabs.utils.logs.LogsManager;
import org.testng.annotations.Test;
import tests.BaseClass;
public class E2E extends BaseClass {
   // Login ->Add to Cart (without sorting) -> Checkout -> Payment -> Order Confirmation
    @Test
    public void endToEndTestFromLoginToFinsh() {
        // End-to-end test steps would go here
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver())
                .AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
    }
    // Login ->Add to Cart (with sorting product Name) -> Checkout -> Payment -> Order Confirmation
    @Test
    public void endToEndTestFromLoginToFinshWithSortingProductByName() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).filterProducts("Name (A to Z)").assertNamesSortedAToZ()
                .AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
    }
    // Login ->Add to Cart (with sorting product Price) -> Checkout -> Payment -> Order Confirmation
    @Test
    public void endToEndTestFromLoginToFinshWithSortingProductByPrice() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).filterProducts("Price (high to low)").assertPricesSortedHighToLow()
                .AddAllProductsToCart(getProductFour(),getProductOne()).ClickOnCartIcon().assertToAddToCartPage();
    }
    // Login -> Home -> Product Details -> Checkout -> Payment -> Order Confirmation
    @Test
    public void endToEndTestFromLoginToFinshViaProductDetails() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
    }
    // Login -> Home -> Logout
    @Test
    public void LoginToLogout(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnKebabMenu().ClickOnLogoutBtn()
                .assertToLogout();
    }
}
