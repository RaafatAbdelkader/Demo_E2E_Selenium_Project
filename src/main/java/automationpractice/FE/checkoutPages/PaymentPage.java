package automationpractice.FE.checkoutPages;

import basePg.ProjectActions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static Logger log = LogManager.getLogger(PaymentPage.class.getName());
    private By headingMsg= By.className("page-heading");
    private By subHeadingMsg= By.className("page-subheading");
    private By payByCheck=By.className("cheque");
    private By pageBtn=By.xpath("//ul[@id='order_step']/li[contains(@class,' last')]");
    private By confirmOrder=By.xpath("//p[@id='cart_navigation']/button[@type='submit']");
    private By alertMsg=By.xpath("//p[contains(@class,'alert')]");
    private By orderInformation=By.xpath("//div[contains(@class,'order-confirmation')]");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void waitForPageToBeLoaded(){
        wait.until(ExpectedConditions.attributeContains(pageBtn,"class","step_current"));
    }
    public String getHeadingMsg(){
        waitForPageToBeLoaded();
        return driver.findElement(headingMsg).getText().trim();
    }
    public void selectPayByCheck(){
        waitForPageToBeLoaded();
        driver.findElement(payByCheck).click();
        log.info("Selected payment by check");
    }
    public String getSubHeadingMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(subHeadingMsg));
        return driver.findElement(subHeadingMsg).getText();
    }
    public void confirmOrder(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmOrder));
        driver.findElement(confirmOrder).click();
        log.info("order confirmed");
    }
    public String getOrderConfirmationMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(alertMsg));
        return driver.findElement(alertMsg).getText();
    }
    public String getOrderInformationMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderInformation));
        return driver.findElement(orderInformation).getText();
    }
    public  String getOrderReferenceNum(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderInformation));
        String ref = null;
        String orderInfoMSG= driver.findElement(orderInformation).getText();
        String[]arrMsg=orderInfoMSG.split("-");
        for (String msg:arrMsg) {
            if(msg.contains("reference")) {
                ref = msg.split("reference")[1].split("\\.")[0].trim();
                break;
            }
        }
        return ref;
    }

}
