package testBase;
import automationpractice.FE.*;
import base.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.minidev.json.parser.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestBase{
    public WebDriver driver ;
    public PropReader propReader = new PropReader();
    public ExcelReader excelReader= new ExcelReader();
    public DBReader db=new DBReader();
    public ProjectActions projectActions;
    public Homepage homepage;
    public Header header;
    public Actions actions;
    public String url=propReader.getUrl();
    public TestBase() throws IOException {
    }
    String browserName= propReader.getBrowserName();
    public static Logger log = LogManager.getLogger(TestBase.class.getName());

    @BeforeMethod(alwaysRun = true)
    public void before()  {
        switch (browserName) {
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions=new FirefoxOptions();
                if(propReader.getHeadlessMode()){
                    firefoxOptions.addArguments("headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            }
            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions=new EdgeOptions();
                if(propReader.getHeadlessMode()){
                    edgeOptions.addArguments("headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            }
            default: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions=new ChromeOptions();
                if(propReader.getHeadlessMode()){
                    chromeOptions.addArguments("headless");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            }
        }
        projectActions = new ProjectActions(driver);
        homepage=new Homepage(driver);
        header=new Header(driver);
        actions=new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(url);
    }

    @AfterMethod(alwaysRun = true)
    public void postConditions() {
        header.logout();
        driver.quit();
    }

    //---Data driven  from json file--
    private static List<String> valuesToReturn= List.of("username","password","status","expectedMSG");
    @DataProvider
    protected Object[][] getAllTestUsers() throws FileNotFoundException, ParseException {
        return JsonReader.getAllUsersData(valuesToReturn);
    }
    @DataProvider
    public Object[][] getValidLoginTestData() throws FileNotFoundException, ParseException {
        return  JsonReader.getDataUsingStatus(valuesToReturn,"valid");
    }
    public static Map<String,String> oneValidTestData() throws FileNotFoundException, ParseException {
        Map<String,String>data=new HashMap<>();
        Object[][] values=JsonReader.getDataUsingStatus(valuesToReturn,"valid");
        data.put("username",values[0][0].toString());
        data.put("password",values[0][1].toString());
        return data;
    }

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        System.out.println(oneValidTestData().get("password"));
    }

    @DataProvider
    public Object[][] getInvalidLoginTestData() throws FileNotFoundException, ParseException {
        return  JsonReader.getDataUsingStatus(valuesToReturn,"invalid");
    }


    //---Data driven dynamically from Excel file---
    public Map<String,String> getRegisterTestData =excelReader.getRegisterTestData("UserX");

    @DataProvider
    public Object[][] getInvalidPersonalData() throws IOException {
        return excelReader.getInvalidPersonalData();
    }




    //---Data driven from Database ---
//    @DataProvider
//    public Object[][] validDataFromDB() throws SQLException {
//            return  db.getLoginData(true,1);
//    }
//    @DataProvider
//    public Object[][] invalidDataFromDB() throws SQLException {
//        return  db.getLoginData(false,4);
//    }

}





















//336 done

//29 devtools still not seen
//35 git seen