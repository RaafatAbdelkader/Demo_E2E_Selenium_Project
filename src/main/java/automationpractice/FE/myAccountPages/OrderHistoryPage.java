package automationpractice.FE.myAccountPages;

import automationpractice.FE.checkoutPages.ShippingPage;
import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderHistoryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    ProjectActions projectActions;
    private By navigationPage= By.className("navigation_page");
    private By orderItems= By.xpath("//table[@id='order-list']/tbody/tr");
    private By references= By.xpath("//td[contains(@class,'history_link')]/a");
    private By invoices= By.xpath("//a[@title='Invoice']");

    public OrderHistoryPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        projectActions= new ProjectActions(driver);
    }
    public void downloadInvoice(String ref){
        waitForNavigation();
        List<WebElement>ordersRef=driver.findElements(references);
        List<WebElement>inv=driver.findElements(invoices);
        for (int i = 0; i < ordersRef.size(); i++) {
           if (ordersRef.get(i).getText().contains(ref)){
               inv.get(i).click();
               break;
           }
        }
        projectActions.waitToBeDownloaded();
    }
    private void waitForNavigation(){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(navigationPage,"Order history"));
    }

}
