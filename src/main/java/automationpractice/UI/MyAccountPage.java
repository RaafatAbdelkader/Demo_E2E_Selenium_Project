package automationpractice.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyAccountPage {
    WebDriver driver;
    private By page_heading_msg= By.className("info-account");
    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement getPage_heading_msg() {
        return driver.findElement(page_heading_msg);
    }
}
