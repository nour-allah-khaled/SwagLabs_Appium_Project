package com.swaglabs.utils;

public class TimeManager {
    public static String getTimestamp() {
        return new java.text.SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new java.util.Date());
    }
}
