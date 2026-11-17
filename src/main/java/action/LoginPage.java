package action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.LoginPageUI;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(LoginPageUI.USERNAME_FIELD).sendKeys(username);
    }

    public void enterpassword(String password) {
        driver.findElement(LoginPageUI.PASSWORD_FIELD).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(LoginPageUI.LOGIN_BUTTON).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterpassword(password);
        clickLogin();
    }
    public boolean isLogoDisplayed() {
        return driver.findElement(LoginPageUI.APP_LOGO).isDisplayed();
    }
    public int getInventoryItemCount() {
        return driver.findElements(LoginPageUI.INVENTORY_ITEM).size();
    }
    public String getErrorMessage() {
        return driver.findElement(LoginPageUI.ERROR_MESSAGE).getText();
    }
    public boolean isErrorMessageDisplay(){
        return driver.findElement(LoginPageUI.ERROR_MESSAGE).isDisplayed();
    }
    }

