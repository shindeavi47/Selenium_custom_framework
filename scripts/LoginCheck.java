import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginCheck {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://www.saucedemo.com/");
            new PageActions(driver).loginPage("standard_user", "secret_sauce");
            System.out.println("Page title: " + driver.getTitle());
        } finally {
            driver.quit();
        }
    }
}