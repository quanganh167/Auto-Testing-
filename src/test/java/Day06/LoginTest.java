package Day06;

import action.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest{
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String INVENTORY_URL = BASE_URL + "inventory.html";
    private static final String PAGE_TITLE = "Swag Labs";

    private WebDriver driver;
    private LoginPage loginPage;
    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        // Mở trang
    }
    @BeforeMethod
    public void beforeMethod(){
        driver.get(BASE_URL);
    }

    @Test
    public void testLoginFunctionality() {
        // Kiểm tra tiêu đề và URL trang login
        Assert.assertEquals(driver.getTitle(), PAGE_TITLE, "Page title is incorrect!");
        Assert.assertEquals(driver.getCurrentUrl(), BASE_URL, "Page URL is incorrect!");
        // Thực hiện login
        loginPage.login("standard_user", "secret_sauce");
        // Kiểm tra trang Inventory sau khi login thành công
        Assert.assertEquals(driver.getCurrentUrl(), INVENTORY_URL, "Login failed - Inventory page not loaded!");
        Assert.assertTrue(loginPage.isLogoDisplayed(), "App logo is not displayed on inventory page!");
        Assert.assertTrue(loginPage.getInventoryItemCount() > 0, "No products displayed on inventory page!");
    }
    @Test
    public void testLoginFunctionality11() {
        // Kiểm tra tiêu đề và URL trang login
        Assert.assertEquals(driver.getTitle(), PAGE_TITLE, "Page title is incorrect!");
        Assert.assertEquals(driver.getCurrentUrl(), BASE_URL, "Page URL is incorrect!");
        // Thực hiện login
        loginPage.login("standard_user", "secret_sauce");
        // Kiểm tra trang Inventory sau khi login thành công
        Assert.assertEquals(driver.getCurrentUrl(), INVENTORY_URL, "Login failed - Inventory page not loaded!");
        Assert.assertTrue(loginPage.isLogoDisplayed(), "App logo is not displayed on inventory page!");
        Assert.assertTrue(loginPage.getInventoryItemCount() > 0, "No products displayed on inventory page!");
    }
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
