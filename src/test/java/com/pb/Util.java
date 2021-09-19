package com.pb;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class Util {
    public static void takeScreenshot(WebDriver webDriver,String locator) {
        File src = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        File des = new File("./Results/Screenshots/"+" "+"_" + "sample.jpeg");
        try{
            FileUtils.copyFile(src,des);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
