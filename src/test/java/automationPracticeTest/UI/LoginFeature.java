package automationPracticeTest.UI;
import net.minidev.json.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.TestBase;
import java.io.IOException;

public class LoginFeature extends TestBase {
    String newEmail= null;
    String registeredEmail=null;
    String registeredPassword=null;

    public LoginFeature() throws IOException, ParseException {
    }


    @Test(description = "As an user I should be able to create an account using valid data", groups = "Smoke")
    public void createAccount() {
        driver.get(url);
        log.info("URL opened");
        newEmail= loginPage.getNewRandomEmail();
        header.navigateToLoginPage().click();
        Assert.assertEquals(loginPage.getHeadingMsg().getText(),"AUTHENTICATION","can't navigate to login page");
        log.info("navigated to LoginPage");
        loginPage.createAccount_NewEmail().sendKeys(newEmail);
        log.info("entered a new email to create an account ");
        loginPage.submit_createAccount().click();
        general.waitToBeClickable(signupPage.getSubHeadingMsg(),5);
        Assert.assertEquals(signupPage.getSubHeadingMsg().getText(),
                "YOUR PERSONAL INFORMATION","did not navigate to signup page");
        log.info("navigated to signupPage");
        Assert.assertFalse(signupPage.genderIsChecked("male"),"gender should be unchecked by default");
        Assert.assertFalse(signupPage.genderIsChecked("female"),"gender should be unchecked by default");
        signupPage.getGenderRadio(getRegisterTestData.get("Gender*")).click();
        signupPage.getFirstName().sendKeys(getRegisterTestData.get("Firstname*"));
        signupPage.getLastName().sendKeys(getRegisterTestData.get("Lastname*"));
        String placeholder_Email =signupPage.getEmail().getAttribute("value");
        Assert.assertEquals(placeholder_Email,newEmail,
                "Placeholder email does not match the new email entered");
        String psw= getRegisterTestData.get("Password*");
        signupPage.getPassword().sendKeys(psw);
        signupPage.selectDate(getRegisterTestData.get("DateOfBirth*"));
        log.info("entered personal data ");
        signupPage.getNewsletter().click();
        signupPage.getSpecialOffers().click();
        String placeholder_AddressFirstname= signupPage.getAddressFirstname().getAttribute("value");
        Assert.assertEquals(placeholder_AddressFirstname, getRegisterTestData.get("Firstname*"),"Wrong Placeholder");
        String placeholder_AddressLastname= signupPage.getAddressLastname().getAttribute("value");
        Assert.assertEquals(placeholder_AddressLastname, getRegisterTestData.get("Lastname*"),"Wrong Placeholder");
        if (getRegisterTestData.get("Company")!=null)
            signupPage.getCompany().sendKeys(getRegisterTestData.get("Company"));
        signupPage.getAddress().sendKeys(getRegisterTestData.get("Address*"));
        signupPage.getCity().sendKeys(getRegisterTestData.get("City*"));
        signupPage.selectState(getRegisterTestData.get("State*"));
        signupPage.getPostcode().sendKeys(getRegisterTestData.get("ZipCode*"));
        Assert.assertEquals(signupPage.getDefaultSelectedCountry().getText(), getRegisterTestData.get("Country*"),
                "the country selected by default does not match the requirement");
        if (getRegisterTestData.get("MobilePhone*")!=null)
            signupPage.getMobilePhone().sendKeys(getRegisterTestData.get("MobilePhone*"));
        else
            signupPage.getPhone().sendKeys(getRegisterTestData.get("HomePhone"));
        log.info("entered address and contact data");
        signupPage.getSubmitAccount().click();
        general.waitToBeClickable(myAccountPage.getPage_heading_msg(),5);
        String success_MSG=myAccountPage.getPage_heading_msg().getText();
        Assert.assertEquals(success_MSG,
                "Welcome to your account. Here you can manage all of your personal information and orders.",
                "Failed to create an account");
        log.info("Account created successfully");
        registeredEmail=newEmail;
        registeredPassword=psw;
    }

    @Test(dependsOnMethods = "createAccount",description = "The user is able to log in with Login data created", groups = "Smoke")
    public void loginWithDataCreated(){
        driver.get(url);
        log.info("URL opened");
        header.navigateToLoginPage().click();
        log.info("navigated to login page");
        loginPage.getLoginEmail().sendKeys(registeredEmail);
        log.info("entered an email");
        loginPage.getLoginPsw().sendKeys(registeredPassword);
        log.info("entered an Password");
        loginPage.getSubmitLogin().click();
        log.info("Clicked on submit");
        general.waitToBeClickable(myAccountPage.getPage_heading_msg(),5);
        String success_MSG=myAccountPage.getPage_heading_msg().getText();
        Assert.assertEquals(success_MSG,
                "Welcome to your account. Here you can manage all of your personal information and orders.",
                "Failed to login with valid data");
        log.info("user logged in successfully");
    }

