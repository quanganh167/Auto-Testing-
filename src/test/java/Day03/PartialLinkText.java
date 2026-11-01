package Day03;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PartialLinkText {
    public static void main(String[]args){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();;
        driver.get("https://saucelabs.com/request-demo");
        WebElement element = driver.findElement(By.linkText("Terms of Service"));
        WebElement element1 = driver.findElement(By.partialLinkText("Terms of"));
        element.click();
    }
}
