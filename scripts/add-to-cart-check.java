import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ValidationResults;

class AddToCartCheck {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://www.saucedemo.com/");

            ValidationResults.resetValidationResults();
            PageActions pageActions = new PageActions(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

            pageActions.loginPage("standard_user", "secret_sauce");
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-test='inventory-container']")));
            acceptAlertIfPresent(driver);

            pageActions.validateAction(
                    "Adds Sauce Labs Backpack to the cart",
                    "add-to-cart-sauce-labs-backpack",
                    List.of(new PageActions.ValidationCheck("#remove-sauce-labs-backpack", null)));

            pageActions.validateAction(
                    "Validates cart badge count",
                    null,
                    List.of(new PageActions.ValidationCheck(".shopping_cart_badge", "1")));

            pageActions.validateAction(
                    "Opens the shopping cart",
                    "shopping_cart_container",
                    List.of(new PageActions.ValidationCheck("[data-test='inventory-item']", null)));

            pageActions.validateAction(
                    "Validates Sauce Labs Backpack in the cart",
                    null,
                    List.of(new PageActions.ValidationCheck(
                            "[data-test='inventory-item-name']", "Sauce Labs Backpack")));

            System.out.println("Item added to cart successfully.");
        } finally {
            driver.quit();
        }
    }

    private static void acceptAlertIfPresent(WebDriver driver) {
        try {
            Alert alert = new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.alertIsPresent());
            System.out.println("Accepted warning: " + alert.getText());
            alert.accept();
        } catch (NoAlertPresentException exception) {
            // No browser alert was displayed.
        }
    }
}