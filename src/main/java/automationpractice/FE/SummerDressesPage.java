package automationpractice.FE;

import basePg.MyLogger;
import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SummerDressesPage {
    private WebDriver driver;
    private Actions actions;
    private ProjectActions projectActions;
    private By categoryName= By.xpath("//span[@class='cat-name']");
    private By headingCounter= By.xpath("//span[@class='heading-counter']");
    private By productItems= By.xpath("//ul[contains(@class,'product_list')]/li");
    private By sortDDL= By.id("selectProductSort");
    private By productPrice= By.xpath("//div[contains(@class,'right-block')] //span[@itemprop='price']");
    private By loadingIcon=By.xpath("//ul[contains(@class,'product_list')]/p[contains(text(),'Loading')]");
    private By view= By.xpath("//li[contains(@class,'hovered')] //a[@title='View']");
    private By addToCart= By.xpath("//li[contains(@class,'hovered')] //a[@title='Add to cart']");


    public SummerDressesPage(WebDriver driver) {
        this.driver = driver;
        actions=new Actions(driver);
        projectActions= new ProjectActions(driver);
    }

    public String getCategoryNameMSG(){
        return driver.findElement(categoryName).getText();
    }
    public String getHeadingCounterMSG(){
        return driver.findElement(headingCounter).getText();
    }
    public List<WebElement> getProductItems(){
        return driver.findElements(productItems);
    }
    public void sortBy(String text){
        Select select=new Select(driver.findElement(sortDDL));
        select.getOptions().forEach(s->{
            if(s.getText().contains(text))
                s.click();
        });
        waitToBeSorted(10);
        projectActions.scrollDown("250");
        MyLogger.info("Sorted by: "+text);
    }
    public Boolean isOrderedDescending(){
        List<Double>priceList=getPriceList();
        List<Double>temp=new ArrayList<>(priceList);
        Collections.sort(temp,Collections.reverseOrder());
        return priceList.equals(temp);
    }
    public Boolean isOrderedAscending(){
        List<Double>priceList=getPriceList();
        List<Double>temp=new ArrayList<>(priceList);
        Collections.sort(temp);
        return priceList.equals(temp);
    }

    public List<Double> getPriceList(){
        List<Double>priceList = new ArrayList<>();
        driver.findElements(productPrice)
                .forEach(s->{
                    String[] priceValue = s.getText().split("\\$");
                    priceList.add(Double.valueOf(priceValue[priceValue.length-1].trim()));
                });

        return priceList;
    }
    public void waitToBeSorted(int timeLimitInSec){
       WebDriverWait wait =new WebDriverWait(driver, Duration.ofSeconds(timeLimitInSec));
       wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
    }
    public WebElement getProductItem(String name){
       WebElement item=null;
       List<WebElement>items=driver.findElements(productItems);
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getText().contains(name)){
               item= items.get(i);
                break;
            }
        }
        return item;
    }
    public void addProductToCart(String productName){
        WebElement product = getProductItem(productName);
        actions.moveToElement(product).build().perform();
        driver.findElement(addToCart).click();
        MyLogger.info("Product ("+productName+") added to cart");
    }
    public ProductViewPage viewProduct(String productName){
        WebElement productItem=getProductItem(productName);
        actions.moveToElement(productItem).build().perform();
        driver.findElement(view).click();
        MyLogger.info("navigated to view page of the product: "+ productName);
        return new ProductViewPage(driver);
    }

}
