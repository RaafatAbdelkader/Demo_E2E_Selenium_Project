package automationPracticeTest.FE;
import automationpractice.FE.LoginPage;
import automationpractice.FE.MyAccountPage;
import automationpractice.FE.SignupPage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.TestBase;

import java.io.*;

public class LoginFeature extends TestBase {
    String newEmail= null;
    String registeredEmail=null;
    String registeredPassword=null;
    LoginPage loginPage;
    SignupPage signupPage;
    MyAccountPage myAccountPage;

    @Test(priority = 1,groups = "Smoke", description = "As an user I should be able to create an account using valid data")
    public void createAccount() {
        loginPage=header.navigateToLoginPage();
        newEmail= loginPage.getNewRandomEmail();
        Assert.assertEquals(loginPage.getHeadingMsg(),"AUTHENTICATION","can't navigate to login page");
        log.info("navigated to LoginPage");
        loginPage.createAccount_enterNewEmail(newEmail);
        log.info("entered a new email to create an account ");
        signupPage=loginPage.navigateToSignupPage();
        Assert.assertEquals(signupPage.getSubHeadingMsg(),
                "YOUR PERSONAL INFORMATION","did not navigate to signup page");
        log.info("navigated to signupPage");
        Assert.assertFalse(signupPage.genderIsChecked("male"),"gender should be unchecked by default");
        Assert.assertFalse(signupPage.genderIsChecked("female"),"gender should be unchecked by default");
        signupPage.selectGender(getRegisterTestData.get("Gender*"));
        signupPage.setFirstName(getRegisterTestData.get("Firstname*"));
        signupPage.setLastName(getRegisterTestData.get("Lastname*"));

        String placeholder_Email =signupPage.getEmailPlaceholder();
        Assert.assertEquals(placeholder_Email,newEmail,
                "Placeholder email does not match the new email entered");
        String psw= getRegisterTestData.get("Password*");
        signupPage.setPassword(psw);
        signupPage.selectDate(getRegisterTestData.get("DateOfBirth*"));
        log.info("entered personal data ");
        signupPage.selectNewsletter();
        signupPage.selectSpecialOffers();
        Assert.assertEquals(signupPage.getAddressFirstnamePlaceholder(),getRegisterTestData.get("Firstname*"),"Wrong Placeholder");
        Assert.assertEquals(signupPage.getAddressLastnamePlaceholder(), getRegisterTestData.get("Lastname*"),"Wrong Placeholder");
        if (getRegisterTestData.get("Company")!=null)
            signupPage.setCompany(getRegisterTestData.get("Company"));
        signupPage.setAddress(getRegisterTestData.get("Address*"));
        signupPage.setCity(getRegisterTestData.get("City*"));
        signupPage.selectState(getRegisterTestData.get("State*"));
        signupPage.setPostcode(getRegisterTestData.get("ZipCode*"));
        Assert.assertEquals(signupPage.getDefaultSelectedCountry(), getRegisterTestData.get("Country*"),
                "the country selected by default does not match the requirement");
        if (getRegisterTestData.get("MobilePhone*")!=null)
            signupPage.setMobilePhone(getRegisterTestData.get("MobilePhone*"));
        else
            signupPage.setPhone(getRegisterTestData.get("HomePhone"));
        log.info("entered address and contact data");
        signupPage.getSubmitAccount();
        myAccountPage=header.navigateToMyAccount();
        String success_MSG=myAccountPage.getPage_heading_msg();
        Assert.assertEquals(success_MSG,
                "Welcome to your account. Here you can manage all of your personal information and orders.",
                "Failed to create an account");
        log.info("Account created successfully");
        registeredEmail=newEmail;
        registeredPassword=psw;
    }

    @Test(dependsOnMethods = "createAccount",priority = 1,description = "The user is able to log in with Login data created", groups = "Smoke")
    public void loginWithDataCreated(){
        loginPage=header.navigateToLoginPage();
        log.info("navigated to login page");
        loginPage.login(registeredEmail,registeredPassword);
        log.info("Login submitted");
        myAccountPage=header.navigateToMyAccount();
        String success_MSG=myAccountPage.getPage_heading_msg();
        Assert.assertEquals(success_MSG,
                "Welcome to your account. Here you can manage all of your personal information and orders.",
                "Failed to login with valid data");
        log.info("user logged in successfully");
    }

