package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.pages.Page01_Login;
import com.swaglabs.pages.Page02_Home;
import com.swaglabs.pages.Page03_ProductDetails;
import org.testng.annotations.Test;

public class TC03_ProductDetails extends BaseClass
{
    @Test
    public void LoginToAddProduct()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
        new Page03_ProductDetails(DriverManager.getDriver()).clickAddToCart()
                .assertCartBadgeState("added").clickonCartIcon().assertToAddToCartPage();
    }
    @Test
    public void LoginToAddProductToCartPage()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne())
                .ClickOnSpecificProductTitle().assertToProductDetails();
        new Page03_ProductDetails(DriverManager.getDriver()).clickRemoveFromCart()
                .assertCartBadgeState("removed").clickAddToCart()
                .assertCartBadgeState("added").clickonCartIcon().assertToAddToCartPage();
    }
    @Test
    public void LoginToBackToHome()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
        new Page03_ProductDetails(DriverManager.getDriver()).clickAddToCart()
                .assertCartBadgeState("added").clickBackToHome().assertToHomePage();
    }
}
