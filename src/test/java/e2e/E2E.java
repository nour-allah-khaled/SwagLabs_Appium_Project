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
        LogsManager.info("Login successful" + getUserName() +" " + getPassword());
        new Page02_Home(DriverManager.getDriver())
                .AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        LogsManager.info("All products added to the cart successfully and navigate to the cart page");
    }
    // Login ->Add to Cart (with sorting product Name) -> Checkout -> Payment -> Order Confirmation
    @Test
    public void endToEndTestFromLoginToFinshWithSortingProductByName() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        LogsManager.info("User logged in successfully" + "by enter username: " + getUserName() + " and password: " + getPassword());
        new Page02_Home(DriverManager.getDriver()).filterProducts("Name (A to Z)").assertNamesSortedAToZ()
                .AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        LogsManager.info("All products added to the cart successfully and navigate to the cart page");
    }
    // Login ->Add to Cart (with sorting product Price) -> Checkout -> Payment -> Order Confirmation
    @Test
    public void endToEndTestFromLoginToFinshWithSortingProductByPrice() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        LogsManager.info("User logged in successfully" + "by enter username: " + getUserName() + " and password: " + getPassword());
        new Page02_Home(DriverManager.getDriver()).filterProducts("Price (high to low)").assertPricesSortedHighToLow()
                .AddAllProductsToCart(getProductFour(),getProductOne()).ClickOnCartIcon().assertToAddToCartPage();
        LogsManager.info("The products sorted by Price from high to low successfully and all products added to the cart and navigate to the cart page");
    }
    // Login -> Home -> Product Details -> Checkout -> Payment -> Order Confirmation
    @Test
    public void endToEndTestFromLoginToFinshViaProductDetails() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        LogsManager.info("Login successful" + getUserName() +" " + getPassword());
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
        LogsManager.info("Navigated to product details page successfully");
    }
    // Login -> Home -> Logout
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
