
package BTVN_Day04;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductCheckOut {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();

        try {
            // STEP 1: Đăng nhập.
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.xpath("//input[@data-test='username']"))
                    .sendKeys("standard_user");
            driver.findElement(By.xpath("//input[@data-test='password']"))
                    .sendKeys("secret_sauce");
            driver.findElement(By.xpath("//input[@data-test='login-button']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//select[@data-test='product-sort-container']")
            ));
            Thread.sleep(2000);

            check("STEP 1 - Đăng nhập",
                    driver.getCurrentUrl().contains("inventory.html"));

            // STEP 2: Sắp xếp giá từ thấp đến cao.
            Select sort = new Select(driver.findElement(
                    By.xpath("//select[@data-test='product-sort-container']")
            ));
            sort.selectByVisibleText("Price (low to high)");


            boolean sorted = wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.attributeToBe(By.xpath("//select[@data-test='product-sort-container']"),
                            "value",
                            "lohi"
                    )
            ));

            Thread.sleep(2000);
            check("STEP 2 - Chọn Price (low to high)", sorted);

            // STEP 3: Thêm hai sản phẩm đầu tiên vào giỏ.
            String[] names = new String[2];
            BigDecimal[] prices = new BigDecimal[2];

            check("STEP 3 - Có ít nhất 2 sản phẩm để chọn",
                    driver.findElements(By.xpath(
                            "//div[@data-test='inventory-item']"
                    )).size() >= 2);

            for (int i = 0; i < 2; i++) {
                WebElement item = driver.findElements(By.xpath(
                        "//div[@data-test='inventory-item']"
                )).get(i);

                names[i] = item.findElement(By.xpath(
                        ".//div[@data-test='inventory-item-name']"
                )).getText();

                prices[i] = money(item.findElement(By.xpath(
                        ".//div[@data-test='inventory-item-price']"
                )).getText());

                item.findElement(By.xpath(
                        ".//button[text()='Add to cart']"
                )).click();

                System.out.println("Đã thêm: " + names[i] + " - $" + prices[i]);
                Thread.sleep(1500);
            }

            check("STEP 3 - Giỏ hàng hiển thị số 2",
                    driver.findElement(By.xpath(
                            "//span[@data-test='shopping-cart-badge']"
                    )).getText().equals("2"));
            Thread.sleep(2000); // Nhìn biểu tượng giỏ hàng.

            // STEP 4: Mở Your Cart và kiểm tra sản phẩm.
            driver.findElement(By.xpath(
                    "//a[@data-test='shopping-cart-link']"
            )).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[@data-test='title' and text()='Your Cart']")
            ));
            Thread.sleep(2000);

            List<WebElement> cartItems = driver.findElements(By.xpath(
                    "//div[@data-test='inventory-item']"
            ));
            check("STEP 4 - Your Cart có đúng 2 sản phẩm",
                    cartItems.size() == 2);

            for (int i = 0; i < 2; i++) {
                WebElement item = cartItems.get(i);
                String name = item.findElement(By.xpath(
                        ".//div[@data-test='inventory-item-name']"
                )).getText();
                BigDecimal price = money(item.findElement(By.xpath(
                        ".//div[@data-test='inventory-item-price']"
                )).getText());

                System.out.println(
                        "Your Cart " + (i + 1) + ": " + name + " - $" + price
                );
                check("STEP 4 - Tên và giá sản phẩm " + (i + 1),
                        name.equals(names[i])
                                && price.compareTo(prices[i]) == 0);
            }

            check("STEP 4 - Có 2 nút Remove",
                    driver.findElements(By.xpath(
                            "//div[@data-test='inventory-item']"
                                    + "//button[text()='Remove']"
                    )).size() == 2);
            Thread.sleep(2000);

            // STEP 5: Click Checkout và nhập thông tin.
            driver.findElement(By.xpath(
                    "//button[@data-test='checkout']"
            )).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@data-test='firstName']")
            ));
            Thread.sleep(2000); // Nhìn màn hình nhập thông tin.

            driver.findElement(By.xpath(
                    "//input[@data-test='firstName']"
            )).sendKeys("An");
            Thread.sleep(800); // Nhìn First name vừa nhập.

            driver.findElement(By.xpath(
                    "//input[@data-test='lastName']"
            )).sendKeys("Nguyen");
            Thread.sleep(800); // Nhìn Last name vừa nhập.

            driver.findElement(By.xpath(
                    "//input[@data-test='postalCode']"
            )).sendKeys("700000");

            check("STEP 5 - Đã nhập First name, Last name, Zip code",
                    driver.findElement(By.xpath(
                            "//input[@data-test='firstName']"
                    )).getAttribute("value").equals("An")
                            && driver.findElement(By.xpath(
                            "//input[@data-test='lastName']"
                    )).getAttribute("value").equals("Nguyen")
                            && driver.findElement(By.xpath(
                            "//input[@data-test='postalCode']"
                    )).getAttribute("value").equals("700000"));
            Thread.sleep(2000);

            // STEP 6: Click Continue và kiểm tra Overview.
            driver.findElement(By.xpath(
                    "//input[@data-test='continue']"
            )).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(
                            "//span[@data-test='title'"
                                    + " and text()='Checkout: Overview']"
                    )
            ));
            Thread.sleep(2000); // Nhìn màn hình Checkout: Overview.

            List<WebElement> overviewItems = driver.findElements(By.xpath(
                    "//div[@data-test='inventory-item']"
            ));
            check("STEP 6 - Description có đúng 2 sản phẩm",
                    overviewItems.size() == 2);

            for (int i = 0; i < 2; i++) {
                WebElement item = overviewItems.get(i);
                String quantity = item.findElement(By.xpath(
                        ".//div[@data-test='item-quantity']"
                )).getText();
                String name = item.findElement(By.xpath(
                        ".//div[@data-test='inventory-item-name']"
                )).getText();
                BigDecimal price = money(item.findElement(By.xpath(
                        ".//div[@data-test='inventory-item-price']"
                )).getText());

                System.out.println(
                        "Overview " + (i + 1) + ": "
                                + quantity + " x " + name + " - $" + price
                );
                check("STEP 6 - Số lượng, tên, giá sản phẩm " + (i + 1),
                        quantity.equals("1")
                                && name.equals(names[i])
                                && price.compareTo(prices[i]) == 0);
            }

            check("STEP 6 - Shipping Information",
                    driver.findElement(By.xpath(
                            "//div[@data-test='shipping-info-value']"
                    )).getText().equals("Free Pony Express Delivery!"));

            BigDecimal expectedItems = prices[0].add(prices[1]);
            BigDecimal itemTotal = money(driver.findElement(By.xpath(
                    "//div[@data-test='subtotal-label']"
            )).getText());
            BigDecimal tax = money(driver.findElement(By.xpath(
                    "//div[@data-test='tax-label']"
            )).getText());
            BigDecimal total = money(driver.findElement(By.xpath(
                    "//div[@data-test='total-label']"
            )).getText());

            System.out.println(
                    "Item total: $" + itemTotal
                            + " | Tax: $" + tax
                            + " | Total: $" + total
            );
            check("STEP 6 - Item total = tổng giá 2 sản phẩm",
                    itemTotal.compareTo(expectedItems) == 0);
            check("STEP 6 - Total = Item total + Tax",
                    total.compareTo(itemTotal.add(tax)) == 0);
            check("STEP 6 - Hiển thị nút Finish",
                    driver.findElement(By.xpath(
                            "//button[@data-test='finish']"
                    )).isDisplayed());
            Thread.sleep(2000);

            // STEP 7: Click Finish và kiểm tra thông báo.
            driver.findElement(By.xpath(
                    "//button[@data-test='finish']"
            )).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h2[@data-test='complete-header']")
            ));
            Thread.sleep(2000); // Nhìn màn hình Checkout: Complete!

            check("STEP 7 - Checkout: Complete!",
                    driver.findElement(By.xpath(
                            "//span[@data-test='title']"
                    )).getText().equals("Checkout: Complete!"));
            check("STEP 7 - Thank you for your order!",
                    driver.findElement(By.xpath(
                            "//h2[@data-test='complete-header']"
                    )).getText().equals("Thank you for your order!"));
            check("STEP 7 - Thông báo giao hàng",
                    driver.findElement(By.xpath(
                            "//div[@data-test='complete-text']"
                    )).getText().equals(
                            "Your order has been dispatched, and will arrive "
                                    + "just as fast as the pony can get there!"
                    ));
            check("STEP 7 - Hiển thị nút Back Home",
                    driver.findElement(By.xpath(
                            "//button[@data-test='back-to-products']"
                    )).isDisplayed());

            System.out.println("PASS: Checkout sản phẩm thành công.");
            Thread.sleep(3000);
        } finally {
            driver.quit();
        }
    }

    private static BigDecimal money(String text) {
        return new BigDecimal(
                text.substring(text.indexOf('$') + 1).trim()
        );
    }

    private static void check(String step, boolean passed) {
        System.out.println((passed ? "PASS: " : "FAIL: ") + step);
        if (!passed) {
            throw new AssertionError(step);
        }
    }
}