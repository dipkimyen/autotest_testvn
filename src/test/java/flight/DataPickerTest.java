package flight;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DataPickerTest {
    @Test
    void verifySelectDepartDateSuccessfully() {
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.vietnamairlines.com/vn/vi/home");

        driver.findElement(By.id("cookie-agree")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement departDateInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("roundtrip-date-depart"))
        );

        // Click bằng JS để tránh bị che khuất
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", departDateInput);

        LocalDate today = LocalDate.now();
        LocalDate next2Week = today.plusWeeks(2);
        String next2WeekStr = next2Week.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Retry chọn ngày để tránh stale element
        for (int i = 0; i < 2; i++) {
            try {
                List<WebElement> calendars = driver.findElements(By.className("ui-datepicker-calendar"));
                WebElement departMonthTable = (today.getMonthValue() < next2Week.getMonthValue())
                        ? calendars.get(1)
                        : calendars.get(0);

                for (WebElement dateCell : departMonthTable.findElements(By.xpath("./tbody/tr/td/a"))) {
                    if (dateCell.getText().equals(String.valueOf(next2Week.getDayOfMonth()))) {
                        dateCell.click();
                        break;
                    }
                }
                break;
            } catch (StaleElementReferenceException e) {
                if (i == 1) throw e;
            }
        }

        String departDate = driver.findElement(By.id("roundtrip-date-depart")).getAttribute("value");
        Assert.assertEquals(departDate, next2WeekStr);

        driver.quit();
    }
}
