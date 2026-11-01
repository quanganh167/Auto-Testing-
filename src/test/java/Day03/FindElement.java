package Day03;

//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class FindElement {
    public static void main(String[]args){
      //  WebDriver driver = new ChromeDriver();
      //  driver.manage().window().maximize();
      //  driver.get("https://saucelabs.com/request-demo");
     //   WebElement element = driver.findElement(By.xpath("//h3[contains(text(),'Request')]"));
     //   System.out.println("get text: " +element.getText());
      //  WebElement element1 = driver.findElement(By.xpath("//span[contains(text(),'Test')]"));
      //  System.out.println("get text: " +element1.getText());
     //   WebElement element2 = driver.findElement(By.xpath("//span[contains(text(),'Ship')]"));
      //  System.out.println("get text: " + element2.getText());
     //   WebElement element3 = driver.findElement(By.xpath("//p[contains(text(),'get an email')]"));
      //  System.out.println("get text: " +element3.getText());
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://saucelabs.com/request-demo");
        WebElement element = driver.findElement(By.name("Company"));
        element.sendKeys("MB");
        WebElement element1 = driver.findElement(By.className("form-button"));
        element1.click();
    }
}
