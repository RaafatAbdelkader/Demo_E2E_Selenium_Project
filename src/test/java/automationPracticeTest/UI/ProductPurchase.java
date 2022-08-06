package automationPracticeTest.UI;


import net.minidev.json.parser.ParseException;
import org.testng.annotations.Test;
import testBase.TestBase;

import java.io.IOException;

public class ProductPurchase extends TestBase {
    private String username=oneValidTestData().get("username");
    private String password=oneValidTestData().get("password");
    public ProductPurchase() throws IOException, ParseException {
    }

    @Test
    public void productPurchase_E2E() throws InterruptedException {
        driver.get(url);
        header.navigateToLoginPage().click();
        loginPage.login(username,password);
        actions.moveToElement(header.getWomenBTN()).build().perform();
        general.waitToBeClickable(header.getSummerDresses(),5);
        header.getSummerDresses().click();

    }
}
