package BTVN_Day06;

import action.BookDemoPage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.reporters.EmailableReporter2;
import utils.ExcelUtils;
import static ui.BookDemoUI.*;

@Listeners(EmailableReporter2.class)
public class BookDemoTest {
    private WebDriver driver;
    private BookDemoPage page;
    private static final String EXCEL_FILE = System.getProperty(
            "demo.excel", "E://Auto Testing//Auto-Testing-//BookDemoDay06Data.xlsx");
    private static final List<String> COLUMNS = List.of(
            "Test ID", "Business Email", "First Name", "Last Name", "Company", "Phone number",
            "Country", "Use Case", "Comments (optional)", "Expected Result", "Invalid Field", "Expected Error",
            "State", "Test Name", "Next Country", "Next Email", "Expected Value", "Input Length");

    public record Case(Map<String, String> values) {
        // ExcelUtils của Day05 trim() từng ô; {SPACE} giữ ba dấu cách của TC-11.
        public String get(String column) { return values.getOrDefault(column, "").replace("{SPACE}", " "); }
        public String id() { return get("Test ID"); }
        @Override public String toString() { return id() + " - " + get("Test Name"); }
    }


    @DataProvider(name = "bookDemoData", parallel = false)
    public Object[][] readTestData() {
        Path path = Path.of(EXCEL_FILE);
        if (!Files.isRegularFile(path))
            throw new IllegalArgumentException("Không tìm thấy Excel: " + path.toAbsolutePath());

        List<Map<String, String>> rows = ExcelUtils.readExcelData(EXCEL_FILE, "Sheet1");
        if (rows.isEmpty()) throw new IllegalArgumentException("Excel không có dữ liệu test hoặc đọc file lỗi.");

        Object[][] result = new Object[rows.size()][1];
        Set<String> ids = new HashSet<>();
        for (int i = 0; i < rows.size(); i++) {
            Map<String, String> row = rows.get(i);
            for (String column : COLUMNS)
                if (!row.containsKey(column)) throw new IllegalArgumentException("Excel thiếu cột: " + column);
            Case data = new Case(Map.copyOf(row));
            String id = data.id();
            if (!id.matches("TC-(0[1-9]|[12][0-9]|3[0-2])") || !ids.add(id))
                throw new IllegalArgumentException("Test ID không hỗ trợ hoặc bị trùng: " + id);
            if (data.get("Test Name").isBlank() || data.get("Expected Result").isBlank())
                throw new IllegalArgumentException(id + " thiếu Test Name hoặc Expected Result.");
            if (id.equals("TC-19")) {
                int count = Integer.parseInt(data.get("Input Length"));
                int limit = Integer.parseInt(data.get("Expected Value"));
                if (limit < 1 || count != limit + 1 || count > 10000)
                    throw new IllegalArgumentException("TC-19 cần Input Length = Expected Value + 1, tối đa 10000.");
            }
            result[i][0] = data;
        }
        return result;
    }

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        page = new BookDemoPage(driver);
    }

    @BeforeMethod
    public void beforeMethod() { page.openForm(); }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        String id = result.getParameters().length > 0
                ? ((Case) result.getParameters()[0]).id() : "Setup";
        String status = result.isSuccess() ? "PASS" : result.getStatus() == ITestResult.SKIP ? "SKIP" : "FAIL";
        Reporter.log(status + ": " + id, true);
        if (result.getThrowable() != null) Reporter.log("Chi tiết: " + result.getThrowable(), true);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() { if (driver != null) driver.quit(); }

    @Test(dataProvider = "bookDemoData", description = "Kiểm thử Request Demo từ Excel")
    public void testBookDemo(Case data) {
        String testId = data.id();
        Reporter.log(data.toString(), true);
        Reporter.log("Mong đợi: " + data.get("Expected Result"), true);
        switch (testId) {
            case "TC-01", "TC-02": submitHappyCase(data); break;
            case "TC-03", "TC-11": checkEmail(data, "valueMissing"); break;
            case "TC-09", "TC-10", "TC-26", "TC-27": checkEmail(data, "typeMismatch"); break;
            case "TC-04": checkMissing(data, FIRST_NAME); break;
            case "TC-05": checkMissing(data, LAST_NAME); break;
            case "TC-06": checkMissing(data, COMPANY); break;
            case "TC-07": checkMissing(data, PHONE); break;
            case "TC-08": checkMissing(data, COUNTRY); break;
            case "TC-22": checkMissing(data, STATE); break;
            case "TC-12":
                page.enterEmail(data.get("Business Email"));
                page.waitVisible(EMAIL_ERROR);
                page.waitSubmitDisabled();
                equal(page.emailError(), data.get("Expected Value"), "Thông báo email công việc");
                check(!page.isSubmitEnabled(), "Nút gửi bị khóa");
                break;
            case "TC-13":
                equal(page.selectedText(USE_CASE), data.get("Expected Value"), "Use Case mặc định");
                break;
            case "TC-14":
                equal(page.optionTexts(USE_CASE), Arrays.asList(data.get("Expected Value").split("\\|", -1)),
                        "Danh sách Use Case và thứ tự");
                break;
            case "TC-15":
                fillForm(data);
                equal(page.selectedText(USE_CASE), data.get("Expected Value"), "Use Case đã chọn");
                validForm();
                break;
            case "TC-16":
                fillForm(data);
                equal(page.value(COMMENTS), "", "Comments để trống");
                check(!page.isRequired(COMMENTS), "Comments không bắt buộc");
                validForm();
                break;
            case "TC-17", "TC-18":
                page.enterComments(data.get("Comments (optional)"));
                equal(page.value(COMMENTS).replace("\r\n", "\n"), data.get("Expected Value"),
                        "Giữ nguyên nội dung Comments");
                break;
            case "TC-19": checkLength(data); break;
            case "TC-20":
                fillForm(data);
                equal(page.value(COMMENTS), data.get("Comments (optional)"), "Đã nhập Comments");
                page.enterComments("");
                equal(page.value(COMMENTS), "", "Đã xóa Comments");
                validForm();
                break;
            case "TC-21":
                fillForm(data);
                validForm();
                page.enterCompany("");
                assertMissing(COMPANY);
                page.enterCompany(data.get("Company"));
                equal(page.validationMessage(COMPANY), "", "Company hết lỗi sau nhập lại");
                validForm();
                break;
            case "TC-23":
                fillForm(data);
                equal(page.selectedText(STATE), data.get("Expected Value"), "State đã chọn");
                check(page.isRequired(STATE), "State bắt buộc khi chọn United States");
                validForm();
                break;
            case "TC-24":
                fillForm(data);
                equal(page.selectedText(STATE), data.get("State"), "State ban đầu");
                page.selectCountry(data.get("Next Country"));
                equal(page.selectedText(COUNTRY), data.get("Next Country"), "Country sau khi đổi");
                check(!page.isDisplayed(STATE) && !page.isRequired(STATE), "State ẩn và không bắt buộc");
                validForm();
                break;
            case "TC-25": initialForm(data); break;
            case "TC-28":
                fillForm(data);
                page.waitVisible(MARKETING);
                check(!page.isMarketingSelected(), "Checkbox marketing mặc định chưa chọn ở United Kingdom");
                break;
            case "TC-29":
                fillForm(data);
                page.waitVisible(MARKETING);
                page.setMarketing(true);
                check(page.isMarketingSelected(), "Đã chọn checkbox");
                page.setMarketing(false);
                check(!page.isMarketingSelected(), "Đã bỏ chọn checkbox");
                break;
            case "TC-30":
                fillForm(data);
                page.waitVisible(MARKETING);
                page.setMarketing(false);
                check(!page.isRequired(MARKETING), "Checkbox không bắt buộc");
                validForm();
                break;
            case "TC-31":
                fillForm(data);
                check(!page.isDisplayed(STATE), "State ban đầu ẩn");
                page.selectCountry(data.get("Next Country"));
                check(page.isDisplayed(STATE) && page.isRequired(STATE), "State hiện và bắt buộc");
                check(page.validity(STATE, "valueMissing"), "State chưa chọn gây lỗi required");
                break;
            case "TC-32":
                page.enterEmail(data.get("Business Email"));
                page.waitVisible(EMAIL_ERROR);
                page.waitSubmitDisabled();
                page.enterEmail(data.get("Next Email"));
                fillDetails(data);
                page.waitHidden(EMAIL_ERROR);
                validForm();
                break;
            default: throw new IllegalArgumentException("Chưa có xử lý cho " + testId);
        }
    }

    private void fillForm(Case data) {
        page.fillForm(data.get("Business Email"), data.get("First Name"), data.get("Last Name"),
                data.get("Company"), data.get("Phone number"), data.get("Country"),
                data.get("State"), data.get("Use Case"), data.get("Comments (optional)"));
    }

    private void fillDetails(Case data) {
        page.fillDetails(data.get("First Name"), data.get("Last Name"), data.get("Company"),
                data.get("Phone number"), data.get("Country"), data.get("State"),
                data.get("Use Case"), data.get("Comments (optional)"));
    }

    private void checkEmail(Case data, String state) {
        page.enterEmail(data.get("Business Email"));
        check(page.validity(EMAIL, state), "Email validity." + state + " = true");
        check(!page.isValid(EMAIL), "Email không hợp lệ: " + page.validationMessage(EMAIL));
        check(page.isRequestPage(), "Vẫn ở trang Request Demo");
    }

    private void checkMissing(Case data, By target) {
        // Dữ liệu trống lấy trực tiếp từ Excel, không xóa dữ liệu hợp lệ để giả lập.
        fillForm(data);
        assertMissing(target);
    }

    private void assertMissing(By target) {
        check(page.validity(target, "valueMissing"), "Thiếu dữ liệu: " + page.fieldId(target));
        equal(page.invalidFieldIds(), List.of(page.fieldId(target)), "Chỉ trường mục tiêu không hợp lệ");
        page.clickRequestDemo();
        check(page.isRequestPage(), "Form chưa chuyển trang");
        check(!page.validationMessage(target).isBlank(), "Có thông báo: " + page.validationMessage(target));
    }

    private void checkLength(Case data) {
        int count = Integer.parseInt(data.get("Input Length"));
        int limit = Integer.parseInt(data.get("Expected Value"));
        equal(page.maxLength(COMMENTS), limit, "Giới hạn Comments");
        String character = data.get("Comments (optional)");
        if (character.isEmpty()) {
            character = "A";
        }
        Assert.assertEquals(character.length(), 1,
                "TC-19 chỉ chấp nhận một ký tự mẫu nếu có nhập trong Excel.");
        for (int length = limit - 1; length <= count; length++) {
            page.enterComments(character.repeat(length));
            equal(page.value(COMMENTS), character.repeat(Math.min(length, limit)),
                    "Nội dung khi thử nhập " + length + " ký tự");
        }
    }

    private void initialForm(Case data) {
        for (By field : List.of(EMAIL, COMPANY, USE_CASE, COMMENTS, SUBMIT))
            check(page.isDisplayed(field), "Hiển thị ban đầu: " + field);
        for (By field : List.of(FIRST_NAME, LAST_NAME, PHONE, COUNTRY, STATE, MARKETING))
            check(!page.isDisplayed(field), "Ẩn ban đầu: " + field);
        page.enterEmail(data.get("Business Email"));
        page.waitForExpandedForm();
        for (By field : List.of(FIRST_NAME, LAST_NAME, PHONE, COUNTRY))
            check(page.isDisplayed(field), "Hiện sau email hợp lệ: " + field);
    }

    private void submitHappyCase(Case data) {
        if (data.get("Business Email").isBlank()) {
            throw new SkipException("Điền Business Email của bạn vào dòng " + data.get("Test ID")
                    + " trong Excel để chạy test gửi yêu cầu demo thật.");
        }
        fillForm(data);
        validForm();
        page.clickRequestDemo();
        equal(page.waitSubmissionResult(), "SUCCESS", "Gửi form thành công");
        equal(page.currentUrl(), data.get("Expected Value"), "URL sau khi gửi");
        check(page.isThankYouPage(), "Đã đến trang cảm ơn");
    }

    private void validForm() {
        check(page.isFormValid(), "Form hợp lệ, trường lỗi: " + page.invalidFieldIds());
        page.waitSubmitEnabled();
        check(!page.isDisplayed(EMAIL_ERROR), "Không có lỗi email công việc");
    }

    private void check(boolean actual, String label) {
        Reporter.log(label + " | Mong đợi: true | Thực tế: " + actual, true);
        Assert.assertTrue(actual, label);
    }
    private void equal(Object actual, Object expected, String label) {
        Reporter.log(label + " | Mong đợi: " + expected + " | Thực tế: " + actual, true);
        Assert.assertEquals(actual, expected, label);
    }

}
