import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class AddToCartTest {
    private WebDriver driver;
    private PageActions pageActions;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        pageActions = new PageActions(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void addsSauceLabsBackpackToTheCart() {
        String scenario = "adds Sauce Labs Backpack to the cart";
        long startedAt = System.currentTimeMillis();
        boolean passed = false;
        try {
            pageActions.loginPage("standard_user", "secret_sauce");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='inventory-container']")));
            pageActions.click("add-to-cart-sauce-labs-backpack");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='remove-sauce-labs-backpack']")));
            assertEquals(driver.findElement(By.cssSelector("[data-test='shopping-cart-badge']")).getText().trim(), "1");
            pageActions.click("shopping_cart_container");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='inventory-item']")));
            assertEquals(driver.findElement(By.cssSelector("[data-test='inventory-item-name']")).getText().trim(), "Sauce Labs Backpack");
            passed = true;
        } finally {
            pageActions.writeValidationResult(scenario, passed, startedAt);
        }
    }
}