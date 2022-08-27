package automationpractice.FE.checkoutPages;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShippingPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By terms= By.id("cgv");
    private By headingMsg= By.className("page-heading");
    private By proceedToCheckout=By.xpath("//button[@type='submit' and @name='processCarrier']");
    public ShippingPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void acceptTermsOfService(){
        driver.findElement(terms).click();
    }
    public String getHeadingMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(headingMsg));
        return driver.findElement(headingMsg).getText().trim();
    }
    public PaymentPage proceedToPaymentPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(proceedToCheckout));
        driver.findElement(proceedToCheckout).click();
        return new PaymentPage(driver);
    }

}
