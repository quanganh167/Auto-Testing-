package Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class RightClickTest {
    public static void main(String[] args) throws InterruptedException {
        // Khởi tạo WebDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Mở trang web
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");


        // Tìm button cần thực hiện right-click
        WebElement button = driver.findElement(By.xpath("//span[text()='right click me']"));

        // Tạo đối tượng Actions để thực hiện right-click
        Actions actions = new Actions(driver);

        // Thực hiện hành động right-click vào button
        actions.contextClick(button).perform();

        // Lấy danh sách các tùy chọn trong menu chuột phải
        WebElement option = driver.findElement(By.xpath("//li/span[text()='Edit']"));

        // Nhấp vào tùy chọn "Edit"
        option.click();
        Thread.sleep(3000);
        // Xử lý cảnh báo (alert) xuất hiện sau khi chọn "Edit"
        String alertText = driver.switchTo().alert().getText();
        System.out.println("Alert text after right click: " + alertText);

        // Đóng alert
        driver.switchTo().alert().accept();
    }
}

