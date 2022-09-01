package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Page {
    public WebDriver driver;
    Page() {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Marcos Jr\\Desktop\\operadriver_win64\\operadriver.exe");

        this.driver = new ChromeDriver();
    }
}
