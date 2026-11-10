package Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class DoubleClickTest {
    public static void main(String[]args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demo.guru99.com/test/simple_context_menu.html");
        WebElement button = driver.findElement(By.xpath("//button[text()='Double-Click Me To See Alert']"));
        Actions actions = new Actions(driver);
        actions.doubleClick(button).perform();
        ;
        Thread.sleep(5000);

        String alertText = driver.switchTo().alert().getText();
        System.out.printf("Alert text after double cleck: " + alertText);
        driver.switchTo().alert().accept();
        Thread.sleep(5000);
        driver.quit();
    }


    }

