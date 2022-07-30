package automationpractice.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Header {
    WebDriver driver;
    public Header( WebDriver driver){
        this.driver=driver;
    }
    private By loginBTN=By.className("login");
    private By logoutBTN=By.className("logout");
    private By headerText=By.xpath("//div[@class='header_user_info']/parent::nav/parent::div");
    public WebElement navigateToLoginPage(){
        if (alreadyLoggedIn())
            getLogoutBTN().click();
        return driver.findElement(loginBTN);
    }
    public boolean alreadyLoggedIn(){
        return driver.findElement(headerText).getText().contains("Sign out");
    }
    public WebElement getLogoutBTN(){
        return driver.findElement(logoutBTN);
    }
}
