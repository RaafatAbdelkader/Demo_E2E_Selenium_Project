package automationPracticeTest.FE;
import automationpractice.FE.LoginPage;
import automationpractice.FE.ProductViewPage;
import automationpractice.FE.SummerDressesPage;
import net.minidev.json.parser.ParseException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testBase.TestBase;
import java.io.IOException;
import java.util.List;

public class ProductPurchase extends TestBase {
    private String username=oneValidTestData().get("username");
    private String password=oneValidTestData().get("password");
    private LoginPage loginPage;
    private SoftAssert softAssert=new SoftAssert();
    private SummerDressesPage summerDressesPage;
    private ProductViewPage productViewPage;
    public ProductPurchase() throws IOException, ParseException {
    }

    @Test(description = "verify an user can sort by price", groups = "Smoke")
    public void validationOfProductSorting(){
        loginPage=header.navigateToLoginPage();
        loginPage.login(username,password);
        summerDressesPage=header.navigateToSummerDressesPage();
        softAssert.assertEquals(summerDressesPage.getCategoryName().getText().trim(),"SUMMER DRESSES");
        int productsCount= summerDressesPage.getProductItems().size();
        softAssert.assertEquals(summerDressesPage.getHeadingCounter().getText(),
                "There are "+productsCount+" products.","Items count does not match");
        summerDressesPage.sortBy("Price: Lowest first");
        summerDressesPage.waitToBeSorted(5);
        List<Double>priceList=summerDressesPage.getPriceList();
        softAssert.assertTrue(summerDressesPage.isOrderedAscending(priceList),
                "Products should be in an ascending order");
        summerDressesPage.sortBy("Price: Highest first");
        summerDressesPage.waitToBeSorted(5);
        priceList=summerDressesPage.getPriceList();
        softAssert.assertTrue(summerDressesPage.isOrderedDescending(priceList),
                "Products should be in a descending order");
        softAssert.assertAll();
    }

    @Test(description = "validation of quantity with invalid entries")
    public void productQuantityValidation() throws InterruptedException {
        loginPage=header.navigateToLoginPage();
        loginPage.login(username,password);
        summerDressesPage=header.navigateToSummerDressesPage();
        WebElement productItem=summerDressesPage.getProductItem("Printed Chiffon Dress");
        actions.moveToElement(productItem).build().perform();
        productViewPage= summerDressesPage.viewProduct();
        projectActions.waitToBeClickable(productViewPage.getQuantity(),5);
        WebElement qty=productViewPage.getQuantity();
        qty.clear();
        qty.sendKeys("0");
        productViewPage.addToCart();
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");
        qty.clear();
        qty.sendKeys("test");
        productViewPage.addToCart();
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");
        qty.clear();
        qty.sendKeys("-20");
        productViewPage.addToCart();
        softAssert.assertFalse(productViewPage.isAddedToCart(),"product added to cart with invalid quantity");
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Negative quantity.");
        qty.clear();
        qty.sendKeys("9999999999999999999999999999999999");
        productViewPage.addToCart();
        softAssert.assertFalse(productViewPage.isAddedToCart(),"product added to cart with invalid quantity");
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Quantity should be between 1 and 1000");
        qty.clear();
        qty.sendKeys("h@#??//'");
        productViewPage.addToCart();
        softAssert.assertFalse(productViewPage.isAddedToCart(),"product added to cart with invalid quantity");
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");
        softAssert.assertAll();
    }

}
