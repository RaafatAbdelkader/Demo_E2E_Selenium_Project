package rediff.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;
    public LoginPage(WebDriver driver){
        this.driver=driver;
    }
    private By usernameInput= By.id("user_email");
    private By pwdInput= By.id("user_password");
    private By submitLogin=By.xpath("//input[@type='submit']");
    private By msg=By.xpath("//div[@role='alert']");

    public WebElement username(){
        return driver.findElement(usernameInput);
    }
    public WebElement password(){
        return driver.findElement(pwdInput);
    }
    public WebElement submit(){
        return driver.findElement(submitLogin);
    }
    public String getAlertMsg(){
        return driver.findElement(msg).getText();
    }

}
