package automationPracticeTest.FE;
import automationpractice.FE.LoginPage;
import automationpractice.FE.MyAccountPage;
import automationpractice.FE.ProductViewPage;
import automationpractice.FE.SummerDressesPage;
import automationpractice.FE.checkoutPages.AddressPage;
import automationpractice.FE.checkoutPages.PaymentPage;
import automationpractice.FE.checkoutPages.ShippingPage;
import automationpractice.FE.checkoutPages.SummeryPage;
import automationpractice.FE.myAccountPages.OrderHistoryPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import testBase.TestBase;

import java.io.IOException;

public class ProductPurchase extends TestBase {
    private String username=oneValidTestData().get("username");
    private String password=oneValidTestData().get("password");
    private String productName="Printed Chiffon Dress";
    private LoginPage loginPage;
    private SummerDressesPage summerDressesPage;
    private ProductViewPage productViewPage;
    private SummeryPage summeryPage;
    private AddressPage addressPage;
    private ShippingPage shippingPage;
    private PaymentPage paymentPage;
    private MyAccountPage myAccountPage;
    private OrderHistoryPage orderHistoryPage;
    private String orderReference;

    @Test(priority = 1,groups = "Smoke",description = "productPurchase_E2E")
    public  void productPurchase_E2E(){
        loginPage = header.navigateToLoginPage();
        loginPage.login(username, password);
        summerDressesPage = header.navigateToSummerDressesPage();
        summerDressesPage.addProductToCart(productName);
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
        Assert.assertEquals(paymentPage.getOrderConfirmationMsg(),"Your order on My Store is complete.","order is not completed successfully");
        orderReference=paymentPage.getOrderReferenceNum();
    }
    @Test(priority = 1,dependsOnMethods="productPurchase_E2E",groups = "Smoke",description = "verify invoice pdf can be downloaded successfully and it has the right product and reference")
    public  void validateDownloadingInvoicePDF() throws IOException {
        //projectActions.cleanupProjectDownLoadDir();
        myAccountPage=header.navigateToMyAccount();
        orderHistoryPage=myAccountPage.navigateToOrderHistory();
        int lastDownloadedFilesNum = projectActions.getNumOfFilesExist();
        orderHistoryPage.downloadInvoice(orderReference);
        int currentDownloadedFilesNum=projectActions.getNumOfFilesExist();
        Assert.assertTrue(currentDownloadedFilesNum==lastDownloadedFilesNum+1,"File was not downloaded");
        projectActions.openNewTab();
        projectActions.openLastModifiedFile();
        String filePath=projectActions.getLastModifiedFile().getPath();
        String pdfContent=projectActions.getPDFContent(filePath);
        Assert.assertTrue(pdfContent.contains(productName));
        Assert.assertTrue(pdfContent.contains(orderReference));
        driver.close();
        projectActions.returnToLastTab();
        projectActions.deleteLastModifiedFile();
    }
    @Test(description = "verify an user can sort by price")
    public void validationOfProductSorting(){
        loginPage=header.navigateToLoginPage();
        loginPage.login(username,password);
        summerDressesPage=header.navigateToSummerDressesPage();
        Assert.assertEquals(summerDressesPage.getCategoryNameMSG().trim(),"SUMMER DRESSES");
        int productsCount= summerDressesPage.getProductItems().size();
        Assert.assertEquals(summerDressesPage.getHeadingCounterMSG(),
                "There are "+productsCount+" products.","Items count does not match");
        summerDressesPage.sortBy("Price: Lowest first");
        Assert.assertTrue(summerDressesPage.isOrderedAscending(),
                "Products should be in an ascending order");
        summerDressesPage.sortBy("Price: Highest first");
        Assert.assertTrue(summerDressesPage.isOrderedDescending(),
                "Products should be in a descending order");  //bug
    }
    @Test(description = "validation of quantity with invalid entries")
    public void productQuantityValidation() throws InterruptedException {
        loginPage=header.navigateToLoginPage();
        loginPage.login(username,password);
        summerDressesPage=header.navigateToSummerDressesPage();
        productViewPage= summerDressesPage.viewProduct(productName);
        productViewPage.setProductQuantity("0");
        productViewPage.addToCart();
        Assert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");
        productViewPage.setProductQuantity("test");
        productViewPage.addToCart();
        Assert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");
        productViewPage.setProductQuantity("-20");
        productViewPage.addToCart();
        Assert.assertEquals(productViewPage.getErrorMSG(),"Negative quantity.");
        productViewPage.setProductQuantity("9999999999999999999999999999999999");
        productViewPage.addToCart();
        Assert.assertEquals(productViewPage.getErrorMSG(),"Quantity should be between 1 and 1000");
        productViewPage.setProductQuantity("h@#??//'");
        productViewPage.addToCart();
        Assert.assertEquals(productViewPage.getErrorMSG(),"Null quantity.");

    }
    @Test(description = "verify product is still added to cart after user is logged out")
    public void AddToCartFunctionality(){
        int numOfProductItemsAddedToCart;
        loginPage= header.navigateToLoginPage();
        loginPage.login(username,password);
        numOfProductItemsAddedToCart=header.getNumOfProductItemsAddedToCart();
        summerDressesPage= header.navigateToSummerDressesPage();
        productViewPage=summerDressesPage.viewProduct(productName);
        productViewPage.addToCart();
        header.logout();
        header.navigateToLoginPage();
        loginPage.login(username,password);
        Assert.assertEquals(header.getNumOfProductItemsAddedToCart(),
                header.getNumOfProductItemsAddedToCart()+1,"product added to the cart can not be found");
    }

}
