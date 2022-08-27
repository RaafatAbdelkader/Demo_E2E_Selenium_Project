package automationpractice.FE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SignupPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static Logger log = LogManager.getLogger(SignupPage.class.getName());
    private By subHeadingMsg =By.xpath("//label[contains(text(),'Title')]/parent:: div/preceding-sibling:: h3[@class='page-subheading']");
    private By gender_radio_male= By.xpath("//input[@id='id_gender1']/parent::span");
    private By gender_radio_female=  By.xpath("//input[@id='id_gender2']/parent::span");
    private By firstname=  By.id("customer_firstname");
    private By lastname=  By.id("customer_lastname");
    private By email=  By.xpath("//div[@class='account_creation'] //input[@id='email']");
    private By psw=  By.id("passwd");
    private By days= By.id("days");
    private By months= By.id("months");
    private By years= By.id("years");
    private By newsletter=By.id("newsletter");
    private By specialOffers=By.id("optin");
    private By addressFirstname=  By.xpath("//p[@class='required form-group']/child::input[@id='firstname']");
    private By addressLastname=  By.xpath("//p[@class='required form-group']/child::input[@id='lastname']");
    private By address=By.id("address1");
    private By addressLine2=By.id("address2");
    private By city=By.id("city");
    private By company=By.id("company");
    private By state=By.id("id_state"); //select
    private By postcode=By.id("postcode");
    private By country=By.id("id_country");
    private By additionalInfo=By.id("other");
    private By phone=By.id("phone");
    private By mobilePhone=By.id("phone_mobile");
    private By addressAlias=By.id("alias");
    private By submitAccount=By.id("submitAccount");
    private By create_account_error=By.xpath("//div[contains(@class,'alert')]");

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public String getSubHeadingMsg() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(subHeadingMsg));
        return driver.findElement(subHeadingMsg).getText();
    }
    public String getCreateAccountErrorMSG() {
        return driver.findElement(create_account_error).getText();
    }
    public void selectGender(String gender){
        if (gender.equalsIgnoreCase("male")) {
            driver.findElement(gender_radio_male).click();
        }else if (gender.equalsIgnoreCase("female"))
                driver.findElement(gender_radio_female).click();
        else
            System.out.println("Please provide a specific gender Male/ Female");
        log.info("Selected gender as: "+gender);
    }
    public void setFirstName(String firstName) {
        driver.findElement(firstname).sendKeys(firstName);
        log.info("set firstname as: "+firstName);
    }
    public void setLastName(String lastName) {
         driver.findElement(lastname).sendKeys(lastName);
        log.info("set lastName as: "+lastName);
    }
    public String getEmailPlaceholder() {
        return driver.findElement(email).getAttribute("value");
    }
    public void changeDefaultEmail(String nEmail) {
      driver.findElement(email).clear();
      driver.findElement(email).sendKeys(nEmail);
      log.info("changed the default email to: "+nEmail);
    }
    public void setPassword(String password) {
        driver.findElement(psw).sendKeys(password);
        log.info("set password as: "+password);
    }
    //the date should be provided in this Format:"dd/mm/yyyy"
    public void selectDate(String date) {
        System.out.println(date);
        String[]dateValues=date.split("/");
        for (int i = 0; i < dateValues.length; i++) {
            WebElement  element;
            if (i==0)
                element = driver.findElement(days);
            else if (i==1)
                element = driver.findElement(months);
            else
                element= driver.findElement(years);
            Select select =new Select(element);
            select.selectByValue(dateValues[i]);
        }
        log.info("Date: "+date+ " selected");
    }
    public void selectNewsletter() {
        driver.findElement(newsletter).click();
        log.info("selected news letter");
    }
    public void selectSpecialOffers() {
        driver.findElement(specialOffers).click();
        log.info("selected special offers");
    }
    public void setAddressFirstname(String addressFirstnameV) {
        driver.findElement(addressFirstname).sendKeys(addressFirstnameV);
        log.info("set address firstname as: "+addressFirstnameV);
    }
    public String getAddressFirstnamePlaceholder() {
        return driver.findElement(addressFirstname).getAttribute("value");
    }
    public String getAddressLastnamePlaceholder() {
        return driver.findElement(addressLastname).getAttribute("value");
    }
    public void setAddressLastname(String addressLastnameV) {
         driver.findElement(addressLastname).sendKeys(addressLastnameV);
         log.info("set address lastname as: "+addressLastnameV);
    }
    public void setAddress(String addressV) {
        driver.findElement(address).sendKeys(addressV);
        log.info("set address as: "+addressV);
    }
    public void setCity(String cityName) {
        driver.findElement(city).sendKeys(cityName);
        log.info("set cityName as: "+cityName);
    }
    public void setCompany(String companyName) {
        driver.findElement(company).sendKeys(companyName);
        log.info("set companyName as: "+companyName);
    }
    public void selectState(String stateName) {
        Select select= new Select(driver.findElement(state));
        select.selectByVisibleText(stateName);
        log.info("selected stateName: "+stateName);
    }
    public void selectCountry(String vText) {
        Select select= new Select(driver.findElement(country));
        select.selectByVisibleText(vText);
        log.info("selected country: "+vText);
    }
    public String getDefaultSelectedCountry() {
        Select select= new Select(driver.findElement(country));
        return   select.getFirstSelectedOption().getText();
    }
    public void setPostcode(String postalCode) {
        driver.findElement(postcode).sendKeys(postalCode);
    }
    public void setCountry(String countryName) {
        driver.findElement(country).sendKeys(countryName);
        log.info("set country as: "+countryName);
    }
    public void setAdditionalInfo(String info) {
        driver.findElement(additionalInfo).sendKeys(info);
        log.info("set additionalInfo as: "+info);
    }
    public void setPhone(String phoneV) {
        driver.findElement(phone).sendKeys(phoneV);
        log.info("set phone as: "+phoneV);
    }
    public void setMobilePhone(String mobilePhoneV) {
        driver.findElement(mobilePhone).sendKeys(mobilePhoneV);
        log.info("set mobile phone as: "+mobilePhoneV);
    }
    public void setAddressAlias(String addressAliasV) {
        driver.findElement(addressAlias).sendKeys(addressAliasV);
        log.info("set address alias as: "+addressAliasV);
    }
    public void getSubmitAccount() {
       driver.findElement(submitAccount).click();
       log.info("submitted account creation ");
    }
    public boolean genderIsChecked(String gender){
        if (gender.equalsIgnoreCase("male"))
            return driver.findElement(gender_radio_male).getCssValue("class").contains("checked");
        else
            return driver.findElement(gender_radio_female).getCssValue("class").contains("checked");
    }

}
