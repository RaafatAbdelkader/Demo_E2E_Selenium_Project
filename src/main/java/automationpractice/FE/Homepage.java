package automationpractice.FE;

import base.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Homepage{
    private WebDriver driver;
    private By alertMsg= By.xpath("//p[contains(@class,'alert-warning')]");
    public Homepage(WebDriver driver) {
        this.driver = driver;
    }
    public String alertMsg(){
        return driver.findElement(alertMsg).getText();
    }




}
