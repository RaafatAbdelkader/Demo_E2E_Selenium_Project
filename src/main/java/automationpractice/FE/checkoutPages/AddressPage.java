package automationpractice.FE.checkoutPages;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddressPage {
    private WebDriver driver;
    private ProjectActions projectActions;
    private By headingMsg= By.className("page-heading");
    private By proceedToCheckout=By.xpath("//button[@type='submit' and @name='processAddress']");

    public AddressPage(WebDriver driver) {
        this.driver = driver;
        projectActions=new ProjectActions(driver);
    }
    public String getHeadingMsg(){
        projectActions.waitToBeClickable(driver.findElement(headingMsg),10);
        return driver.findElement(headingMsg).getText().trim();
    }
    public ShippingPage proceedToShipping(){
        projectActions.waitToBeClickable(driver.findElement(proceedToCheckout),5);
        driver.findElement(proceedToCheckout).click();
        return new ShippingPage(driver);
    }
}
