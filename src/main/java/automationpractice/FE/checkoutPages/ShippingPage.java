package automationpractice.FE.checkoutPages;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShippingPage {
    private WebDriver driver;
    private ProjectActions projectActions;
    private By terms= By.id("cgv");
    private By headingMsg= By.className("page-heading");
    private By proceedToCheckout=By.xpath("//button[@type='submit' and @name='processCarrier']");
    public ShippingPage(WebDriver driver) {
        this.driver = driver;
        projectActions=new ProjectActions(driver);
    }
    public void acceptTermsOfService(){
        driver.findElement(terms).click();
    }
    public String getHeadingMsg(){
        projectActions.waitToBeClickable(driver.findElement(proceedToCheckout),10);
        return driver.findElement(headingMsg).getText().trim();
    }
    public PaymentPage proceedToPaymentPage(){
        projectActions.waitToBeClickable(driver.findElement(proceedToCheckout),5);
        driver.findElement(proceedToCheckout).click();
        return new PaymentPage(driver);
    }

}
