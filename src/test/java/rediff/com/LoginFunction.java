package rediff.com;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.TestBase;

import java.io.IOException;

public class LoginFunction extends TestBase {
    public LoginFunction() throws IOException {

    }

    @Test(groups = "Smoke")
    public void test88() {
        driver.navigate().to("https://facebook.com");
    }
    @Test(groups = "Smoke")
    public void test7() {
        driver.navigate().to("https://facebook.com");
    }
    @Test(groups = "Smoke")
    public void test8() {
        driver.navigate().to("https://facebook.com");
    }
    @Test(groups = "Smoke")
    public void test9() {
        driver.navigate().to("https://facebook.com");
    }
    @Test(groups = "Smoke")
    public void test10() {
        driver.navigate().to("https://facebook.com");
    }
    @Test(groups = "Smoke")
    public void test89() {
        driver.navigate().to("https://facebook.com");
    }



    @Test()
    public void test99() {
        driver.get(url);
        System.out.println(driver.getTitle());
    }
    @Test()
    public void test49() {
        driver.get(url);
        System.out.println(driver.getTitle());
    }

    @Test(groups = "absolete")
    public void test66() {
        driver.get("https://facebook.com");
        Assert.assertTrue(false,"absolete tc run");
    }


    @Test(dataProvider="invalidDataFromDB")
    public void test1(String username, String psw,String status) {
         driver.get(url);
         loginPage.username().sendKeys(username);
         loginPage.password().sendKeys(psw);
         loginPage.submit().click();
         if (status.equalsIgnoreCase("valid")){
             //assertion of the following page
         }else {
             Assert.assertEquals(loginPage.getAlertMsg(),"Invalid email or password.","alert message not found");
         }
    }


}
