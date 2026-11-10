package Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExplicitWaitExampleTest {
 public static void main(String[]args){
     WebDriver driver = new ChromeDriver();
     driver.manage().window().maximize();
     driver.get("https://saucelabs.com/request-demo");
     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
     WebElement firstNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email1")));
     firstNameField.sendKeys("student@exmple.com");
     driver.quit();
 }
}
