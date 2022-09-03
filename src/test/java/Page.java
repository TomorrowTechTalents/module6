import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

class Page {
    private final WebDriver driver;
    Page() {
        System.setProperty("webdriver.chrome.driver", ".\\assets\\operadriver.exe");

        this.driver = new ChromeDriver();
    }

    WebDriver getDriver() {
        return this.driver;
    }

    String getTitle() {
        return this.driver.getTitle();
    }
}
