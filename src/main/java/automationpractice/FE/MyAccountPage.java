package automationpractice.FE;

import base.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyAccountPage {
    private WebDriver driver;
    private ProjectActions projectActions;

    private By page_heading_msg= By.className("info-account");

    public MyAccountPage(WebDriver driver) {
        this.driver = driver;
        projectActions=new ProjectActions(driver);
    }
    public String getPage_heading_msg() {
        projectActions.waitToBeClickable(driver.findElement(page_heading_msg),5);
         return driver.findElement(page_heading_msg).getText();
    }
}
