package testBase;
import automationpractice.Homepage;
import base.DBConnector;
import base.GenMethods;
import base.PropReader;
import base.TDataReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.minidev.json.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.*;
import rediff.com.HomePage;
import rediff.com.LoginPage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;


public class TestBase{
    public WebDriver driver ;
    public  GenMethods gm;
    public  HomePage homePage;
    public  Homepage home;
    public  LoginPage loginPage;
    public  TDataReader tDataReader;
    public  PropReader reader = new PropReader();
    public DBConnector db=new DBConnector();
    public TestBase() throws IOException {

    }
    public String url=reader.getUrl();

    @BeforeClass(alwaysRun = true)
    public void before() throws IOException {
        switch (reader.getBrowserName()) {
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
        homePage =new HomePage(driver);
        loginPage =new LoginPage(driver);
        gm = new GenMethods(driver);
        home =new Homepage(driver);
        tDataReader= new TDataReader();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
       driver.quit();
    }

    //-------------from json file------------
    private List<String> valuesToReturn= Arrays.stream(new String[]{"username","password","status"}).toList();
    @DataProvider
    protected Object[][] allUsers() throws FileNotFoundException, ParseException {
        return TDataReader.getAllUsersData(valuesToReturn);
    }
    @DataProvider
    public Object[][] validLoginData() throws FileNotFoundException, ParseException {
        return  TDataReader.getDataUsingStatus(valuesToReturn,"valid");
    }
    @DataProvider
    public Object[][] invalidLoginData() throws FileNotFoundException, ParseException {
        return  TDataReader.getDataUsingStatus(valuesToReturn,"invalid");
    }

    //-------------from Database ------------
    @DataProvider
    public Object[][] validDataFromDB() throws SQLException {
            return  db.getLoginData(true,1);
    }
    @DataProvider
    public Object[][] invalidDataFromDB() throws SQLException {
        return  db.getLoginData(false,4);
    }






}



//334done

//29 devtools still not seen
//35 git seen