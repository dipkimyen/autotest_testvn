package heroku.pages;

import org.openqa.selenium.By;
import static common.Browser.*;

public class LoginPage {
    public void  open(){
        visit("https://the-internet.herokuapp.com/login");
    }

    public void login(String userName, String password){
        fill(By.id("username"),userName);
        fill(By.id("password"),password);
        click(By.xpath("//button[@type='submit']"));
    }

    public String getFlashMessage(String messageType){
        return getText(By.className(messageType));
    }
}
