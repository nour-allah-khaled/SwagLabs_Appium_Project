package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.pages.Page01_Login;
import com.swaglabs.pages.Page02_Home;
import com.swaglabs.pages.Page03_ProductDetails;
import com.swaglabs.pages.Page04_AddToCart;
import org.testng.annotations.Test;

public class TC04_AddToCart extends BaseClass
    {
    @Test
    public void LoginToAddProductToCheckoutPage()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
    }
    @Test
    public void LoginToContinueShoppingFromCartPage()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickContinueShopping().assertToHome();
    }
    @Test
    public void LoginToProceedToCheckoutFromCartPage()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
        new Page03_ProductDetails(DriverManager.getDriver()).clickAddToCart().assertCartBadgeState("added")
                .clickonCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
    }
}
