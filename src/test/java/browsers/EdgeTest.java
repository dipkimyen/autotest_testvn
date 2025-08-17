package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EdgeTest {
    @Test
    void openBrowserWithDefaultMode(){
        // Cách thủ công khi máy bạn bị chặn Selenium Manager
        System.setProperty("webdriver.edge.driver", "D:\\auto_test\\autotest_testvn\\edgedriver_win64\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }
}
