package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.Objects;

public class BrokenImageTest {
    @Test
    void verifyBrokenImage(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/broken_images");
        driver.findElements(By.xpath("//div[@class='example']/img"))
                .forEach(image->{
                        System.out.println(image.getAttribute("src"));
                        if(Objects.equals(image.getDomProperty("naturalHeight"),"0") && Objects.equals(image.getDomProperty("naturalWidth"),"0"))
                        {
                            System.out.println("image not found");
                        }
                        else
                        {
                            System.out.println("image found");
                        }
                });

    }
}
