package browsers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v138.emulation.Emulation;
import org.openqa.selenium.devtools.v138.network.Network;
import org.openqa.selenium.devtools.v138.network.model.ConnectionType;
import org.openqa.selenium.devtools.v138.performance.Performance;
import org.openqa.selenium.devtools.v138.performance.model.Metric;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChromeTest {
    // 1. Test normal (Có hiển thị cửa sổ trình duyệt)
    @Test
    public void normalModeTest(){
        //Khởi taaoj ChromerDriver
        WebDriver driver = new ChromeDriver();
        //Mở trang Selenium
        driver.get("https://www.selenium.dev/");
        //Kiểm tra (Assert) tiêu đề
        Assert.assertEquals(driver.getTitle(),"Selenium");
        //Đóng trình duyệt
        driver.quit();
    }

    //2.Headless Mode (chạy ngầm, không hiện cửa sổ trình duyệt) (Usual)
    @Test
    public void headlessModeTest(){
        //Tạo đối tượng ChromeOptions để cấu hình Chrome
        ChromeOptions chromeOptions = new ChromeOptions();
        //Thêm tùy chọn chạy ở chế độ Headless (ẩn trình duyệt)
        chromeOptions.addArguments("--headless");

        //Khởi tạo ChromeDriver với cấu hình Headless
        WebDriver driver = new ChromeDriver(chromeOptions);
        //Mở web Selenium
        driver.get("https://www.selenium.dev/");
        //Kiểm tra (assert) title
        Assert.assertEquals(driver.getTitle(),"Selenium");
        //Đóng trình duyệt
        driver.quit();
    }

    //3.Mobile View Port
    @Test
    void mobileViewPortraitTest(){
        //Tạo Map lưu thông số kỹ thuật cho màn hình di động
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 344);
        deviceMetrics.put("height", 882);

        //Tạo Map cấu hình cho chế độ MobileEmulation
        Map<String, Object> mobileEmulation = new HashMap<>();
        //Gắn devideMetrics vừa định nghĩa vào Mobile Emulation
        mobileEmulation.put("deviceMetrics", deviceMetrics);

        //Khởi tạo ChromeOptions với MobileEmulation
        ChromeOptions chromeOptions =  new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);

        //Tạo ChromeDriver với cấu hình MobileEmulation
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }

    //4,5.Open Old Version
    @Test
    void openBrowserWithOldVersion(){
        //Khởi tạo ChromeOptions với Browser Version
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setBrowserVersion("138");

        //Tạo ChromeDriver với cấu hình Browser Version
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.selenium.dev/");
        Assert.assertEquals(driver.getTitle(),"Selenium");
        driver.quit();
    }

    //6.Fake Geo Location
    @Test
    void openBrowserWithFakeGeoLocation(){
        // Khởi tạo WebDriver cho Chrome
        WebDriver driver = new ChromeDriver();

        // Lấy DevTools để có thể điều khiển Chrome DevTools Protocol (CDP)
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession(); // tạo session mới để dùng DevTools

        // Giả lập vị trí địa lý: London (gần tọa độ 50.507351, -0.127758)
        devTools.send(Emulation.setGeolocationOverride(
                Optional.of(50.507351), // Vĩ độ (Latitude)
                Optional.of(-0.127758), // Kinh độ (Longitude)
                Optional.of(1),         // Accuracy (độ chính xác, mét)
                Optional.of(10),        // (các tham số bổ sung không bắt buộc, ví dụ heading, speed…)
                Optional.of(1),
                Optional.of(1),
                Optional.of(0)
        ));

        // Mở website demo geolocation
        driver.get("https://the-internet.herokuapp.com/geolocation");

        // Click vào button "Where am I?" để lấy vị trí
        driver.findElement(By.xpath("//button[.='Where am I?']")).click();

        // Kiểm tra giá trị lat và long hiển thị trên web có đúng như fake location không
        Assert.assertEquals(driver.findElement(By.cssSelector("#lat-value")).getText(),"50.507351");
        Assert.assertEquals(driver.findElement(By.cssSelector("#long-value")).getText(),"-0.127758");

        // Đóng browser sau khi test
        driver.quit();
    }

    //7.Capture Network Requests
    @Test
    void interceptionNetwork(){
        // Khởi tạo WebDriver cho Chrome
        WebDriver driver = new ChromeDriver();

        // Lấy DevTools để dùng Chrome DevTools Protocol
        DevTools devTool = ((HasDevTools) driver).getDevTools();
        devTool.createSession(); // Tạo session CDP

        // Bật tính năng Network tracking
        devTool.send(Network.enable(
                Optional.empty(), // maxTotalBufferSize (optional)
                Optional.empty(), // maxResourceBufferSize (optional)
                Optional.empty(), // maxPostDataSize (optional)
                Optional.empty()  // additional params
        ));

        // Lắng nghe sự kiện khi request được gửi đi
        devTool.addListener(Network.requestWillBeSent(), requestSent -> {
            System.out.println("Request URL => " + requestSent.getRequest().getUrl());
            System.out.println("Request Method => " + requestSent.getRequest().getMethod());
            System.out.println("Request Headers => " + requestSent.getRequest().getHeaders().toString());
            System.out.println("----------------------------------------------------------------------");
        });

        // Lắng nghe sự kiện khi response được nhận về
        devTool.addListener(Network.responseReceived(), responseReceived -> {
            System.out.println("Response URL => " + responseReceived.getResponse().getUrl());
            System.out.println("Response Status => " + responseReceived.getResponse().getStatus());
            System.out.println("Response Headers => " + responseReceived.getResponse().getHeaders().toString());
            System.out.println("Response MIME Type => " + responseReceived.getResponse().getMimeType().toString());
            System.out.println("------------------------------------------------------------------------");
        });

        // Mở trang Selenium để trigger request & response
        driver.get("https://www.selenium.dev/");

        // Đóng browser
        driver.quit();
    }

    //8. Capture Page Performance Metrics
        @Test
        void openSeleniumHomePageAndCCapturePerformanceMetrics(){
            // Khởi tạo ChromeDriver
            ChromeDriver driver = new ChromeDriver();

            // Lấy DevTools để giao tiếp với Chrome DevTools Protocol
            DevTools devTools = driver.getDevTools();
            devTools.createSession(); // Tạo session CDP

            // Bật tính năng Performance tracking
            devTools.send(Performance.enable(Optional.empty()));

            // Lấy danh sách metric từ DevTools
            List<Metric> metricList = devTools.send(Performance.getMetrics());

            // Mở website Selenium
            driver.get("https://www.selenium.dev");

            // Xác minh tiêu đề trang
            Assert.assertEquals(driver.getTitle(), "Selenium");

            // Đóng browser
            driver.quit();

        for (Metric m : metricList){
            System.out.println(m.getName() + "=" + m.getValue());
        }
    }



    //9. Simulate Mobile Network Condition (3G/LTE)
    @Test
    void simulate3GNetworkCondition(){
        ChromeDriver driver = new ChromeDriver();
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        // Bật tính năng giám sát Network trong CDP
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        // Giả lập điều kiện mạng (ở đây là 2G/3G/4G)
        devTools.send(Network.emulateNetworkConditions(
                false,                  // offline = false (không phải chế độ offline)
                100,                    // latency = 100ms (độ trễ)
                75000,                  // downloadThroughput = 75kb/s
                25000,                  // uploadThroughput = 25kb/s
                Optional.of(ConnectionType.CELLULAR2G), // Loại mạng: 2G, 3G, 4G, WiFi...
                Optional.of(0),         // packet loss (default = 0)
                Optional.of(0),         // jitter (dao động độ trễ)
                Optional.of(false)      // không simulate unlimited data
        ));

        driver.get("https://selenium.dev");
        driver.quit();
    }
}
