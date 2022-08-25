package automationpractice.FE.checkoutPages;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SummeryPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By cartTitle = By.id("cart_title");
    private By headingCounterMsg= By.className("heading-counter");
    private By productItems= By.xpath("//tr[contains(@class,'cart_item')]");
    private By proceedToCheckout=By.xpath("//p[contains(@class,'cart_navigation')]/a[@title='Proceed to checkout']");
    private By pageBtn=By.xpath("//ul[@id='order_step']/li[contains(@class,' first')]");

    public SummeryPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getCartTitle(){
        waitForPageToBeLoaded();
        return driver.findElement(cartTitle).getText().trim();
    }
    public String getHeadingCounterMsg(){
        waitForPageToBeLoaded();
        return driver.findElement(headingCounterMsg).getText().trim();
    }


    public Integer getNumOfCartItems(){
        waitForPageToBeLoaded();
        return driver.findElements(productItems).size();
    }

    public AddressPage proceedToAddressPage(){
        waitForPageToBeLoaded();
        driver.findElement(proceedToCheckout).click();
        return new AddressPage(driver);
    }
    public SigninPage proceedToSigninPage(){
        waitForPageToBeLoaded();
        driver.findElement(proceedToCheckout).click();
        return new SigninPage(driver);
    }
    public void waitForPageToBeLoaded(){
        wait.until(ExpectedConditions.attributeContains(pageBtn,"class","step_current"));
    }

}
