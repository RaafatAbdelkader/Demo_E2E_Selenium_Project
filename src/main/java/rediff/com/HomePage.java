package rediff.com;

import base.GenMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {
    WebDriver driver;

    private By add= By.xpath("//button[contains(text(),'NO THANKS')]");
    private By loginBtn= By.xpath("//span[contains(text(),'Login')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    public WebElement navigateToLogin(){
        return driver.findElement(loginBtn);
    }
    public void closeAdd(){
        GenMethods genMethods=new GenMethods(driver);
        genMethods.waitToBeClickable(driver.findElement(add),10);
        WebElement element=driver.findElement(add);
        if (element.isDisplayed()){
            element.click();
        }else {
            System.out.println("Add not found");
        }

    }
}
