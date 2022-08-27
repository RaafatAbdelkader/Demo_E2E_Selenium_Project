package automationpractice.FE;

import automationpractice.FE.checkoutPages.SummeryPage;
import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public Header( WebDriver driver){
        this.driver=driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        actions= new Actions(driver);
    }

    private By loginBTN=By.className("login");
    private By searchInput= By.id("search_query_top");
    private By logoutBTN=By.className("logout");
    private By shoppingCart= By.xpath("//div[@class='shopping_cart']/a");
    private By headerText=By.xpath("//div[@class='header_user_info']/parent::nav/parent::div");
    private By women=By.xpath("//li/a[@title='Women']");
    private By summerDresses=By.xpath("//li/a[@title='Summer Dresses']");
    private By myAccount=By.xpath("//a[@class='account']");
    private By viewCartProductsQuantity=By.xpath("//a[@title='View my shopping cart']/span[contains(@class,'cart_quantity')]");
    private By proceedToCheckout=By.xpath("//a[@title='Proceed to checkout']");
    private By addedTOCartSuccessMSG=By.xpath("//div[contains(@class,'layer_cart_product')] //h2");
    private By homePage=By.xpath("//a[@title='My Store']");

    public LoginPage navigateToLoginPage(){
       logout();
       driver.findElement(loginBTN).click();
       return  new LoginPage(driver);
    }
    public boolean isLoggedIn(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(headerText));
        return driver.findElement(headerText).getText().contains("Sign out");
    }
    public void logout(){
        if (isLoggedIn()){
            wait.until(ExpectedConditions.elementToBeClickable(logoutBTN));
            driver.findElement(logoutBTN).click();
        }

    }
    public void clickWomenBTN(){
        driver.findElement(women).click();
    }
    public SummerDressesPage navigateToSummerDressesPage(){
        Actions actions=new Actions(driver);
        actions.moveToElement(driver.findElement(women)).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(summerDresses));
         driver.findElement(summerDresses).click();
         return new SummerDressesPage(driver);
    }
    public MyAccountPage navigateToMyAccount(){
        if (isLoggedIn())
            driver.findElement(myAccount).click();
        return new MyAccountPage(driver);
    }
    public void viewShoppingCart(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCart));
        actions.moveToElement(driver.findElement(shoppingCart)).build().perform();
    }
    public void searchInput(String searchWord){
        driver.findElement(searchInput).sendKeys(searchWord);
    }
    public Integer getNumOfProductItemsAddedToCart(){
        int qty=0;
        viewShoppingCart();
        String v=driver.findElement(viewCartProductsQuantity).getText().trim();
        if (v.length()<1){

        }else {
             qty=Integer.valueOf(v);
        }
        return qty;
    }
    public SummeryPage proceedToCheckout(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(addedTOCartSuccessMSG));
        driver.findElement(proceedToCheckout).click();
        return new SummeryPage(driver);
    }
    public String getAddedTOCartSuccessMSG(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(addedTOCartSuccessMSG));
        return  driver.findElement(addedTOCartSuccessMSG).getText().trim();
    }
    public void returnToHomePage(){
        if (driver.findElement(homePage).isDisplayed())
            driver.findElement(homePage).click();
    }

}
