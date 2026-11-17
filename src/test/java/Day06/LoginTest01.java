package Day06;

import action.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTest01 {
    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String INVENTORY_URL = BASE_URL + "inventory.html";
    private static final String PAGE_TITLE = "Swag Labs";
    private WebDriver driver;
    private LoginPage loginPage;


        private static final String VALID_USERNAME = "standard_user";
        private static final String VALID_PASSWORD = "secret_sauce";

        private static final String MSG_NOT_MATCH = "Username and password do not match";
        private static final String MSG_USERNAME_REQUIRED = "Username is required";
        private static final String MSG_PASSWORD_REQUIRED = "Password is required";


@BeforeClass
public void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    loginPage = new LoginPage(driver);
}
@BeforeMethod
    public void beforeMethod(){
        driver.get(BASE_URL);
    }
    @Test
    public void testLoginWithValidCredentials() {
        loginPage.login(VALID_USERNAME, VALID_PASSWORD);

        Assert.assertEquals(driver.getCurrentUrl(), INVENTORY_URL, "Login failed!");
        Assert.assertTrue(loginPage.isLogoDisplayed(), "Logo not displayed!");
        Assert.assertTrue(loginPage.getInventoryItemCount() > 0, "No products displayed!");
    }

    @Test
    public void testLoginWithInvalidUsername() {
        loginPage.login("invalid_user", VALID_PASSWORD);
        verifyErrorMessage(MSG_NOT_MATCH);
    }

    @Test
    public void testLoginWithInvalidPassword() {
        loginPage.login(VALID_USERNAME, "invalid_pass");
        verifyErrorMessage(MSG_NOT_MATCH);
    }

    @Test
    public void testLoginWithEmptyUsername() {
        loginPage.login("", VALID_PASSWORD);
        verifyErrorMessage(MSG_USERNAME_REQUIRED);
    }

    @Test
    public void testLoginWithEmptyPassword() {
        loginPage.login(VALID_USERNAME, "");
        verifyErrorMessage(MSG_PASSWORD_REQUIRED);
    }

    private void verifyErrorMessage(String expectedMessage) {
        Assert.assertTrue(loginPage.isErrorMessageDisplay(), "Error message not displayed!");
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedMessage),
                "Unexpected error message: " + loginPage.getErrorMessage());
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("===== After Each Test Method =====");
    }

    @AfterClass
    public void tearDownClass() {
        System.out.println("===== Test Class TearDown =====");
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        System.out.println("===== Test Suite Completed =====");
    }
}
