package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

public class InfinitiScrollTest {
    @Test
    void scrolDown(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/infinite_scroll");

        Actions actions = new Actions(driver);
        for (int i = 0; i<10; i++){
            actions.scrollByAmount(0,500).perform();
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
            }
        }
        driver.quit();
    }

    @Test
    void viewMore(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://tiki.vn/tac-pham-kinh-dien-viet-nam/c67995");
        Actions actions = new Actions(driver);

        WebElement viewMoreBtn = driver.findElement(By.xpath("//*[@data-view-id=\"category_infinity_view.more\"]"));
        actions.scrollToElement(viewMoreBtn).perform();

        driver.quit();
    }
}
