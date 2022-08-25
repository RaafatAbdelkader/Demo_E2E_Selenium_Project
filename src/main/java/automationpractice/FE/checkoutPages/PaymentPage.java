package automationpractice.FE.checkoutPages;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By headingMsg= By.className("page-heading");
    private By subHeadingMsg= By.className("page-subheading");
    private By payByCheck=By.className("cheque");
    private By pageBtn=By.xpath("//ul[@id='order_step']/li[contains(@class,' last')]");
    private By confirmOrder=By.xpath("//p[@id='cart_navigation']/button[@type='submit']");
    private By alertMsg=By.xpath("//p[contains(@class,'alert')]");

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
    }
    public String getSubHeadingMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(subHeadingMsg));
        return driver.findElement(subHeadingMsg).getText();
    }
    public void confirmOrder(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmOrder));
        driver.findElement(confirmOrder).click();
    }
    public String getConfirmationMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(alertMsg));
        return driver.findElement(alertMsg).getText();
    }

}
