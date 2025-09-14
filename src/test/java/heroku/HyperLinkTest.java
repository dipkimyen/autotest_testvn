package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HyperLinkTest {
    @Test
    void verifyNavigateHyperLinkSuccessfully(){
        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/status_codes");

        String href = driver.findElement(By.linkText("200")).getAttribute("href");
        driver.findElement(By.linkText("200")).click();
        Assert.assertEquals(driver.getCurrentUrl(),href);
        driver.findElement(By.linkText("here")).click();// driver.navigate().back();

        href = driver.findElement(By.linkText("301")).getAttribute("href");
        driver.findElement(By.linkText("301")).click();
        Assert.assertEquals(driver.getCurrentUrl(),href);
        driver.findElement(By.linkText("here")).click();// driver.navigate().back();

        href = driver.findElement(By.linkText("404")).getAttribute("href");
        driver.findElement(By.linkText("404")).click();
        Assert.assertEquals(driver.getCurrentUrl(),href);
        driver.findElement(By.linkText("here")).click();// driver.navigate().back();

        href = driver.findElement(By.linkText("500")).getAttribute("href");
        driver.findElement(By.linkText("500")).click();
        Assert.assertEquals(driver.getCurrentUrl(),href);
        driver.findElement(By.linkText("here")).click();// driver.navigate().back();

        driver.quit();
    }
}