    @Test(dependsOnMethods = "createAccount",description = "An user should not be able to create an account using an email already registered before")
    public void accountCreationUsingRegisteredEmail(){
        loginPage=header.navigateToLoginPage();
        log.info("navigated to LoginPage");
        loginPage.createAccount_enterNewEmail(registeredEmail);
        log.info("Entered a registered email");
        signupPage=loginPage.navigateToSignupPage();
        log.info("submitted account creation");

        Assert.assertEquals(loginPage.getErrorMsg(),
               "An account using this email address has already been registered. Please enter a valid password or request a new one.",
                "user should not be able to create an account using an email already registered before");
        log.info("Error message displayed");
        loginPage.createAccount_enterNewEmail(loginPage.getNewRandomEmail());
        log.info("entered an new random email");
        loginPage.navigateToSignupPage();
        log.info("submitted account creation");
        signupPage.changeDefaultEmail(registeredEmail);
        log.info("change the default email value to a registered email after navigating to signup page");
        signupPage.getSubmitAccount();
        log.info("submitted account creation");
        Assert.assertTrue(signupPage.getCreateAccountErrorMSG()
                .contains("An account using this email address has already been registered."),
                "user should not be able to change the default email value to a registered email after navigating to signup page");
        log.info("Error message displayed");
    }

    @Test(dataProvider = "getInvalidPersonalData",description = "As an user I should not be able to create an account using invalid PersonalData")
    public void accountCreationUsingInvalidPersonalData(
                            String firstname,String firstname_errorMSG, String lastname,String lastname_errorMSG,
                            String email,String email_errorMSG,String psw,String psw_errorMSG,String dateOfBirth,
                            String dateOfBirth_errorMsg) throws InterruptedException {
        loginPage= header.navigateToLoginPage();
        loginPage.createAccount_enterNewEmail(loginPage.getNewRandomEmail());
        log.info("entered an new random email");
        signupPage=loginPage.navigateToSignupPage();
        Assert.assertEquals(signupPage.getSubHeadingMsg(),
                "YOUR PERSONAL INFORMATION","did not navigate to signup page");
        log.info("Navigated to signup page");
        signupPage.selectGender("male");
        signupPage.selectGender("female");
        Assert.assertFalse(signupPage.genderIsChecked("male"),
                "the user should not be able to select both of gender types at same time");
        signupPage.setFirstName(firstname);
        signupPage.setLastName(lastname);
        signupPage.changeDefaultEmail(email);
        signupPage.setPassword(psw);
        signupPage.selectDate(dateOfBirth);
        log.info("entered invalid personal data");
        signupPage.getSubmitAccount();
        String actualErrorMSG=signupPage.getCreateAccountErrorMSG();
        String assertionMSG="The error message: -M- should be contained in the following actual message:"+actualErrorMSG;
        Assert.assertTrue(actualErrorMSG.contains(firstname_errorMSG),
                assertionMSG.replace("-M-",firstname_errorMSG));
        Assert.assertTrue(actualErrorMSG.contains(lastname_errorMSG),
                assertionMSG.replace("-M-",lastname_errorMSG));
        Assert.assertTrue(actualErrorMSG.contains(email_errorMSG),
                assertionMSG.replace("-M-",email_errorMSG));
        Assert.assertTrue(actualErrorMSG.contains(psw_errorMSG),
                assertionMSG.replace("-M-",psw_errorMSG));
        Assert.assertTrue(actualErrorMSG.contains(dateOfBirth_errorMsg),
                assertionMSG.replace("-M-",dateOfBirth_errorMsg));
        log.info("all expected messages have been verified");
    }
    @Test(dataProvider = "getAllTestUsers",priority = 1,groups = "Smoke")
    public void loginValidation(String username,String password,String status, String expectedMSG){
        loginPage=header.navigateToLoginPage();
        log.info("navigated to login page");
        loginPage.enterLoginUsername(username);
        log.info("entered an username");
        loginPage.enterLoginPassword(password);
        log.info("entered a password");
        loginPage.submitLogin();
        log.info("submitted login");
        myAccountPage=header.navigateToMyAccount();
        if (status.equalsIgnoreCase("valid")) {
            Assert.assertEquals(myAccountPage.getPage_heading_msg(),
                    expectedMSG, "Not able to login" );
            log.info("logged in successfully with valid data");
        }else {
            Assert.assertTrue(loginPage.getErrorMsg().contains(expectedMSG),
                    "Message verification failed. expected:"+expectedMSG+" but found: "+ loginPage.getErrorMsg());
            log.info("can not log in using invalid data");
        }
    }


}
