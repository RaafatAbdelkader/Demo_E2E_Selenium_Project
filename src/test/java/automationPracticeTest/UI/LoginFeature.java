package automationPracticeTest.UI;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.TestBase;
import java.io.IOException;

public class LoginFeature extends TestBase {
    String url=propReader.getUrl();
    String newEmail= null;
    String registeredEmail=null;
    String registeredPassword=null;
    public LoginFeature() throws IOException {
    }
    @Test(description = "to verify the ability of account creation with valid data")
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
                "YOUR PERSONAL INFORMATION","can't navigate to signup page");
        log.info("navigated to signupPage");
        signupPage.getGenderRadio(data.get("Gender*")).click();
        signupPage.getFirstName().sendKeys(data.get("Firstname*"));
        signupPage.getLastName().sendKeys(data.get("Lastname*"));
        String placeholder_Email =signupPage.getEmail().getAttribute("value");
        Assert.assertEquals(placeholder_Email,newEmail,
                "Placeholder email does not match the new email entered");
        String psw=data.get("Password*");
        signupPage.getPassword().sendKeys(psw);
        signupPage.selectDate(data.get("DateOfBirth*"));
        log.info("entered personal data ");
        signupPage.getNewsletter().click();
        signupPage.getSpecialOffers().click();
        String placeholder_AddressFirstname= signupPage.getAddressFirstname().getAttribute("value");
        Assert.assertEquals(placeholder_AddressFirstname,data.get("Firstname*"),"Wrong Placeholder");
        String placeholder_AddressLastname= signupPage.getAddressLastname().getAttribute("value");
        Assert.assertEquals(placeholder_AddressLastname,data.get("Lastname*"),"Wrong Placeholder");
        if (data.get("Company")!=null)
            signupPage.getCompany().sendKeys(data.get("Company"));
        signupPage.getAddress().sendKeys(data.get("Address*"));
        signupPage.getCity().sendKeys(data.get("City*"));
        signupPage.selectState(data.get("State*"));
        signupPage.getPostcode().sendKeys(data.get("ZipCode*"));
        Assert.assertEquals(signupPage.getDefaultSelectedCountry().getText(),data.get("Country*"),
                "the country selected by default does not match the requirement");
        if (data.get("MobilePhone*")!=null)
            signupPage.getMobilePhone().sendKeys(data.get("MobilePhone*"));
        else
            signupPage.getPhone().sendKeys(data.get("HomePhone"));

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
    @Test(dependsOnMethods = "createAccount",description = "Verify user is able to login with Login data created")
    public void loginWithValidData(){
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
}
