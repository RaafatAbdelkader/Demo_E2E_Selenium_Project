package automationPractice;

import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.TestBase;

import java.io.IOException;

public class TestWithSameDriver extends TestBase {

    public TestWithSameDriver() throws IOException {
    }
    @Test
    public void openWebsite(){
        home.shoppingCart().click();
    }
    @Test(dependsOnMethods = "openWebsite")
    public void assertionOnMsg(){
        Assert.assertEquals(home.alertMsg().getText(),"Your shopping cart is empty.");
    }

    @Test
    public void search(){
        home.searchInput().sendKeys("ich suche ein Angebot");
    }
}
