package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DragDropTest {
    @Test
    void verifyMoveAToBSuccessfully(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        Actions actions = new Actions(driver);
        WebElement a = driver.findElement(By.id("column-a"));
        WebElement b = driver.findElement(By.id("column-b"));
        Assert.assertEquals(a.getText(), "A");
        Assert.assertEquals(b.getText(), "B");

        actions.dragAndDrop(a,b).perform();
        Assert.assertEquals(a.getText(), "B");
        Assert.assertEquals(b.getText(), "A");
        driver.quit();
    }
}
