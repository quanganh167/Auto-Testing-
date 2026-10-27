package Day01;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class OpenSerenityChrome {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            String expectedUrl = "https://serenity-bdd.github.io/";
            driver.get(expectedUrl);
            String actualUrl = driver.getCurrentUrl();
            if (!expectedUrl.equals(actualUrl)) {
                throw new AssertionError(
                        "Sai URL. Mong đợi: " + expectedUrl
                                + " | Thực tế: " + actualUrl
                );
            }
            System.out.println("PASS: Chrome đã mở đúng URL.");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            driver.quit();
        }
    }
}