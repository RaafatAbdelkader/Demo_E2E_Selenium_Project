package automationPracticeTest.FE;
import automationpractice.FE.LoginPage;
import automationpractice.FE.MyAccountPage;
import automationpractice.FE.SignupPage;
import basePg.MyLogger;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.TestBase;

import java.lang.reflect.Method;

public class LoginFeature extends TestBase {
    String newEmail= null;
    String registeredEmail=null;
    String registeredPassword=null;
    LoginPage loginPage;
    SignupPage signupPage;
    MyAccountPage myAccountPage;

    @Test(dataProvider = "getAllTestUsers",priority = 1,groups = "Smoke")
    public void loginValidation(String username,String password,String status, String expectedMSG){
        loginPage=header.navigateToLoginPage();
        loginPage.setLoginUsername(username);
        loginPage.setLoginPassword(password);
        loginPage.submitLogin();
        myAccountPage=header.navigateToMyAccount();
        if (status.equalsIgnoreCase("valid")) {
            Assert.assertEquals(myAccountPage.getPage_heading_msg(),
                    expectedMSG, "Not able to login" );
        }else {
            Assert.assertTrue(loginPage.getErrorMsg().contains(expectedMSG),
                    "Message verification failed. expected:"+expectedMSG+" but found: "+ loginPage.getErrorMsg());
        }
    }

    @Test(priority = 1,groups = "Smoke", description = "As an user I should be able to create an account using valid data")
    public void createAccountValidation() {
        loginPage=header.navigateToLoginPage();
        newEmail= loginPage.getNewRandomEmail();
        loginPage.createAccount_enterNewEmail(newEmail);
        signupPage=loginPage.navigateToSignupPage();
        Assert.assertEquals(signupPage.getSubHeadingMsg(),
                "YOUR PERSONAL INFORMATION");
        Assert.assertFalse(signupPage.genderIsChecked("male"),"gender should be unchecked by default");
        Assert.assertFalse(signupPage.genderIsChecked("female"),"gender should be unchecked by default");
        signupPage.selectGender(registerTData.get("Gender*"));
        signupPage.setFirstName(registerTData.get("Firstname*"));
        signupPage.setLastName(registerTData.get("Lastname*"));
        String placeholder_Email =signupPage.getEmailPlaceholder();
        Assert.assertEquals(placeholder_Email,newEmail,
                "Placeholder email does not match the new email entered");
        String psw= registerTData.get("Password*");
        signupPage.setPassword(psw);
        signupPage.selectDate(registerTData.get("DateOfBirth*"));

        signupPage.selectNewsletter();
        signupPage.selectSpecialOffers();
        Assert.assertEquals(signupPage.getAddressFirstnamePlaceholder(), registerTData.get("Firstname*"),"Wrong Placeholder");
        Assert.assertEquals(signupPage.getAddressLastnamePlaceholder(), registerTData.get("Lastname*"),"Wrong Placeholder");
        if (registerTData.get("Company")!=null)
            signupPage.setCompany(registerTData.get("Company"));
        signupPage.setAddress(registerTData.get("Address*"));
        signupPage.setCity(registerTData.get("City*"));
        signupPage.selectState(registerTData.get("State*"));
        signupPage.setPostcode(registerTData.get("ZipCode*"));
        Assert.assertEquals(signupPage.getDefaultSelectedCountry(), registerTData.get("Country*"),
                "the country selected by default does not match the requirement");
        if (registerTData.get("MobilePhone*")!=null)
            signupPage.setMobilePhone(registerTData.get("MobilePhone*"));
        else
            signupPage.setPhone(registerTData.get("HomePhone"));
        signupPage.getSubmitAccount();
        myAccountPage=header.navigateToMyAccount();
        String success_MSG=myAccountPage.getPage_heading_msg();
        Assert.assertEquals(success_MSG,
                "Welcome to your account. Here you can manage all of your personal information and orders.",
                "Failed to create an account");
        registeredEmail=newEmail;
        registeredPassword=psw;
    }

    @Test(dependsOnMethods = "createAccountValidation",priority = 1,description = "The user is able to log in with Login data created", groups = "Smoke")
    public void loginWithDataCreated(){
        loginPage=header.navigateToLoginPage();
        loginPage.login(registeredEmail,registeredPassword);
        myAccountPage=header.navigateToMyAccount();
        String success_MSG=myAccountPage.getPage_heading_msg();
        Assert.assertEquals(success_MSG,
                "Welcome to your account. Here you can manage all of your personal information and orders.",
                "Failed to login with valid data");
    }

    @Test(dependsOnMethods = "createAccountValidation",priority = 2,description = "An user should not be able to create an account using an email already registered before")
    public void accountCreationUsingRegisteredEmail(){
        loginPage=header.navigateToLoginPage();
        loginPage.createAccount_enterNewEmail(registeredEmail);
        signupPage=loginPage.navigateToSignupPage();
        Assert.assertEquals(loginPage.getErrorMsg(),
               "An account using this email address has already been registered. Please enter a valid password or request a new one.",
                "user should not be able to create an account using an email already registered before");
        loginPage.createAccount_enterNewEmail(loginPage.getNewRandomEmail());
        loginPage.navigateToSignupPage();
        signupPage.changeDefaultEmail(registeredEmail);
        signupPage.getSubmitAccount();
        Assert.assertTrue(signupPage.getCreateAccountErrorMSG()
                .contains("An account using this email address has already been registered."),
                "user should not be able to change the default email value to a registered email after navigating to signup page");
    }

    @Test(dataProvider = "getInvalidPersonalData",priority = 2,description = "As an user I should not be able to create an account using invalid PersonalData")
    public void accountCreationUsingInvalidPersonalData(
                            String firstname,String firstname_errorMSG, String lastname,String lastname_errorMSG,
                            String email,String email_errorMSG,String psw,String psw_errorMSG,String dateOfBirth,
                            String dateOfBirth_errorMsg){
        loginPage= header.navigateToLoginPage();
        loginPage.createAccount_enterNewEmail(loginPage.getNewRandomEmail());
        signupPage=loginPage.navigateToSignupPage();
        Assert.assertEquals(signupPage.getSubHeadingMsg(),
                "YOUR PERSONAL INFORMATION","did not navigate to signup page");
        signupPage.selectGender("male");
        signupPage.selectGender("female");
        Assert.assertFalse(signupPage.genderIsChecked("male"),
                "the user should not be able to select both of gender types at same time");
        signupPage.setFirstName(firstname);
        signupPage.setLastName(lastname);
        signupPage.changeDefaultEmail(email);
        signupPage.setPassword(psw);
        signupPage.selectDate(dateOfBirth);
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
    }


    @Test
    public void test(){
        MyLogger.startTC(new Throwable().getStackTrace()[0].getClassName(),new Throwable().getStackTrace()[0].getMethodName());
        MyLogger.info("test1 ");
        MyLogger.info("test2 ");
        MyLogger.info("test3 ");
        MyLogger.endTC(new Throwable().getStackTrace()[0].getMethodName());
    }
    @Test
    public void validation(){
        MyLogger.startTC(new Throwable().getStackTrace()[0].getClassName(),new Throwable().getStackTrace()[0].getMethodName());
        MyLogger.info("validation 1 ");
        MyLogger.info("validation 2 ");
        MyLogger.info("validation 3 ");
        MyLogger.endTC(new Throwable().getStackTrace()[0].getMethodName());
    }

    //https://www.youtube.com/watch?v=0JQekot_5V8
}
