package automationpractice.FE;

import basePg.MyLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By createAccount_NewEmail =By.id("email_create");
    private By headingMsg =By.className("page-heading");
    private By submit_createAccount =By.id("SubmitCreate");
    private By loginEmail =By.id("email");
    private By loginPsw =By.id("passwd");
    private By submit_login=By.id("SubmitLogin");
    private By errorMsg=By.xpath("//div[contains(@class,'alert')]");

    public LoginPage( WebDriver driver){
         this.driver=driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
     }

    public String getNewRandomEmail(){
        String newEmail="testX@gmail.com";
        Random r=new Random();
        newEmail=newEmail.replace("X",String.valueOf(r.nextInt(1000,100000)));
        MyLogger.info("Created a new  random email: "+newEmail);
        return newEmail;
    }
    public void createAccount_enterNewEmail(String newEmail){
        wait.until(ExpectedConditions.visibilityOfElementLocated(createAccount_NewEmail));
        WebElement el=driver.findElement(createAccount_NewEmail);
        el.clear();
        el.sendKeys(newEmail);
        MyLogger.info("Set "+newEmail+" as a new email to create an account");
     }
    public String getHeadingMsg() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(headingMsg));
        return driver.findElement(headingMsg).getText();
    }
    public SignupPage navigateToSignupPage(){
       wait.until(ExpectedConditions.visibilityOfElementLocated(submit_createAccount));
       driver.findElement(submit_createAccount).click();
       MyLogger.info("Navigated to signup page");
       return new SignupPage(driver);
    }
    public void setLoginUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginEmail));
        driver.findElement(loginEmail).sendKeys(username);
        MyLogger.info("set username as: "+username);
    }
    public void setLoginPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginPsw));
        driver.findElement(loginPsw).sendKeys(password);
        MyLogger.info("set password as: "+password);
    }
    public void submitLogin() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(submit_login));
        driver.findElement(submit_login).click();
        MyLogger.info("submitted login");
    }
    public String getErrorMsg() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));
        return driver.findElement(errorMsg).getText();
    }
    public void login(String username,String psw){
        setLoginUsername(username);
        setLoginPassword(psw);
        submitLogin();
    }


}
