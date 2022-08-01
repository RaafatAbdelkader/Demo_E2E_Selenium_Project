package testBase;
import automationpractice.UI.*;
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
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class TestBase{
    public WebDriver driver ;
    public PropReader propReader = new PropReader();
    public ExcelReader excelReader= new ExcelReader();
    public DBReader db=new DBReader();
    public General general;
    public Homepage home;
    public LoginPage loginPage;
    public Homepage homepage;
    public SignupPage signupPage;
    public MyAccountPage myAccountPage;
    public Footer footer;
    public Header header;
    public TestBase() throws IOException {
    }

    String browserName= propReader.getBrowserName();
    public static Logger log = LogManager.getLogger(TestBase.class.getName());

    @BeforeClass(alwaysRun = true)
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
        general = new General(driver);
        home =new Homepage(driver);
        loginPage=new LoginPage(driver);
        homepage=new Homepage(driver);
        myAccountPage=new MyAccountPage(driver);
        header=new Header(driver);
        footer=new Footer(driver);
        signupPage=new SignupPage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
     driver.quit();
    }

    //---Data driven  from json file--
    private List<String> valuesToReturn= Arrays.stream(new String[]{"username","password","status"}).toList();
    @DataProvider
    protected Object[][] allUsers() throws FileNotFoundException, ParseException {
        return JsonReader.getAllUsersData(valuesToReturn);
    }
    @DataProvider
    public Object[][] validLoginData() throws FileNotFoundException, ParseException {
        return  JsonReader.getDataUsingStatus(valuesToReturn,"valid");
    }
    @DataProvider
    public Object[][] invalidLoginData() throws FileNotFoundException, ParseException {
        return  JsonReader.getDataUsingStatus(valuesToReturn,"invalid");
    }

    //---Data driven dynamically from Excel file---
    public Map<String,String> data=excelReader.getData("UserX");
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