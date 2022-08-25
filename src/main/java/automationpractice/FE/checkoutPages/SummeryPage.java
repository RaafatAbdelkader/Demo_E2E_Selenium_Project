package automationpractice.FE.checkoutPages;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SummeryPage {
    private WebDriver driver;
    private ProjectActions projectActions;
    private By cartTitle = By.id("cart_title");
    private By headingCounterMsg= By.className("heading-counter");
    private By productItems= By.xpath("//tr[contains(@class,'cart_item')]");
    private By proceedToCheckout=By.xpath("//p[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']");
    public SummeryPage(WebDriver driver) {
        this.driver = driver;
        projectActions=new ProjectActions(driver);
    }

    public String getCartTitle(){
        projectActions.waitToBeClickable(driver.findElement(cartTitle),5);
        return driver.findElement(cartTitle).getText().trim();
    }
    public String getHeadingCounterMsg(){
        projectActions.waitToBeClickable(driver.findElement(headingCounterMsg),5);
        return driver.findElement(headingCounterMsg).getText().trim();
    }


    public Integer getNumOfCartItems(){
        return driver.findElements(productItems).size();
    }

    public AddressPage proceedToAddressPage(){
        projectActions.waitToBeClickable(driver.findElement(proceedToCheckout),5);
        driver.findElement(proceedToCheckout).click();
        return new AddressPage(driver);
    }
    public SigninPage proceedToSigninPage(){
        projectActions.waitToBeClickable(driver.findElement(proceedToCheckout),5);
        driver.findElement(proceedToCheckout).click();
        return new SigninPage(driver);
    }
}
