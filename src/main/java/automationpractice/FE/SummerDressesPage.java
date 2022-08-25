package automationpractice.FE;

import basePg.ProjectActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SummerDressesPage {
    private WebDriver driver;
    private ProjectActions projectActions;
    public SummerDressesPage(WebDriver driver) {
        this.driver = driver;
        projectActions=new ProjectActions(driver);
    }
    private By categoryName= By.xpath("//span[@class='cat-name']");
    private By headingCounter= By.xpath("//span[@class='heading-counter']");
    private By productItems= By.xpath("//ul[contains(@class,'product_list')]/li");
    private By sortDDL= By.id("selectProductSort");
    private By productPrice= By.xpath("//div[contains(@class,'right-block')] //span[@itemprop='price']");
    private By loadingIcon=By.xpath("//ul[contains(@class,'product_list')]/p[contains(text(),'Loading')]");
    private By view= By.xpath("//li[contains(@class,'hovered')] //a[@title='View']");
    private By addToCart= By.xpath("//li[contains(@class,'hovered')] //a[@title='Add to cart']");


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
       waitToBeSorted(5);
    }

    public Boolean isOrderedDescending(List<Double>numList){
        Boolean isOrderedDescending =null;
        List<Double>sortedList= numList.stream().sorted().toList();
        int lastIndex=sortedList.size()-1;
        for (int i =lastIndex ; i >=0; i--) {
            if (!(numList.get(lastIndex-i).equals(sortedList.get(i)))) {
                System.out.println("List is not in a descending order");
                isOrderedDescending=false;
                break;
            }else
                isOrderedDescending=true;
        }
        return isOrderedDescending;
    }
    public Boolean isOrderedAscending(List<Double>numList){
        Boolean isOrderedAscending =null;
        List<Double>sortedList= numList.stream().sorted().toList();
        for (int i = 0; i < sortedList.size(); i++) {
            if (!(numList.get(i).equals(sortedList.get(i)))){
                System.out.println("List is not in a Ascending order");
                isOrderedAscending= false;
                break;
            }else
                isOrderedAscending=true;
        }
        return isOrderedAscending;
    }

    public List<Double> getPriceList(){
        List<Double>priceList = new ArrayList<>();
        driver.findElements(productPrice)
                .forEach(s->{
                    String[] priceValue = s.getText().trim().split("\\$");
                    priceList.add(Double.valueOf(priceValue[1]));
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
    public void addProductToCart(){
        driver.findElement(addToCart).click();
    }

    public ProductViewPage viewProduct(){
        driver.findElement(view).click();
        return new ProductViewPage(driver);
    }


}
