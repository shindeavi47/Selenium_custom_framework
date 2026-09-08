import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OpenChrome {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.manage().window().maximize();
            driver.get("https://example.com");
            System.out.println("Page title: " + driver.getTitle());
        } finally {
            driver.quit();
        }
    }
}