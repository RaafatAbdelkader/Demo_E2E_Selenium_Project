package automationpractice.FE;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductViewPage {
    WebDriver driver;
    private By qty= By.id("quantity_wanted");
    private By alertMsg= By.xpath("//p[@class='fancybox-error']");
    private By addToCart= By.xpath("//p[@id='add_to_cart']/button");
    private By closeErrorMsg=By.xpath("//a[@title='Close']");
    private By layer_cart=By.id("layer_cart");
    private By addedTOCartSuccessMSG=By.xpath("//div[contains(@class,'layer_cart_product')] //h2");
    private By closeAddedToCartWindow =By.xpath(" //span[@title='Close window']");



    public ProductViewPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getQuantity(){
            return driver.findElement(qty);
    }

    public String getErrorMSG() throws InterruptedException {
        if(!(isAddedToCart())){
            String errorMSG= driver.findElement(alertMsg).getText();
            driver.findElement(closeErrorMsg).click();
            return errorMSG;
        } else{
            System.out.println("Error message not displayed. product added to cart");
            closeAddedToCartWindow();
            return null;
        }
    }

    public boolean isAddedToCart() throws InterruptedException {
        Thread.sleep(2000);
        boolean displayed=driver.findElement(addedTOCartSuccessMSG).isDisplayed();
        if (displayed)
            return true;
        else
            return false;
    }

    public void closeAddedToCartWindow() throws InterruptedException {
        if (isAddedToCart())
           driver.findElement(closeAddedToCartWindow).click();
    }

    public void addToCart(){
        driver.findElement(addToCart).click();
    }
}