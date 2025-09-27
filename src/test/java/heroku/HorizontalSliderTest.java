package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class HorizontalSliderTest {
    @Test
    void verifyAblesSlider(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/horizontal_slider");

        Actions actions = new Actions(driver);
        WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
        actions.clickAndHold(slider).moveByOffset(10,0).perform();
        driver.quit();
    }
}
