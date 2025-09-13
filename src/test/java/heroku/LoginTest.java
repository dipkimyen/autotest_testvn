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
        driver.get("https://the-internet.herokuapp.com/login");

        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type=submit]")).click();
        Thread.sleep(2000);
        Assert.assertEquals(driver.getCurrentUrl(), "https://the-internet.herokuapp.com/secure");

        String successMessage = driver.findElement(By.className("success")).getText();
        Assert.assertTrue(successMessage.contains("You logged into a secure area!"));

        driver.quit();
    }

    @Test
    void LoginSuccessfully() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

        driver.quit();
    }
}
