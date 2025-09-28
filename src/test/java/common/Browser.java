package common;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

// all static methods
public class Browser {
    private static WebDriver driver;
    public static WebDriverWait wait;
    public static void launch(String browser){
        switch (browser.toLowerCase()){
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                driver = new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOptions);
            }
            case "safari" -> {
                driver = new SafariDriver();
                break;
            }
            case "edge" -> {
                driver = new EdgeDriver();
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + browser);
        };
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    public static void visit(String url){
        driver.get(url);
    }
    public static String getCurrentUrl (){
        return driver.getCurrentUrl();
    }
    public static void quit(){
        if(driver!=null){
            driver.quit();
        }
    }
    public static WebDriver getDriver(){
        return driver;
    }
    public static void fill(By locator, String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(value);
    };
    public static void click(By locator){
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    public static String getText(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
    public static void captureScreenshot(String fileName){
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File(String.format("target/screenshot-%s-%s.png","context-menu",System.currentTimeMillis()));
        try{
            FileUtils.copyFile(srcFile, destFile);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
