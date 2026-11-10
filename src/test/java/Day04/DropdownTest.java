package Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DropdownTest {
    public static void main(String[]args){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");
        WebElement interestDropdown = driver.findElement(By.id("use case"));
        Select select = new Select(interestDropdown);
        select.selectByIndex(6);
        select.selectByValue("Visual Testing");
        select.selectByVisibleText("Visual Testing");
    }
}
