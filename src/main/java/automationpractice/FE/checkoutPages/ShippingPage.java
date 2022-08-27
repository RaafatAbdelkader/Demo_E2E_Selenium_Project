package automationpractice.FE.checkoutPages;

import basePg.MyLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ShippingPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By terms= By.xpath("//input[@type='checkbox']");
    private By headingMsg= By.className("page-heading");
    private By proceedToCheckout=By.xpath("//button[@type='submit' and @name='processCarrier']");
    public ShippingPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void acceptTermsOfService(){
        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckout));
        driver.findElement(terms).click();
        MyLogger.info("accepted to the terms of service");

    }
    public String getHeadingMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(headingMsg));
        return driver.findElement(headingMsg).getText().trim();
    }
    public PaymentPage proceedToPaymentPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(proceedToCheckout));
        driver.findElement(proceedToCheckout).click();
        MyLogger.info("clicked on proceed to checkout");
        return new PaymentPage(driver);
    }

}