    @Test(dependsOnMethods = "createAccount",description = "An user should not be able to create an account using an email already registered before")
    public void accountCreationUsingRegisteredEmail(){
        driver.get(url);
        log.info("URL opened");
        header.navigateToLoginPage().click();
        log.info("navigated to LoginPage");
        loginPage.createAccount_NewEmail().sendKeys(registeredEmail);
        log.info("Entered a registered email");
        loginPage.submit_createAccount().click();
        log.info("submitted account creation");
        general.waitToBeClickable(loginPage.getErrorMsg(),5);
        Assert.assertEquals(loginPage.getErrorMsg().getText(),
               "An account using this email address has already been registered. Please enter a valid password or request a new one.",
                "user should not be able to create an account using an email already registered before");
        log.info("Error message displayed");
        loginPage.createAccount_NewEmail().clear();
        loginPage.createAccount_NewEmail().sendKeys(loginPage.getNewRandomEmail());
        log.info("entered an new random email");
        loginPage.submit_createAccount().click();
        log.info("submitted account creation");
        signupPage.getEmail().clear();
        signupPage.getEmail().sendKeys(registeredEmail);
        log.info("change the default email value to a registered email after navigating to signup page");
        signupPage.getSubmitAccount().click();
        log.info("submitted account creation");
        Assert.assertTrue(signupPage.getCreateAccountErrorMSG().getText()
                .contains("An account using this email address has already been registered."),
                "user should not be able to change the default email value to a registered email after navigating to signup page");
        log.info("Error message displayed");
    }

    @Test(dataProvider = "getInvalidPersonalData",description = "As an user I should not be able to create an account using invalid PersonalData")
    public void verifyAccountCreationUsingInvalidPersonalData(
                            String firstname,String firstname_errorMSG, String lastname,String lastname_errorMSG,
                            String email,String email_errorMSG,String psw,String psw_errorMSG,String dateOfBirth,
                            String dateOfBirth_errorMsg){
        driver.get(url);
        log.info("URL opened");
        header.navigateToLoginPage().click();
        loginPage.createAccount_NewEmail().sendKeys(loginPage.getNewRandomEmail());
        log.info("entered an new random email");
        loginPage.submit_createAccount().click();
        general.waitToBeClickable(signupPage.getSubHeadingMsg(),5);
        Assert.assertEquals(signupPage.getSubHeadingMsg().getText(),
                "YOUR PERSONAL INFORMATION","did not navigate to signup page");
        log.info("Navigated to signup page");
        signupPage.getEmail().clear();
        signupPage.getGenderRadio("male").click();
        signupPage.getGenderRadio("female").click();
        Assert.assertFalse(signupPage.genderIsChecked("male"),
                "the user should not be able to select both of gender types at same time");
        signupPage.getFirstName().sendKeys(firstname);
        signupPage.getLastName().sendKeys(lastname);
        signupPage.getEmail().sendKeys(email);
        signupPage.getPassword().sendKeys(psw);
        signupPage.selectDate(dateOfBirth);
        log.info("entered invalid personal data");
        signupPage.getSubmitAccount().click();
        String actualErrorMSG=signupPage.getCreateAccountErrorMSG().getText();
        String assertionMSG="The following error message: -M- is not contained in the following actual message:"+actualErrorMSG;
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

    @Test(dataProvider = "getAllTestUsers")
    public void loginVerification(String username,String password,String status, String expectedMSG){
        driver.get(url);
        log.info("URL opened");
        header.navigateToLoginPage().click();
        log.info("navigated to login page");
        loginPage.getLoginEmail().sendKeys(username);
        log.info("entered an username");
        loginPage.getLoginPsw().sendKeys(password);
        log.info("entered a password");
        loginPage.getSubmitLogin().click();
        log.info("submitted login");
        if (status.equalsIgnoreCase("valid")) {
            general.waitToBeClickable(myAccountPage.getPage_heading_msg(),5);
            Assert.assertEquals(myAccountPage.getPage_heading_msg().getText(),
                    expectedMSG, "Not able to login" );
            log.info("logged in successfully with valid data");
        }else {
            general.waitToBeClickable(loginPage.getErrorMsg(),5);
            Assert.assertTrue(loginPage.getErrorMsg().getText().contains(expectedMSG),
                    "Message verification failed. expected:"+expectedMSG+" but found: "+ loginPage.getErrorMsg());
            log.info("can not log in using invalid data");
        }
    }

    @Test
    public void newTab(){

    }

}
