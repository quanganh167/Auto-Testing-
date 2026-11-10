package Day04;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Checkbox {
    public static void main(String[]args){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://demo.guru99.com/test/radio.html");
        List<WebElement> checkboxes = new ArrayList<>();
        checkboxes.add(driver.findElement(By.id("vfb-6-1")));
        checkboxes.add(driver.findElement(By.id("vfb-6-0")));

        for (WebElement checkbox : checkboxes){
            checkbox.click();
            System.out.printf("Checkbox value selected: " + checkbox.getAttribute("value"));
            System.out.printf("Is selected? " + checkbox.isSelected());
        }
    }
}
