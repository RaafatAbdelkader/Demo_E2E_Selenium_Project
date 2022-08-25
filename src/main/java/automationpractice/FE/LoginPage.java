package automationpractice.FE;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Random;

public class LoginPage {
    private WebDriver driver;
    private ProjectActions projectActions;

    private By createAccount_NewEmail =By.id("email_create");
    private By headingMsg =By.className("page-heading");
    private By submit_createAccount =By.id("SubmitCreate");
    private By loginEmail =By.id("email");
    private By loginPsw =By.id("passwd");
    private By submit_login=By.id("SubmitLogin");
    private By errorMsg=By.xpath("//div[contains(@class,'alert')]");

    public LoginPage( WebDriver driver){
         this.driver=driver;
        projectActions=new ProjectActions(driver);
     }

    public String getNewRandomEmail(){
        String newEmail="testX@gmail.com";
        Random r=new Random();
        return newEmail.replace("X",String.valueOf(r.nextInt(1000,100000)));
    }
    public void createAccount_enterNewEmail(String newEmail){
        WebElement el=driver.findElement(createAccount_NewEmail);
        el.clear();
        el.sendKeys(newEmail);
     }
    public String getHeadingMsg() {
        return driver.findElement(headingMsg).getText();
    }
    public SignupPage navigateToSignupPage(){
       driver.findElement(submit_createAccount).click();
       return new SignupPage(driver);
    }
    public void enterLoginUsername(String username) {
        driver.findElement(loginEmail).sendKeys(username);
    }
    public void enterLoginPassword(String password) {
        driver.findElement(loginPsw).sendKeys(password);
    }
    public void submitLogin() {
       driver.findElement(submit_login).click();
    }
    public String getErrorMsg() {
        projectActions.waitToBeClickable(driver.findElement(errorMsg),5);
        return driver.findElement(errorMsg).getText();
    }
    public void login(String username,String psw){
        enterLoginUsername(username);
        enterLoginPassword(psw);
        submitLogin();
    }


}
