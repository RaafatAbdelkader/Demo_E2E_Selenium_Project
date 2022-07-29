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

    public WebElement navigateToLoginPage(){
        return driver.findElement(loginBTN);
    }
}
