package tests;

import com.swaglabs.android.DriverManager;
import com.swaglabs.pages.*;
import org.testng.annotations.Test;

public class TC05_CheckOut extends BaseClass{
    @Test
    public void LoginToCheckout()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertToOverview();
    }
    @Test
    public void LoginToCheckoutFromProductDetailsToCartPage()
    {
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
    }
    @Test
    public void CheckoutwithEmptyFirstname()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertEmptyFirstnameMsg();
    }
    @Test
    public void CheckoutwithEmptyLastname()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterPostalCode(getZipCode()).clickContinue().assertEmptyLastnameMsg();
    }
    @Test
    public void CheckoutwithEmptyPostalCode()
    {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).clickContinue().assertEmptyPostalCodeMsg();
    }
    @Test
    public void LoginToCancelCheckout(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
        new Page03_ProductDetails(DriverManager.getDriver()).clickAddToCart().assertCartBadgeState("added")
                .clickonCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).clickCancel().assertToHome();
    }
}
