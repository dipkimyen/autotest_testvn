package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CheckboxesTest {
    @Test
    public void verifySelectedCheckboxesSuccessfully() {
        //1. Mở trình duyệt
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        //2. Tìm checkbox
        WebElement checkbox1 =  driver.findElement(By.cssSelector("form#checkboxes input:nth-child(1)"));
        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));
        //3. Click chon checkbox1
        if (!checkbox1.isSelected()) checkbox1.click();
        if (!checkbox2.isSelected()) checkbox2.click();
        //4. Kiểm tra
        Assert.assertTrue(checkbox1.isSelected());
        Assert.assertTrue(checkbox2.isSelected());
        driver.quit();
    }

    @Test
    void verifyDeselectedCheckboxesSuccessfully() {
        //1. Mở trình duyệt
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        //2. Tìm checkbox
        WebElement checkbox1 =  driver.findElement(By.cssSelector("form#checkboxes input:nth-child(1)"));
        WebElement checkbox2 = driver.findElement(By.xpath("//*[@id=\"checkboxes\"]/input[2]"));
        //3. Click chon checkbox1
        if (checkbox1.isSelected()) checkbox1.click();
        if (checkbox2.isSelected()) checkbox2.click();
        //4. Kiểm tra
        Assert.assertFalse(checkbox1.isSelected());
        Assert.assertFalse(checkbox2.isSelected());
        driver.quit();
    }
}
