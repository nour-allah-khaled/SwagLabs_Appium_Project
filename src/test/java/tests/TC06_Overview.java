package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.pages.*;
import org.testng.annotations.Test;

public class TC06_Overview extends BaseClass{
    @Test
    public void LoginToOverviewToFinish(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertToOverview();
        new Page06_Overview(DriverManager.getDriver()).clickFinish().assertToFinishPage();
    }
    @Test
    public void LoginToProductDetailsToOverviewToFinish(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
        new Page03_ProductDetails(DriverManager.getDriver()).clickAddToCart().assertCartBadgeState("added")
                .clickonCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertToOverview();
        new Page06_Overview(DriverManager.getDriver()).clickFinish().assertToFinishPage();
    }
    @Test
    public void OverviewToCancel(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertToOverview();
        new Page06_Overview(DriverManager.getDriver()).clickCancel().assertToHome();
    }
}
