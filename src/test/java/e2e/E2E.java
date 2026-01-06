package e2e;

import com.swaglabs.android.DriverManager;
import com.swaglabs.pages.*;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import tests.BaseClass;
@Epic("End-to-End Tests")
@Owner("Nour Allah Khaled")
@Severity(SeverityLevel.CRITICAL)
public class E2E extends BaseClass {
    @Description("Comprehensive end-to-end test scenarios covering complete user journeys")
    @Epic("End-to-End Tests")
   // Login > Home
    @Test
    public void LoginTest(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
    }
    // Login -> Home -> Product Details
    @Test
    public void LoginToProductDetails(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnSpecificProductTitle()
                .assertToProductDetails();
    }
    // Login -> Home (with sorting product Name) -> Add to Cart -> Checkout > Overview > Finish > Home
    @Test
    public void LoginToFinshWithSortingProductByName() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).filterProducts("Name (A to Z)").assertNamesSortedAToZ()
                .AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertToOverview();
        new Page06_Overview(DriverManager.getDriver()).clickFinish().assertToFinishPage();
        new Page07_Finish(DriverManager.getDriver()).clickBackHome().assertToHome();
    }
    // Login -> Home -> Remove Product From Cart -> Add to Cart -> Checkout > Overview > Finish > Home
    @Test
    public void LoginToRemoveProductFromCart() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver())
                .AddAllProductsToCart(getProductOne(), getProductTwo()).
                removeSelectedProductsFromCart(getProductOne()).assertCartBadgeCount().ClickOnCartIcon().assertToAddToCartPage();;
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertToOverview();
        new Page06_Overview(DriverManager.getDriver()).clickFinish().assertToFinishPage();
        new Page07_Finish(DriverManager.getDriver()).clickBackHome().assertToHome();
    }
    // Login -> Home (with sorting product Price) -> Add to Cart -> Checkout > Overview > Finish > Home
    @Test
    public void LoginToFinshWithSortingProductByPrice() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).filterProducts("Price (high to low)").assertPricesSortedHighToLow()
                .AddAllProductsToCart(getProductFour(),getProductOne()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertToOverview();
        new Page06_Overview(DriverManager.getDriver()).clickFinish().assertToFinishPage();
        new Page07_Finish(DriverManager.getDriver()).clickBackHome().assertToHome();
    }
    // Login -> Home -> Product Details -> Add to Cart -> Checkout > Overview > Finish > Home
    @Test
    public void LoginToFinshViaProductDetails() {
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
        new Page07_Finish(DriverManager.getDriver()).clickBackHome().assertToHome();
    }
    // Login -> Home -> Product Details -> Add/Remove to Cart -> Add to Cart Page
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
    // Login -> Home -> Product Details -> Add to Cart -> Checkout Page
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
    // Login -> Home -> Product Details -> Add to Cart -> Checkout -> Overview
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
    // Login -> Home (multiple products) -> Add to Cart -> Checkout -> Overview -> Finish -> Home
    @Test
    public void LoginToFinsh() {
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo()).ClickOnCartIcon().assertToAddToCartPage();
        new Page04_AddToCart(DriverManager.getDriver()).clickCheckout().assertToCheckoutPage();
        new Page05_CheckOut(DriverManager.getDriver()).enterFirstName(getFirstName())
                .enterLastName(getLastName()).enterPostalCode(getZipCode())
                .clickContinue().assertToOverview();
        new Page06_Overview(DriverManager.getDriver()).clickFinish().assertToFinishPage();
        new Page07_Finish(DriverManager.getDriver()).clickBackHome().assertToHome();
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
