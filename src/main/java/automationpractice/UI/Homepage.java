package automationpractice.UI;

import base.General;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Homepage{
    WebDriver driver;
    private By shoppingCart= By.xpath("//div[@class='shopping_cart']/a");
    private By alertMsg= By.xpath("//p[contains(@class,'alert-warning')]");
    private By searchInput= By.id("search_query_top");

    public Homepage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement shoppingCart(){
        General gm =new General(driver);
        WebElement webElement=driver.findElement(shoppingCart);
        gm.waitToBeClickable(webElement,5);
        return webElement;
    }
    public WebElement alertMsg(){
        return driver.findElement(alertMsg);
    }
    public WebElement searchInput(){
        return driver.findElement(searchInput);
    }



}
