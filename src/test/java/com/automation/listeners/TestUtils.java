package com.automation.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtils {

    public static void logToReport(String message) {
        try (FileWriter fw = new FileWriter("report.txt", true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void captureScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File dest = new File("screenshots/" + testName + "_" + timestamp + ".png");
            dest.getParentFile().mkdirs(); // Ensure directory exists
            try (InputStream in = new FileInputStream(src); OutputStream out = new FileOutputStream(dest)) {
                in.transferTo(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
