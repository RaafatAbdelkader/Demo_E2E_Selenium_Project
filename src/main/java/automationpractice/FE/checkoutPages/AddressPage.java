package automationpractice.FE.checkoutPages;
import basePg.MyLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AddressPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By headingMsg= By.className("page-heading");
    private By proceedToCheckout=By.xpath("//button[@type='submit' and @name='processAddress']");
    private By pageBtn=By.xpath("//ul[@id='order_step']/li[contains(@class,' third')]");
    public AddressPage(WebDriver driver) {
        this.driver = driver;

        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public String getHeadingMsg(){
        waitForPageToBeLoaded();
        return driver.findElement(headingMsg).getText().trim();
    }
    public ShippingPage proceedToShipping(){
        waitForPageToBeLoaded();
        driver.findElement(proceedToCheckout).click();
        MyLogger.info("clicked on proceed to checkout");
        return new ShippingPage(driver);
    }

    private void waitForPageToBeLoaded(){
        wait.until(ExpectedConditions.attributeContains(pageBtn,"class","step_current"));
    }
}
