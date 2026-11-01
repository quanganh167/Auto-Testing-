package Day03;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;

public class SwitchTabsExample {
    public static void main(String[]args) throws InterruptedException {
    WebDriver driver = new ChromeDriver();
driver.manage().window().maximize();
driver.get("https://saucelabs.com/request-demo");
        ArrayList<String>tabs = new ArrayList<>(Collections.singleton(driver.getWindowHandle()));
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.youtube.com/?app=desktop&hl=vi");
Thread.sleep(3000);
        WebDriver window = driver.switchTo().window(tabs.get(0));
    }
}