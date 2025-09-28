package heroku;

import base.BaseTest;
import heroku.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static common.Browser.*;

public class LoginTest extends BaseTest {
    LoginPage loginPage;
    @BeforeMethod
    void openPage(){
        loginPage = new LoginPage();
        loginPage.open();
    }

    @DataProvider
    Object[][] testData(){
        return new Object[][]{
                {"","SuperSecretPassword!", "https://the-internet.herokuapp.com/login","error","Your username is invalid!"},
                {"tomsmith","", "https://the-internet.herokuapp.com/login","error","Your password is invalid!"},
        };
    }

    @Test(dataProvider = "testData")
     void successfullWithValidCredentials(String username, String password, String expectedUrl, String expectedMessageType, String expectedMessageContent){
        loginPage.login(username,password);
        Assert.assertEquals(getCurrentUrl(), expectedUrl);

        String successMessage = loginPage.getFlashMessage(expectedMessageType);
        Assert.assertTrue(successMessage.contains(expectedMessageContent));
    }
}
