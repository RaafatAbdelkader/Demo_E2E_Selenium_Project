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

}
