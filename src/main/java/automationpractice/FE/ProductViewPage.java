package automationpractice.FE;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductViewPage {
    private WebDriver driver;
    WebDriverWait wait;
    private By qty= By.id("quantity_wanted");
    private By alertMsg= By.xpath("//p[@class='fancybox-error']");
    private By addToCart= By.xpath("//p[@id='add_to_cart']/button");
    private By closeErrorMsg=By.xpath("//a[@title='Close']");
    private By layer_cart=By.id("layer_cart");
    private By addedTOCartSuccessMSG=By.xpath("//div[contains(@class,'layer_cart_product')] //h2");
    private By closeAddedToCartWindow =By.xpath(" //span[@title='Close window']");


    public ProductViewPage(WebDriver driver) {
        this.driver = driver;
       wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void setProductQuantity(String quantity){
        wait.until(ExpectedConditions.visibilityOfElementLocated(qty));
        driver.findElement(qty).clear();
        driver.findElement(qty).sendKeys(quantity);
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
        Thread.sleep(3000);
        boolean displayed=driver.findElement(addedTOCartSuccessMSG).isDisplayed();
        if (displayed)
            return true;
        else
            return false;
    }

    public void closeAddedToCartWindow() throws InterruptedException {
        if (isAddedToCart()) {
            wait.until(ExpectedConditions.elementToBeClickable(closeAddedToCartWindow));
            driver.findElement(closeAddedToCartWindow).click();
        }
    }

    public void addToCart(){
        wait.until(ExpectedConditions.elementToBeClickable(addToCart));
        driver.findElement(addToCart).click();
    }
}
