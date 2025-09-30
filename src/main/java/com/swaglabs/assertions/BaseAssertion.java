package com.swaglabs.assertions;



import com.swaglabs.utils.WaitManager;
import com.swaglabs.utils.actions.ElementAction;
import io.appium.java_client.android.AndroidDriver;

public abstract class BaseAssertion {
    protected final AndroidDriver driver;
    protected final WaitManager wait;
    protected final ElementAction elemntAction;
    public BaseAssertion(AndroidDriver driver) {
        this.driver = driver;
        wait = new WaitManager(driver);
        elemntAction = new ElementAction(driver);
    }
    protected abstract void assertTrue(boolean condition, String message);
    protected abstract void assertFalse(boolean condition, String message);
    protected abstract void assertEquals(Object actual, Object expected, String message);
    protected abstract void assertNotEquals(Object actual, Object expected, String message);
}
