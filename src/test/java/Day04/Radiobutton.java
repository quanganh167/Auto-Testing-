package Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Driver;

public class Radiobutton {
    public static void main(String[]args){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.guru99.com/test/radio.html");
        WebElement radiobutton = driver.findElement(By.id("vfb-7-1"));
        radiobutton.click();
        String value = radiobutton.getAttribute("value");
        boolean isSelected = radiobutton.isEnabled();
        System.out.printf("Radio Button value selected" + value);
        System.out.printf("Radio Button is selected " + isSelected);
    }
}
