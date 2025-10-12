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
        new Page02_Home(DriverManager.getDriver()).AddAllProductsToCart(getProductOne(), getProductTwo(), getProductThree(),getProductFour()).ClickOnCartIcon();
        LogsManager.info("All products added to the cart successfully and navigate to the cart page");
    }
    // Login ->Add to Cart (with sorting) -> Checkout -> Payment -> Order Confirmation
}
