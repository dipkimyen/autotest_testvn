package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ChromeTest {
    // Test normal
//    @Test
//    public void normalModeTest(){
//        //Open browser
//        WebDriver driver = new ChromeDriver();
//        //Navigate to website
//        driver.get("https://www.selenium.dev/");
//        //Verification
//        Assert.assertEquals(driver.getTitle(),"Selenium");
//        //Close browser
//        driver.quit();
//    }

    //Test run in background at github
    @Test
    public void headlessModeTest(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }
}
