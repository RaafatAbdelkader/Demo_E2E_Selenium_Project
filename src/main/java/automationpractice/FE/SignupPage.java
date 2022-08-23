package automationpractice.FE;

import base.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SignupPage {
    private WebDriver driver;
    private ProjectActions projectActions;
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
        projectActions=new ProjectActions(driver);
    }
    public String getSubHeadingMsg() {
        projectActions.waitToBeClickable(driver.findElement(subHeadingMsg),5);
        return driver.findElement(subHeadingMsg).getText();
    }
    public String getCreateAccountErrorMSG() {
        return driver.findElement(create_account_error).getText();
    }
    public void selectGender(String gender){
        if (gender.equalsIgnoreCase("male"))
                driver.findElement(gender_radio_male).click();
        else if (gender.equalsIgnoreCase("female"))
                driver.findElement(gender_radio_female).click();
        else
                System.out.println("Please provide a specific gender Male/ Female");
    }
    public void setFirstName(String firstName) {
        driver.findElement(firstname).sendKeys(firstName);
    }
    public void setLastName(String lastName) {
         driver.findElement(lastname).sendKeys(lastName);
    }
    public String getEmailPlaceholder() {
        return driver.findElement(email).getAttribute("value");
    }
    public void changeDefaultEmail(String NEmail) {
      driver.findElement(email).clear();
      driver.findElement(email).sendKeys(NEmail);
    }
    public void setPassword(String password) {
        driver.findElement(psw).sendKeys(password);
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
        System.out.println("Date: "+date+ " selected");
    }
    public void selectNewsletter() {
         driver.findElement(newsletter).click();
    }
    public void selectSpecialOffers() {
        driver.findElement(specialOffers).click();
    }
    public void setAddressFirstname(String addressFirstnameV) {
         driver.findElement(addressFirstname).sendKeys(addressFirstnameV);
    }
    public String getAddressFirstnamePlaceholder() {
        return driver.findElement(addressFirstname).getAttribute("value");
    }
    public String getAddressLastnamePlaceholder() {
        return driver.findElement(addressLastname).getAttribute("value");
    }
    public void setAddressLastname(String addressLastnameV) {
         driver.findElement(addressLastname).sendKeys(addressLastnameV);
    }
    public void setAddress(String addressV) {
         driver.findElement(address).sendKeys(addressV);
    }
    public void setCity(String cityName) {
         driver.findElement(city).sendKeys(cityName);
    }
    public void setCompany(String companyName) {
         driver.findElement(company).sendKeys(companyName);
    }
    public void selectState(String stateName) {
        Select select= new Select(driver.findElement(state));
        select.selectByVisibleText(stateName);
    }
    public void selectCountry(String stateName) {
        Select select= new Select(driver.findElement(country));
        select.selectByVisibleText(stateName);
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
    }
    public void getAdditionalInfo(String info) {
         driver.findElement(additionalInfo).sendKeys(info);
    }
    public void setPhone(String phoneV) {
        driver.findElement(phone).sendKeys(phoneV);
    }
    public void setMobilePhone(String mobilePhoneV) {
        driver.findElement(mobilePhone).sendKeys(mobilePhoneV);
    }
    public void setAddressAlias(String addressAliasV) {
        driver.findElement(addressAlias).sendKeys(addressAliasV);
    }
    public void getSubmitAccount() {
       driver.findElement(submitAccount).click();
    }
    public boolean genderIsChecked(String gender){
        if (gender.equalsIgnoreCase("male"))
            return driver.findElement(gender_radio_male).getCssValue("class").contains("checked");
        else
            return driver.findElement(gender_radio_female).getCssValue("class").contains("checked");
    }

}
