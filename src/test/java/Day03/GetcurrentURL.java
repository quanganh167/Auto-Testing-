package Day03;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Driver;

public class GetcurrentURL {
    static WebDriver driver = null;
    public static void main(String []args) throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        Thread.sleep(5000);
        driver.navigate().to("https://saucelabs.com/request-demo");
        driver.navigate().back();
        Thread.sleep(5000);
        driver.navigate().forward();
        Thread.sleep(5000);
        driver.navigate().refresh();
        Thread.sleep(5000);
        String url = driver.getCurrentUrl();
        System.out.printf("String url la:" + url);
        driver.close();//
        driver.quit(); // dong het
    }

}
