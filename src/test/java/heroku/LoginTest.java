package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.openqa.selenium.By.className;

public class LoginTest {
    @Test
    void successfullWithValidCredentials() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
//        driver.get("https://the-internet.herokuapp.com/login");
//
//        driver.findElement(By.id("username")).sendKeys("tomsmith");
//        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
//        driver.findElement(By.cssSelector("button[type=submit]")).click();
//        Thread.sleep(2000);
//        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/secure");
//
//        String successMessage = driver.findElement(By.className("success")).getText();
//        Assert.assertTrue(successMessage.contains("You logged into a secure area!"));
//
//        driver.quit();

        driver.get("https://arobid.stg.arobid.site/vi/authentication/sign-in");

        driver.findElement(By.cssSelector("input.mantine-Input-input.mantine-TextInput-input")).sendKeys("yennguyen1@yopmail.com");
        driver.findElement(By.cssSelector("input.mantine-PasswordInput-innerInput")).sendKeys("Pass@123");
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        Thread.sleep(2000);
        Assert.assertEquals(driver.getCurrentUrl(), "https://arobid.stg.arobid.site/vi");

        driver.quit();
    }
}
