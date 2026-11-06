package BTVN_Day03;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookDemoAllCases {
    private static final String URL = "https://saucelabs.com/request-demo";

    private static final String WORK_EMAIL = "nguyencongquanganh16705@devpro.com";
    private static final String PHONE_NUMBER = "0395285535";

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static int passed = 0;
    private static int failed = 0;

    private static final By EMAIL =
            By.xpath("//input[@name='Business Email']");
    private static final By FIRST_NAME =
            By.xpath("//input[@name='First Name']");
    private static final By LAST_NAME =
            By.xpath("//input[@name='Last Name']");
    private static final By COMPANY =
            By.xpath("//input[@name='Company']");
    private static final By PHONE =
            By.xpath("//input[@name='Phone']");
    private static final By COUNTRY =
            By.xpath("//select[@name='Country']");
    private static final By USE_CASE =
            By.xpath("//select[@name='Use Case']");
    private static final By COMMENTS =
            By.xpath("//textarea[@name='Comments']");

    private static final By SUBMIT =
            By.xpath("//button[@type='submit']");

    private static final By THANK_YOU =
            By.xpath("//h1[contains(.,'Thanks for reaching out!')]");
    private static final By GMAIL_ERROR =
            By.xpath("//*[contains(text(),"
                    + "'Please enter a valid business email.')]");

    public static void main(String[] args) {
        driver = new ChromeDriver();

        try {
            driver.manage().window().maximize();
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            for (int test = 1; test <= 12; test++) {
                try {
                    openForm();
                    Thread.sleep(1500);
                    fillRequiredFields(test != 8);
                    check("TEST " + test, runTest(test));
                } catch (Exception e) {
                    check("TEST " + test, false);
                    System.out.println("  Lỗi: " + e.getMessage());
                }
            }
        } finally {
            driver.quit();
        }

        System.out.println(
                "TỔNG: PASS=" + passed + ", FAIL=" + failed + " / 12"
        );
    }

    private static boolean runTest(int test) throws InterruptedException {
        switch (test) {
            case 1:
                return submitAndCheckSuccess();

            case 2:
                driver.findElement(COMMENTS)
                        .sendKeys("Please demo cross-browser testing.");
                return submitAndCheckSuccess();

            case 3:
                return requiredFieldIsBlocked(EMAIL, true);

            case 4:
                return requiredFieldIsBlocked(FIRST_NAME, true);

            case 5:
                return requiredFieldIsBlocked(LAST_NAME, true);

            case 6:
                return requiredFieldIsBlocked(COMPANY, true);

            case 7:
                return requiredFieldIsBlocked(PHONE, true);

            case 8:
                return requiredFieldIsBlocked(COUNTRY, false);

            case 9:
                return invalidEmailIsBlocked("userexample.com");

            case 10:
                return invalidEmailIsBlocked("user@");

            case 11:
                return invalidEmailIsBlocked("   ");

            case 12:
                WebElement email = driver.findElement(EMAIL);
                email.clear();
                email.sendKeys("qa.demo@gmail.com");
                driver.findElement(COMPANY).click();

                boolean errorShown = visible(GMAIL_ERROR).isDisplayed();
                submit();
                Thread.sleep(2000);

                return errorShown && stillOnForm();

            default:
                throw new IllegalArgumentException(
                        "Không có TEST " + test
                );
        }
    }

    private static void openForm() {
        driver.get(URL);
    }

    private static WebElement visible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    private static void fillRequiredFields(boolean chooseCountry)
            throws InterruptedException {
        WebElement email = visible(EMAIL);
        email.sendKeys(WORK_EMAIL);
        driver.findElement(COMPANY).click();

        visible(FIRST_NAME).sendKeys("An");
        driver.findElement(COMPANY).click();

        visible(LAST_NAME).sendKeys("Nguyen");
        visible(COMPANY).sendKeys("QA Training Company");
        driver.findElement(LAST_NAME).click();

        visible(PHONE).sendKeys(PHONE_NUMBER);
        driver.findElement(COMPANY).click();

        if (chooseCountry) {
            Select country = new Select(visible(COUNTRY));
            country.selectByValue("Vietnam");
        }

        Select useCase = new Select(visible(USE_CASE));
        useCase.selectByVisibleText("Cross-Browser Testing");
        Thread.sleep(1000);
    }

    private static boolean submitAndCheckSuccess()
            throws InterruptedException {
        submit();
        wait.until(
                ExpectedConditions.urlContains("/thank-you-demo")
        );
        Thread.sleep(2000);

        return driver.findElement(THANK_YOU).isDisplayed();
    }

    private static boolean requiredFieldIsBlocked(
            By locator, boolean clear
    ) throws InterruptedException {
        WebElement field = driver.findElement(locator);

        if (clear) {
            field.clear();
        }

        String message = field.getAttribute("validationMessage");
        if (message == null || message.isBlank()) {
            return false;
        }

        submit();
        Thread.sleep(2000);
        return stillOnForm();
    }

    private static boolean invalidEmailIsBlocked(String value)
            throws InterruptedException {
        WebElement email = driver.findElement(EMAIL);
        email.clear();
        email.sendKeys(value);
        driver.findElement(COMPANY).click();

        String message = email.getAttribute("validationMessage");
        if (message == null || message.isBlank()) {
            return false;
        }

        submit();
        Thread.sleep(2000);
        return stillOnForm();
    }

    private static boolean stillOnForm() {
        return driver.getCurrentUrl().contains("/request-demo");
    }

    private static void submit() {
        driver.findElement(SUBMIT).click();
    }

    private static void check(String name, boolean result) {
        if (result) {
            passed++;
            System.out.println("PASS " + name);
        } else {
            failed++;
            System.out.println("FAIL " + name);
        }
    }
}