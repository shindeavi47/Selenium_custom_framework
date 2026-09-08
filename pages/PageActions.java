import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ValidationResults;

public class PageActions {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public PageActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void loginPage(String username, String password) {
        type("user-name", username);
        type("password", password);
        click("login-button");
    }

    public void type(String id, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        element.sendKeys(text);
    }

    public void click(String id) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
    }

    public boolean validateAction(String scenario, String clickId, List<ValidationCheck> checks) {
        if (checks == null || checks.isEmpty()) {
            throw new IllegalArgumentException("At least one validation check is required.");
        }

        boolean passed = false;
        long startedAt = System.currentTimeMillis();
        try {
            if (clickId != null && !clickId.isBlank()) {
                click(clickId);
            }
            for (ValidationCheck check : checks) {
                if (check == null || check.element() == null || check.element().isBlank()) {
                    throw new IllegalArgumentException("Each validation check requires an element selector.");
                }
                if (check.clickId() != null && !check.clickId().isBlank()) {
                    click(check.clickId());
                }
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(check.element())));
                if (check.value() != null && !check.value().equals(element.getText().trim())) {
                    throw new AssertionError("Expected \"" + check.value() + "\" for " + check.element()
                            + ", but found \"" + element.getText().trim() + "\".");
                }
            }
            passed = true;
            return true;
        } finally {
            writeValidationResult(scenario, passed, startedAt);
        }
    }

    public void writeValidationResult(String scenario, boolean passed, long startedAt) {
        long elapsedMs = System.currentTimeMillis() - startedAt;
        String result = elapsedMs >= 30_000 ? "TIMEOUT" : passed ? "PASS" : "FAIL";
        ValidationResults.addValidationResult(result, scenario, elapsedMs);
    }

    public record ValidationCheck(String element, String value, String clickId) {
        public ValidationCheck(String element, String value) {
            this(element, value, null);
        }
    }
}