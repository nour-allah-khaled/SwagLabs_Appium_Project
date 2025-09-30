package com.swaglabs.utils;


import com.swaglabs.utils.logs.LogsManager;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;

public class AllureUtil {
    public static void cleanAllureResults() {
        FileUtils.deleteQuietly(new File("Test_out/allure-results"));
    }
    public static void cleanLogfile(File file)
    {
        try{
            org.apache.commons.io.FileUtils.deleteQuietly(file);
    }catch (Exception e){
            LogsManager.error("Failed to clean directory:" , file.getAbsolutePath(), e.getMessage());
        }
    }
    public static void attachLogs() {
        try {
            File logFile = new File(LogsManager.LOGS_PATH + "logs.log");
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
            }
        } catch (Exception e) {
            LogsManager.error("Error attaching logs", e.getMessage());
        }
    }
}
