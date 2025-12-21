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
    // Login -> Home (multiple products) -> Add to Cart -> Checkout -> Overview -> Finish -> Home
    @Test
    public void endToEndTestFromLoginToFinsh() {
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
    // Login -> Home (with sorting product Name) -> Add to Cart -> Checkout > Overview > Finish > Home
    @Test
    public void endToEndTestFromLoginToFinshWithSortingProductByName() {
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
    // Login -> Home (with sorting product Price) -> Add to Cart -> Checkout > Overview > Finish > Home
    @Test
    public void endToEndTestFromLoginToFinshWithSortingProductByPrice() {
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
    public void endToEndTestFromLoginToFinshViaProductDetails() {
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
    // Login -> Home -> Logout
    @Test
    public void LoginToLogout(){
        new Page01_Login(DriverManager.getDriver()).userName(getUserName())
                .passWord(getPassword()).loginBtn().assertLogin();
        new Page02_Home(DriverManager.getDriver()).ClickOnKebabMenu().ClickOnLogoutBtn()
                .assertToLogout();
    }
}
