package automationpractice.FE;

import basePg.MyLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductViewPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By qty= By.id("quantity_wanted");
    private By alertMsg= By.xpath("//p[@class='fancybox-error']");
    private By addToCart= By.xpath("//p[@id='add_to_cart']/button");
    private By closeErrorMsg=By.xpath("//a[@title='Close']");
    private By layer_cart=By.id("layer_cart");
    private By addedTOCartSuccessMSG=By.xpath("//div[contains(@class,'layer_cart_product')] //h2");
    private By closeAddedToCartWindow =By.xpath(" //span[@title='Close window']");


    public ProductViewPage(WebDriver driver) {
        this.driver = driver;
       wait=new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void setProductQuantity(String quantity){
        wait.until(ExpectedConditions.visibilityOfElementLocated(qty));
        driver.findElement(qty).clear();
        driver.findElement(qty).sendKeys(quantity);
        MyLogger.info("set quantity as: "+quantity);
    }

    public String getErrorMSG(){
        if(isAddedToCart()){
            MyLogger.warn("Error message not displayed. product is added to cart");
            closeAddedToCartWindow();
            return null;
        } else{
            String errorMSG= driver.findElement(alertMsg).getText();
            driver.findElement(closeErrorMsg).click();
            return errorMSG;
        }
    }

    public boolean isAddedToCart(){
        wait.until(driver->{
            Boolean displayed;
            displayed=driver.findElement(addedTOCartSuccessMSG).isDisplayed() ||
                    driver.findElement(alertMsg).isDisplayed();
            return displayed;
        });
        boolean AddedToCart=driver.findElement(addedTOCartSuccessMSG).isDisplayed();
        if (AddedToCart)
            return true;
        else
            return false;
    }

    public void closeAddedToCartWindow(){
        if (isAddedToCart()) {
            wait.until(ExpectedConditions.elementToBeClickable(closeAddedToCartWindow));
            driver.findElement(closeAddedToCartWindow).click();
            MyLogger.info("closed addedToCart window");
        }
    }

    public void addToCart(){
        wait.until(ExpectedConditions.elementToBeClickable(addToCart));
        driver.findElement(addToCart).click();
        MyLogger.info("clicked add to cart");
    }
}