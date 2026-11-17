package action;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import static ui.BookDemoUI.*;


public class BookDemoPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public BookDemoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void openForm() {
        driver.get(URL);
        visible(EMAIL);
        wait.until(d -> new Select(d.findElement(USE_CASE)).getOptions().size() >= 2);
    }

    private WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void enter(By locator, String text) {
        WebElement element = visible(locator);
        element.clear();
        if (!text.isEmpty()) element.sendKeys(text);
        element.sendKeys(Keys.TAB);
    }

    public void enterEmail(String text) { enter(EMAIL, text); }
    public void enterFirstName(String text) { enter(FIRST_NAME, text); }
    public void enterLastName(String text) { enter(LAST_NAME, text); }
    public void enterCompany(String text) { enter(COMPANY, text); }
    public void enterPhone(String text) { enter(PHONE, text); }
    public void enterComments(String text) { enter(COMMENTS, text); }

    // Tương tự LoginPage.login(): ghép các thao tác nhập thành một luồng dùng lại.
    public void fillForm(String email, String firstName, String lastName, String company,
                         String phone, String country, String state, String useCase, String comments) {
        enterEmail(email);
        fillDetails(firstName, lastName, company, phone, country, state, useCase, comments);
    }

    public void fillDetails(String firstName, String lastName, String company,
                            String phone, String country, String state, String useCase, String comments) {
        waitForExpandedForm();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterCompany(company);
        enterPhone(phone);
        if (!country.isBlank()) selectCountry(country);
        if (!state.isBlank()) selectState(state);
        if (!useCase.isBlank()) selectUseCase(useCase);
        enterComments(comments);
    }

    public void waitForExpandedForm() { visible(FIRST_NAME); visible(COUNTRY); }

    public void selectCountry(String country) {
        new Select(visible(COUNTRY)).selectByVisibleText(country);
        if (country.equals("United States")) {
            visible(STATE);
            wait.until(d -> isRequired(STATE));
        } else {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(STATE));
            wait.until(d -> !isRequired(STATE));
        }
    }

    public void selectState(String state) { new Select(visible(STATE)).selectByVisibleText(state); }
    public void selectUseCase(String useCase) { new Select(visible(USE_CASE)).selectByVisibleText(useCase); }
    public String selectedText(By locator) {
        List<WebElement> selected = new Select(visible(locator)).getAllSelectedOptions();
        return selected.isEmpty() ? "" : selected.get(0).getText().trim();
    }
    public List<String> optionTexts(By locator) {
        List<String> texts = new ArrayList<>();
        for (WebElement option : new Select(visible(locator)).getOptions())
            texts.add(option.getText().trim());
        return texts;
    }
    public void setMarketing(boolean checked) {
        WebElement checkbox = visible(MARKETING);
        if (checkbox.isSelected() != checked) checkbox.click();
        wait.until(d -> d.findElement(MARKETING).isSelected() == checked);
    }
    public boolean isMarketingSelected() { return visible(MARKETING).isSelected(); }
    public boolean isDisplayed(By locator) {
        for (WebElement element : driver.findElements(locator))
            if (element.isDisplayed()) return true;
        return false;
    }
    public void waitVisible(By locator) { visible(locator); }
    public void waitHidden(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public boolean isRequired(By locator) {
        return Boolean.parseBoolean(driver.findElement(locator).getDomProperty("required"));
    }
    public String value(By locator) { return driver.findElement(locator).getDomProperty("value"); }
    public boolean isValid(By locator) {
        return (Boolean) js.executeScript("return arguments[0].validity.valid;", driver.findElement(locator));
    }
    public boolean validity(By locator, String state) {
        return (Boolean) js.executeScript("return arguments[0].validity[arguments[1]];",
                driver.findElement(locator), state);
    }
    public boolean isFormValid() {
        return (Boolean) js.executeScript("return arguments[0].checkValidity();", driver.findElement(FORM));
    }
    @SuppressWarnings("unchecked")
    public List<String> invalidFieldIds() {
        return (List<String>) js.executeScript(
                "return Array.from(arguments[0].elements).filter(e => e.willValidate && !e.validity.valid).map(e => e.id);",
                driver.findElement(FORM));
    }
    public String fieldId(By locator) { return driver.findElement(locator).getDomAttribute("id"); }
    public String validationMessage(By locator) {
        return driver.findElement(locator).getDomProperty("validationMessage");
    }
    public int maxLength(By locator) {
        String text = driver.findElement(locator).getDomAttribute("maxlength");
        return text == null ? -1 : Integer.parseInt(text);
    }
    public boolean isSubmitEnabled() { return visible(SUBMIT).isEnabled(); }
    public void waitSubmitEnabled() {
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT));
    }
    public void waitSubmitDisabled() {
        wait.until(d -> !d.findElement(SUBMIT).isEnabled());
    }
    public void clickRequestDemo() {
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT)).click();
    }
    public String emailError() { return visible(EMAIL_ERROR).getText(); }
    public String currentUrl() { return driver.getCurrentUrl(); }
    public boolean isRequestPage() {
        return "/request-demo".equals(URI.create(driver.getCurrentUrl()).getPath());
    }
    public boolean isThankYouPage() {
        URI url = URI.create(driver.getCurrentUrl());
        return "saucelabs.com".equalsIgnoreCase(url.getHost())
                && url.getPath().replaceAll("/+$", "").equals("/thank-you-demo");
    }
    public String waitSubmissionResult() {
        return wait.until(d -> {
            if (isThankYouPage()) return "SUCCESS";
            if (isDisplayed(SUBMISSION_ERROR)) return "ERROR: " + visible(SUBMISSION_ERROR).getText();
            return null;
        });
    }
}
