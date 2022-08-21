package automationpractice.FE;

import base.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Header {
    private WebDriver driver;
    private ProjectActions projectActions;

    public Header( WebDriver driver){
        this.driver=driver;
        projectActions=new ProjectActions(driver);
    }

    private By loginBTN=By.className("login");
    private By searchInput= By.id("search_query_top");
    private By logoutBTN=By.className("logout");
    private By shoppingCart= By.xpath("//div[@class='shopping_cart']/a");
    private By headerText=By.xpath("//div[@class='header_user_info']/parent::nav/parent::div");
    private By women=By.xpath("//li/a[@title='Women']");
    private By summerDresses=By.xpath("//li/a[@title='Summer Dresses']");
    private By myAccount=By.xpath("//a[@class='account']");

    public LoginPage navigateToLoginPage(){
        if (isLoggedIn())
            logout();
       driver.findElement(loginBTN).click();
       return  new LoginPage(driver);
    }
    public boolean isLoggedIn(){
        return driver.findElement(headerText).getText().contains("Sign out");
    }
    public void logout(){
        driver.findElement(logoutBTN).click();
    }
    public void clickWomenBTN(){
        driver.findElement(women).click();
    }
    public SummerDressesPage navigateToSummerDressesPage(){
        Actions actions=new Actions(driver);
        actions.moveToElement(driver.findElement(women)).build().perform();
        projectActions.waitToBeClickable(driver.findElement(summerDresses),5);
         driver.findElement(summerDresses).click();
         return new SummerDressesPage(driver);
    }
    public MyAccountPage navigateToMyAccount(){
        if (isLoggedIn())
            driver.findElement(myAccount).click();
        return new MyAccountPage(driver);
    }
    public void openShoppingCart(){
        WebElement shoppingCartWE=driver.findElement(shoppingCart);
        projectActions.waitToBeClickable(shoppingCartWE,5);
        shoppingCartWE.click();
    }
    public WebElement searchInput(){
        return driver.findElement(searchInput);
    }

}
