package automationpractice.FE;

import automationpractice.FE.myAccountPages.OrderHistoryPage;
import basePg.ProjectActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static Logger log = LogManager.getLogger(MyAccountPage.class.getName());

    private By navigationPage= By.className("navigation_page");
    private By orders= By.xpath("//a[@title='Orders']");
    private By page_heading_msg= By.className("info-account");

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPage_heading_msg() {
        waitForNavigation();
        return driver.findElement(page_heading_msg).getText();
    }
    public OrderHistoryPage navigateToOrderHistory(){
        waitForNavigation();
        driver.findElement(orders).click();
        log.info("navigated to order History page");
        return  new OrderHistoryPage(driver);
    }
    private void waitForNavigation(){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(navigationPage,"My account"));
    }
}
