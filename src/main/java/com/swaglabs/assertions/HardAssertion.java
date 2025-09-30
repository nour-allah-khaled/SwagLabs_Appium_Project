package com.swaglabs.assertions;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;

public class HardAssertion extends BaseAssertion{
    public HardAssertion(AndroidDriver driver) {
        super(driver);
    }

    @Override
    public void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    public void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Override
    public void assertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    @Override
    public void assertNotEquals(Object actual, Object expected, String message) {
        Assert.assertNotEquals(actual, expected, message);
    }
}
