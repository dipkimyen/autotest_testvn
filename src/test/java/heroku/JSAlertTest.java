package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JSAlertTest {
    @Test
    void acceptJSAlertPopup(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//button[.='Click for JS Alert']")).click();
        driver.switchTo().alert().accept();
        Assert.assertTrue(driver.findElement(By.id("result")).getText().contains("You successfully clicked an alert"));
        driver.quit();
    }

    @Test
    void acceptJSConfirmPopup(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//button[.='Click for JS Confirm']")).click();
        driver.switchTo().alert().accept();
        Assert.assertTrue(driver.findElement(By.id("result")).getText().contains("You clicked: Ok"));
        driver.quit();
    }

    @Test
    void cancelJSConfirmPopup(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//button[.='Click for JS Confirm']")).click();
        driver.switchTo().alert().dismiss();
        Assert.assertTrue(driver.findElement(By.id("result")).getText().contains("You clicked: Cancel"));
        driver.quit();
    }

    @Test
    void fillJSPromptPopup(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//button[.='Click for JS Prompt']")).click();
        driver.switchTo().alert().sendKeys("hello");
        driver.switchTo().alert().accept();
        Assert.assertTrue(driver.findElement(By.id("result")).getText().contains("You entered: hello"));
        driver.quit();
    }
}
