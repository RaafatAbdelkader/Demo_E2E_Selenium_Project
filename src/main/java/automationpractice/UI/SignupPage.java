package automationpractice.UI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class SignupPage {
    WebDriver driver;
    private By subHeadingMsg =By.xpath("//label[contains(text(),'Title')]/parent:: div/preceding-sibling:: h3[@class='page-subheading']");
    private By gender_radio_male= By.xpath("//input[@id='id_gender1']");
    private By gender_radio_female=  By.xpath("//input[@id='id_gender2']");
    private By firstname=  By.id("customer_firstname");
    private By lastname=  By.id("customer_lastname");
    private By email=  By.id("email");
    private By psw=  By.id("passwd");
    private By days= By.id("days");
    private By months= By.id("months");
    private By years= By.id("years");

    public SignupPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getSubHeadingMsg() {
        return driver.findElement(subHeadingMsg);
    }

    public WebElement getGenderRadio(String gender){
        if (gender.equalsIgnoreCase("male"))
            return driver.findElement(gender_radio_male);
        else if (gender.equalsIgnoreCase("female"))
            return driver.findElement(gender_radio_female);
        else {
            System.out.println("Please provide a specific gender Male/ Female");
            return null;
        }
    }

    public WebElement getFirstName() {
        return driver.findElement(firstname);
    }
    public WebElement getLastName() {
        return driver.findElement(lastname);
    }
    public WebElement getEmail() {
        return driver.findElement(email);
    }
    public WebElement getPassword() {
        return driver.findElement(psw);
    }

    //the date should be provided in this Format:"dd-mm-yyyy"
    public void selectDate(String date) {
        String[]dateValues=date.split("-");
        for (int i = 0; i < 3; i++) {
            WebElement  element = null;
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


}
