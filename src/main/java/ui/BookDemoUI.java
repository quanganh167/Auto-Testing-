
    package ui;

import org.openqa.selenium.By;

    public final class BookDemoUI {
        public static final String URL = "https://saucelabs.com/request-demo";
        public static final By FORM = By.xpath("//form[@data-name='Book Your Demo']");
        public static final By EMAIL = By.xpath("//input[@id='Business-Email']");
        public static final By FIRST_NAME = By.xpath("//input[@name='First Name']");
        public static final By LAST_NAME = By.xpath("//input[@name='Last Name']");
        public static final By COMPANY = By.xpath("//input[@id='Company']");
        public static final By PHONE = By.xpath("//input[@id='Phone']");
        public static final By COUNTRY = By.xpath("//select[@id='Country']");
        public static final By STATE = By.xpath("//select[@id='State']");
        public static final By USE_CASE = By.xpath("//select[@id='Use-Case']");
        public static final By COMMENTS = By.xpath("//textarea[@id='Comments']");
        public static final By MARKETING = By.xpath("//input[@id='Checkbox']");
        public static final By SUBMIT = By.xpath("//form[@data-name='Book Your Demo']//button[@type='submit']");
        public static final By EMAIL_ERROR = By.xpath(
                "//input[@id='Business-Email']/parent::*/*[contains(.,'Please enter a valid business email.')]");
        public static final By SUBMISSION_ERROR = By.xpath(
                "//*[contains(@class,'w-form-fail')]");
        public static final By SUCCESS_HEADING = By.xpath(
                "//*[self::h1 or self::h2 or self::h3][normalize-space()='Thanks for reaching out!']");

        private BookDemoUI() { }
    }


