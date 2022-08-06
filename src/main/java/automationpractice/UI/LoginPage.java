package automationpractice.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.Random;

public class LoginPage {
     WebDriver driver;
    private By createAccount_NewEmail =By.id("email_create");
    private By headingMsg =By.className("page-heading");
    private By submit_createAccount =By.id("SubmitCreate");

    private By loginEmail =By.id("email");
    private By loginPsw =By.id("passwd");
    private By submit_login=By.id("SubmitLogin");
    private By errorMsg=By.xpath("//div[contains(@class,'alert')]");


    public LoginPage( WebDriver driver){
         this.driver=driver;
     }

    public String getNewRandomEmail(){
        String newEmail="testX@gmail.com";
        Random r=new Random();
        return newEmail.replace("X",String.valueOf(r.nextInt(1000,100000)));
    }
    public WebElement createAccount_NewEmail(){
        return driver.findElement(createAccount_NewEmail);
     }
    public WebElement getHeadingMsg() {
        return driver.findElement(headingMsg);
    }
    public WebElement submit_createAccount(){
       return driver.findElement(submit_createAccount);
    }
    public WebElement getLoginEmail() {
        return driver.findElement(loginEmail);
    }
    public WebElement getLoginPsw() {
        return driver.findElement(loginPsw);
    }
    public WebElement getSubmitLogin() {
        return driver.findElement(submit_login);
    }
    public WebElement getErrorMsg() {
        return driver.findElement(errorMsg);
    }

    public void login(String username,String psw){
        getLoginEmail().sendKeys(username);
        getLoginPsw().sendKeys(psw);
        getSubmitLogin().click();
    }


}
