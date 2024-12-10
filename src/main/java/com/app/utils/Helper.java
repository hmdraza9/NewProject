package com.app.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;

public class Helper {

    private static final WebDriver driver = Utils.getDriver();
    static Logger log = LogManager.getFormatterLogger(Helper.class);
    static JavascriptExecutor js = (JavascriptExecutor) driver;


    public static void click(By by){
        try{
            System.out.println("driver.findElement(by).size(): "+driver.findElements(by).size());
            driver.findElement(by).click();
            Thread.sleep(2000);
        }catch(NoSuchSessionException nse){
            log.error(nse.getLocalizedMessage());
        }catch(Exception ex){
            log.error(ex.getStackTrace());
        }
    }

    public static void submit(By by){
        try{
            driver.findElement(by).submit();
        }catch(NoSuchSessionException nse){
            log.error(nse.getLocalizedMessage());
        }catch(Exception ex){
            log.error(ex.getStackTrace());
        }
    }

    public static void fillData(By by, String data){
        try{
            driver.findElement(by).sendKeys(data);
        }catch(NoSuchSessionException nse){
            log.error(nse.getLocalizedMessage());
        }catch(Exception ex){
            log.error(ex.getStackTrace());
        }
    }

    public static String getText(By by) {
        String fetchText = null;
        try {
            fetchText = driver.findElement(by).getText();
        } catch (NoSuchSessionException nse) {
            log.error(nse.getLocalizedMessage());
        } catch (Exception ex) {
            log.error(ex.getStackTrace());
        }
        return fetchText;
    }
}
