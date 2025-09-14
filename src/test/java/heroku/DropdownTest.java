package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DropdownTest {
    @Test
    void verifySelectedOptionSeccessfully(){
        WebDriver driver=new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/dropdown");
        /**
         * <select id="dropdown">
         *     <option value="" disabled="disabled" selected="selected">Please select an option</option>
         *     <option value="1">Option 1</option>
         *     <option value="2">Option 2</option>
         *   </select>
         */
        Select dropdown = new Select(driver.findElement(By.id("dropdown")));
        //dropdown.selectByValue("1");
        dropdown.selectByVisibleText("Option 1");
        //dropdown.selectByIndex(1);
        //Assert.assertTrue(driver.findElement(By.xpath("//option[@value='1']")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//option[.='Option 1']")).isSelected());

        driver.quit();
    }

    @Test
    void verrifySelectMultipleoptionsSuccessfully(){
        WebDriver driver=new ChromeDriver();
        driver.get("https://output.jsbin.com/osebed/2");

        Select dropdown = new Select(driver.findElement(By.id("fruits")));
        // Select Banana & Apple
        dropdown.selectByVisibleText("Banana");
        dropdown.selectByVisibleText("Apple");

        Assert.assertTrue(driver.findElement(By.xpath("//option[.='Banana']")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//option[.='Apple']")).isSelected());
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Orange']")).isSelected());
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Grape']")).isSelected());

        // Deselect Banana
        dropdown.deselectByVisibleText("Banana");
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Banana']")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//option[.='Apple']")).isSelected());
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Orange']")).isSelected());
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Grape']")).isSelected());

        //Deselect all
        dropdown.deselectAll();
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Banana']")).isSelected());
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Apple']")).isSelected());
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Orange']")).isSelected());
        Assert.assertFalse(driver.findElement(By.xpath("//option[.='Grape']")).isSelected());

        driver.quit();
    }
}
