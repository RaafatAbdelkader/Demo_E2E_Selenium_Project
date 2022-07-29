package automationPracticeTest.UI;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.TestBase;

import java.io.IOException;

public class LoginFeature extends TestBase {
    String url=reader.getUrl();
    public LoginFeature() throws IOException {

    }

    @Test(description = "verifies user is able to create with valid data")
    public void createAccount() {
        driver.get(url);
        log.info("URL opened");
        header.navigateToLoginPage().click();
        Assert.assertEquals(loginPage.getHeadingMsg().getText(),"AUTHENTICATION","can't navigate to login page");
        log.info("navigated to LoginPage");
        String newEmail=loginPage.getNewRandomEmail();
        loginPage.createAccount_NewEmail().sendKeys(newEmail);
        log.info("entered a new email to create an account ");
        loginPage.submit_createAccount().click();
        general.waitToBeClickable(signupPage.getSubHeadingMsg(),5);
        Assert.assertEquals(signupPage.getSubHeadingMsg().getText(),"YOUR PERSONAL INFORMATION","can't navigate to signup page");
        log.info("navigated to signupPage");
        signupPage.getGenderRadio(data.get("Gender*")).click();
        signupPage.getFirstName().sendKeys(data.get("Firstname*"));
        signupPage.getLastName().sendKeys(data.get("Lastname*"));
        String placeholder_Email =signupPage.getEmail().getAttribute("value");
        Assert.assertEquals(placeholder_Email,newEmail,"Placeholder email does not match the new email entered");
        signupPage.getPassword().sendKeys(data.get("Password*"));
        //System.out.println();
        signupPage.selectDate(data.get("DateOfBirth*"));
        log.info("entered personal data ");

    }












    //    @Test(groups = "Smoke")
//    public void getLog() {
//            log.trace("trace");
//            log.debug("Debug");
//            log.info("info");
//            log.warn("warn");
//            log.error("error");
//            log.fatal("fatal");
//
//    }



//
}
