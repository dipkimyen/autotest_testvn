package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TableTest {
    /**
     * <table id="table1" class="tablesorter">
     *     <thead>
     *       <tr>
     *         <th class="header"><span>Last Name</span></th>
     *         <th class="header"><span>First Name</span></th>
     *         <th class="header"><span>Email</span></th>
     *         <th class="header"><span>Due</span></th>
     *         <th class="header"><span>Web Site</span></th>
     *         <th class="header"><span>Action</span></th>
     *       </tr>
     *     </thead>
     *     <tbody>
     *       <tr>
     *         <td>Smith</td>
     *         <td>John</td>
     *         <td>jsmith@gmail.com</td>
     *         <td>$50.00</td>
     *         <td>http://www.jsmith.com</td>
     *         <td>
     *           <a href="#edit">edit</a>
     *           <a href="#delete">delete</a>
     *         </td>
     *       </tr>
     *       <tr>
     *         <td>Bach</td>
     *         <td>Frank</td>
     *         <td>fbach@yahoo.com</td>
     *         <td>$51.00</td>
     *         <td>http://www.frank.com</td>
     *         <td>
     *           <a href="#edit">edit</a>
     *           <a href="#delete">delete</a>
     *         </td>
     *       </tr>
     *       <tr>
     *         <td>Doe</td>
     *         <td>Jason</td>
     *         <td>jdoe@hotmail.com</td>
     *         <td>$100.00</td>
     *         <td>http://www.jdoe.com</td>
     *         <td>
     *           <a href="#edit">edit</a>
     *           <a href="#delete">delete</a>
     *         </td>
     *       </tr>
     *       <tr>
     *         <td>Conway</td>
     *         <td>Tim</td>
     *         <td>tconway@earthlink.net</td>
     *         <td>$50.00</td>
     *         <td>http://www.timconway.com</td>
     *         <td>
     *           <a href="#edit">edit</a>
     *           <a href="#delete">delete</a>
     *         </td>
     *       </tr>
     *     </tbody>
     *   </table>
     */
    @Test
    void verifyLargestDuePerson(){
        WebDriver driver=new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/tables");
        // Lấy toàn bộ ô (cell) trong bảng
        List<WebElement> cells = driver.findElements(By.xpath("//table[@id='table1']/tbody/tr/td")); //
        // Lấy toàn bộ giá trị trong cột "Due" (cột thứ 4)
        List<Double> dueValue= driver.findElements(By.xpath("//table[@id='table1']/tbody/tr/td[4]"))
                .stream()
                .map(WebElement::getText)
                // Bỏ dấu $ và parse sang số thực
                .map(d->Double.parseDouble(d.replace("$","")))
                .toList();
        // Tìm giá trị lớn nhất trong list đó
        Double maxDue = dueValue.stream().max(Comparator.naturalOrder()).get();
        // Tìm vị trí (dòng) của giá trị lớn nhất — +1 vì index trong list bắt đầu từ 0 còn dòng bắt đầu từ 1
        int maxDueRowIndex = dueValue.indexOf(maxDue)+1;
        // Tạo xpath động để lấy FirstName (cột 2) và LastName (cột 1) của dòng có giá trị Due lớn nhất
        String maxDuePersonFirstNameLocator = String.format("//table[@id='table1']/tbody/tr[%d]/td[2]",maxDueRowIndex);
        String maxDuePersonLastNameLocator = String.format("//table[@id='table1']/tbody/tr[%d]/td[1]",maxDueRowIndex);
        String firstName = driver.findElement(By.xpath(maxDuePersonFirstNameLocator)).getText();
        String lastName = driver.findElement(By.xpath(maxDuePersonLastNameLocator)).getText();

        Assert.assertEquals(String.format("%s %s", firstName, lastName), "Jason Doe");
        driver.quit();
    }

    @Test
    void verifySmallestDuePerson(){
        WebDriver driver=new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/tables");
        //1. Lấy toàn bộ giá trị Due
        List<Double> dueValues= driver.findElements(By.xpath("//table[@id='table1']/tbody/tr/td[4]"))
                .stream()
                .map(WebElement::getText)
                .map(d->Double.parseDouble(d.replace("$","")))
                .toList();
        //2. Tìm giá trị thấp nhất
        Double minDue = dueValues.stream().min(Comparator.naturalOrder()).get();
        //3. Tìm tất cả dòng có giá trị = minDue
        List<Integer> minDueRowIndexes = new ArrayList<>();
        for (int i = 0; i< dueValues.size(); i++){
            if (dueValues.get(i).equals(minDue)){
                minDueRowIndexes.add(i+1); // +1 vì xpath bắt đầu từ 1
            }
        }
        //4. Lấy tên (First+Last) của những dòng đó
        List<String> actualNames = new ArrayList<>();
        for (Integer rowIndex : minDueRowIndexes){ //Lặp qua từng chỉ số dòng (rowIndex) nằm trong danh sách minDueRowIndexes
            String firstName = driver.findElement(By.xpath(String.format("//table[@id='table1']/tbody/tr[%d]/td[2]",rowIndex))).getText();
            String lastName = driver.findElement(By.xpath(String.format("//table[@id='table1']/tbody/tr[%d]/td[1]",rowIndex))).getText();
            actualNames.add(firstName+" "+lastName);
        }
        //5. So sánh với danh sách mong đợi
        List<String> expectedNames = List.of("John Smith", "Tim Conway");
        Assert.assertEquals(actualNames.toArray(),expectedNames.toArray());

        driver.quit();
    }


}
