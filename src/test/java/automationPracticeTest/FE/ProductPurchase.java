package automationPracticeTest.FE;
import automationpractice.FE.LoginPage;
import automationpractice.FE.ProductViewPage;
import automationpractice.FE.SummerDressesPage;
import automationpractice.FE.checkoutPages.AddressPage;
import automationpractice.FE.checkoutPages.PaymentPage;
import automationpractice.FE.checkoutPages.ShippingPage;
import automationpractice.FE.checkoutPages.SummeryPage;
import basePg.PropReader;
import net.minidev.json.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testBase.TestBase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductPurchase extends TestBase {
    private String username=oneValidTestData().get("username");
    private String password=oneValidTestData().get("password");
    private LoginPage loginPage;
    private SoftAssert softAssert=new SoftAssert();
    private SummerDressesPage summerDressesPage;
    private ProductViewPage productViewPage;
    private SummeryPage summeryPage;
    private AddressPage addressPage;
    private ShippingPage shippingPage;
    private PaymentPage paymentPage;
    private String productName="Printed Chiffon Dress";

    @Test(priority = 1,groups = "Smoke",description = "productPurchase_E2E")
    public  void productPurchase_E2E(){
        loginPage = header.navigateToLoginPage();
        loginPage.login(username, password);
        summerDressesPage = header.navigateToSummerDressesPage();
        WebElement product = summerDressesPage.getProductItem(productName);
        actions.moveToElement(product).build().perform();
        summerDressesPage.addProductToCart();
        String acMsg=header.getAddedTOCartSuccessMSG();
        Assert.assertEquals(acMsg,"Product successfully added to your shopping cart");
        summeryPage= header.proceedToCheckout();
        Assert.assertTrue(summeryPage.getCartTitle()
                .contains("SHOPPING-CART SUMMARY"),"Title does not match");
        int numOfCartItems=summeryPage.getNumOfCartItems();
        Assert.assertTrue(summeryPage.getHeadingCounterMsg()
                .contains("Your shopping cart contains: "+numOfCartItems+" Product"),"counter message does not match");
        addressPage=summeryPage.proceedToAddressPage();
        Assert.assertEquals(addressPage.getHeadingMsg(),"ADDRESSES","heading message does not match");
        shippingPage=addressPage.proceedToShipping();
        Assert.assertEquals(shippingPage.getHeadingMsg(),"SHIPPING");
        shippingPage.acceptTermsOfService();
        paymentPage=shippingPage.proceedToPaymentPage();
        Assert.assertEquals(paymentPage.getHeadingMsg(),"PLEASE CHOOSE YOUR PAYMENT METHOD");
        paymentPage.selectPayByCheck();
        Assert.assertEquals(paymentPage.getSubHeadingMsg(),"CHECK PAYMENT");
        paymentPage.confirmOrder();
        Assert.assertEquals(paymentPage.getConfirmationMsg(),"Your order on My Store is complete.","order is not completed successfully");
        //get order ref and save it
        header.returnToHomePage();
    }
    @Test(priority = 1,groups = "Smoke",description = "verify invoice pdf can be downloaded and it has the right product")
    public  void validateDownloadingInvoicePDF(){
        //projectActions.cleanupDownLoadDirectory();
        int lastDownloadedFilesNum = projectActions.getNumOfFilesExist();
        driver.navigate().to("http://automationpractice.com/index.php?controller=history");
        WebElement pdf= driver.findElement(By.xpath("//a[@title='Invoice']"));
        projectActions.waitToBeClickable(pdf,10);
        pdf.click();
        projectActions.waitToBeDownloaded();
        int currentDownloadedFilesNum=projectActions.getNumOfFilesExist();
        Assert.assertTrue(currentDownloadedFilesNum>lastDownloadedFilesNum,"File was not downloaded");
        projectActions.openNewTab();
        projectActions.openLastModifiedFile();
        String filePath=projectActions.getLastModifiedFile().getPath();
        String pdfContent=projectActions.getPDFContent(filePath);
        Assert.assertTrue(pdfContent.contains(productName));
        driver.close();
        projectActions.returnToLastTab();
        projectActions.deleteLastModifiedFile();

    }
    @Test(description = "verify an user can sort by price")
    public void validationOfProductSorting(){
        loginPage=header.navigateToLoginPage();
        loginPage.login(username,password);
        summerDressesPage=header.navigateToSummerDressesPage();
        softAssert.assertEquals(summerDressesPage.getCategoryNameMSG().trim(),"SUMMER DRESSES");
        int productsCount= summerDressesPage.getProductItems().size();
        softAssert.assertEquals(summerDressesPage.getHeadingCounterMSG(),
                "There are "+productsCount+" products.","Items count does not match");
        summerDressesPage.sortBy("Price: Lowest first");
        List<Double>priceList=summerDressesPage.getPriceList();
        softAssert.assertTrue(summerDressesPage.isOrderedAscending(priceList),
                "Products should be in an ascending order");
        summerDressesPage.sortBy("Price: Highest first");
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
        WebElement productItem=summerDressesPage.getProductItem(productName);
        actions.moveToElement(productItem).build().perform();
        productViewPage= summerDressesPage.viewProduct();
        productViewPage.setProductQuantity("0");
        productViewPage.addToCart();
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");
        productViewPage.setProductQuantity("test");
        productViewPage.addToCart();
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");
        productViewPage.setProductQuantity("-20");
        productViewPage.addToCart();
        softAssert.assertFalse(productViewPage.isAddedToCart(),"product added to cart with invalid quantity");
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Negative quantity.");
        productViewPage.setProductQuantity("9999999999999999999999999999999999");
        productViewPage.addToCart();
        softAssert.assertFalse(productViewPage.isAddedToCart(),"product added to cart with invalid quantity");
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Quantity should be between 1 and 1000");
        productViewPage.setProductQuantity("h@#??//'");
        productViewPage.addToCart();
        softAssert.assertFalse(productViewPage.isAddedToCart(),"product added to cart with invalid quantity");
        softAssert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");
        softAssert.assertAll();
    }
    @Test(description = "verify product is still added to cart after user is logged out")
    public void AddToCartFunctionality(){
        int productQtyAddedToCart;
        loginPage= header.navigateToLoginPage();
        loginPage.login(username,password);
        productQtyAddedToCart=header.getProductQtyAddedToCart();
        summerDressesPage= header.navigateToSummerDressesPage();
        actions.moveToElement(summerDressesPage.getProductItem(productName)).build().perform();
        productViewPage=summerDressesPage.viewProduct();
        productViewPage.addToCart();
        header.logout();
        header.navigateToLoginPage();
        loginPage.login(username,password);
        Assert.assertEquals(header.getProductQtyAddedToCart(),
                header.getProductQtyAddedToCart()+1,"product added to the cart can not be found");
    }

}
