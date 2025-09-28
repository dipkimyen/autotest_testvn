package base;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import static common.Browser.*;

public class BaseTest {
    @Parameters({"browser"})
    @BeforeClass
    protected void setup(String browser) {
        launch(browser);
    }

    @AfterMethod
    protected void captureScreenshotWhenTestFail(ITestResult testResult){
        if(!testResult.isSuccess()){
            captureScreenshot(testResult.getMethod().getMethodName());
        }

    }

    @AfterClass
    protected void teardown(){
        quit();
    }
}
