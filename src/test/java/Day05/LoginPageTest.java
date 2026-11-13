package Day05;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ExcelUtils;

import java.util.List;
import java.util.Map;

public class LoginPageTest {
    private static final String EXCEL_PATH = "DataLogin.xlsx";
    private static final String SHEET_NAME = "Sheet1";
    private static final String LOGIN_URL = "https://www.saucedemo.com/";

    public static void main(String[] args) {

        List<Map<String, String>> testData = ExcelUtils.readExcelData(EXCEL_PATH, SHEET_NAME);

        WebDriver driver;

        for (Map<String, String> row : testData) {

            driver = new ChromeDriver();

            String user = row.get("user");
            String pass = row.get("password");

            System.out.println("Đang thử đăng nhập với User: " + user);

            driver.get(LOGIN_URL);

            WebElement userField = driver.findElement(By.id("user-name"));
            WebElement passField = driver.findElement(By.id("password"));
            WebElement loginBtn = driver.findElement(By.id("login-button"));

            userField.sendKeys(user);
            passField.sendKeys(pass);

            loginBtn.click();

            driver.quit();
        }
    }


}
