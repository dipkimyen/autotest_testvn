package browsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v138.emulation.Emulation;
import org.openqa.selenium.devtools.v138.network.Network;
import org.openqa.selenium.devtools.v138.network.model.ConnectionType;
import org.openqa.selenium.devtools.v138.performance.Performance;
import org.openqa.selenium.devtools.v138.performance.model.Metric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChromeTest {
    // 1. Test normal (Usual)
    @Test
    public void normalModeTest(){
        //Open browser
        WebDriver driver = new ChromeDriver();
        //Navigate to website
        driver.get("https://www.selenium.dev/");
        //Verification
        Assert.assertEquals(driver.getTitle(),"Selenium");
        //Close browser
        driver.quit();
    }

    //2.Headless Mode (Run in background at gitHub) (Usual)
    @Test
    public void headlessModeTest(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }

    //3.Mobile View Port
    @Test
    void mobileViewPortraitTest(){
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 344);
        deviceMetrics.put("height", 882);
        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);

        ChromeOptions chromeOptions =  new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }

    //4,5.Open Old Version
    @Test
    void openBrowserWithOldVersion(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBrowserVersion("138");

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }

    //6.Fake Geo Location
    @Test
    void openBrowserWithFakeGeoLocation(){
        WebDriver driver = new ChromeDriver();
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        // Mountain view
        devTools.send(Emulation.setGeolocationOverride(
                Optional.of(50.507351),
                Optional.of(-0.127758),
                Optional.of(1),
                Optional.of(10),
                Optional.of(1),
                Optional.of(1),
                Optional.of(0)
        ));
        driver.get("https://the-internet.herokuapp.com/geolocation");
        driver.findElement(By.xpath("//button[.='Where am I?']")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector("#lat-value")).getText(),"50.507351");
        Assert.assertEquals(driver.findElement(By.cssSelector("#long-value")).getText(),"-0.127758");

        driver.quit();
    }

    //7.Capture Network Requests
    @Test
    void interceptionNetwork(){
        WebDriver driver = new ChromeDriver();
        DevTools devTool = ((HasDevTools) driver).getDevTools();

        devTool.createSession();
        devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(),Optional.empty()));

        devTool.addListener(Network.requestWillBeSent(), requestSent -> {
            System.out.println("Request URL => " + requestSent.getRequest().getUrl());
            System.out.println("Request Method => " + requestSent.getRequest().getMethod());
            System.out.println("Request Headers => " + requestSent.getRequest().getHeaders().toString());
            System.out.println("------------------------------------------------------");
        });

        devTool.addListener(Network.responseReceived(), responseReceived -> {
            System.out.println("Response Url => " + responseReceived.getResponse().getUrl());
            System.out.println("Response Status => " + responseReceived.getResponse().getStatus());
            System.out.println("Response Headers => " + responseReceived.getResponse().getHeaders().toString());
            System.out.println("Response MIME Type => " + responseReceived.getResponse().getMimeType().toString());
            System.out.println("------------------------------------------------------");
        });

        driver.get("https://selenium.dev");
        driver.quit();
    }

    //8. Capture Page Performance Metrics
    @Test
    void openSeleniumHomePageAndCapturePerformanceMetrics(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Performance.enable(Optional.empty()));
        List<Metric> metricList = devTools.send(Performance.getMetrics());
        driver.get("https://selenium.dev");

        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();

        for(Metric m : metricList) {
            System.out.println(m.getName() + " = " + m.getValue());
        }
        driver.quit();
    }

    //9. Simulate Mobile Network Condition (3G/LTE)
    @Test
    void simulate3GNetworkCondition(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        // Enable Network emulation
        devTools.send(Network.enable(Optional.of(100000000), Optional.empty(), Optional.empty(),Optional.empty()));

        // Set network conditions to emulate 3G or 4G
        devTools.send(Network.emulateNetworkConditions(
                false,
                100,
                75000,
                25000,
                Optional.of(ConnectionType.CELLULAR2G),
                Optional.of(0),
                Optional.of(0),
                Optional.of(false)
        ));
        driver.get("https://selenium.dev");
        driver.quit();
    }
}
