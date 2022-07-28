package testBase;
import automationpractice.UI.Homepage;
import base.DBReader;
import base.GenMethods;
import base.PropReader;
import base.JsonReader;
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


public class TestBase{
    public WebDriver driver ;
    public  GenMethods gm;
    public  Homepage home;
    public JsonReader jsonReader;
    public  PropReader reader = new PropReader();
    public DBReader db=new DBReader();
    public TestBase() throws IOException {

    }

    String browserName= reader.getBrowserName();
    public static Logger log = LogManager.getLogger(TestBase.class.getName());

    @BeforeClass(alwaysRun = true)
    public void before() throws IOException {
        switch (browserName) {
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions=new FirefoxOptions();
                if(reader.getHeadlessMode()){
                    firefoxOptions.addArguments("headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            }
            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions=new EdgeOptions();
                if(reader.getHeadlessMode()){
                    edgeOptions.addArguments("headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            }
            default: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions=new ChromeOptions();
                if(reader.getHeadlessMode()){
                    chromeOptions.addArguments("headless");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            }
        }
        gm = new GenMethods(driver);
        home =new Homepage(driver);
        jsonReader = new JsonReader();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
       driver.quit();
    }

    //-------------Data driven from json file------------
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

    //-------------from Database ------------
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