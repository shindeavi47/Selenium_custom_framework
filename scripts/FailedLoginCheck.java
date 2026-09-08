import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FailedLoginCheck {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://www.saucedemo.com/");
            PageActions pageActions = new PageActions(driver);
            pageActions.loginPage("locked_out_user", "secret_sauce");
            String errorMessage = new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")))
                    .getText().trim();
            if (driver.getCurrentUrl().contains("/inventory.html") || errorMessage.isBlank()) {
                throw new IllegalStateException("Login unexpectedly succeeded.");
            }
            System.out.println("Login failed as expected: " + errorMessage);
        } finally {
            driver.quit();
        }
    }
}