package testBase;
import automationpractice.FE.*;
import basePg.*;
import net.minidev.json.parser.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestBase {
    public WebDriver driver ;
    public ExcelReader excelReader= new ExcelReader();
    public DBReader db=new DBReader();
    public ProjectActions projectActions;
    public Homepage homepage;
    public Header header;
    public Actions actions;
    public String url= PropReader.getUrl();
    private DriverSetup setup=new DriverSetup();
    public static Logger log = LogManager.getLogger(TestBase.class.getName());


    @BeforeClass(alwaysRun = true)
    public void before()  {
        driver= setup.initializeDriver();
        projectActions = new ProjectActions(driver);
        homepage=new Homepage(driver);
        header=new Header(driver);
        actions=new Actions(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(url);
    }
   @AfterClass(alwaysRun = true)
    public void postConditions() {
        //driver.quit();
    }

    //---Data driven  from json file--
    private static List<String> valuesToReturn= List.of("username","password","status","expectedMSG");
    @DataProvider
    protected Object[][] getAllTestUsers(){
        return JsonReader.getAllUsersData(valuesToReturn);
    }
    @DataProvider
    public Object[][] getValidLoginTestData() {
        return  JsonReader.getDataUsingStatus(valuesToReturn,"valid");
    }
    public static Map<String,String> oneValidTestData() {
        Map<String,String>data=new HashMap<>();
        Object[][] values=JsonReader.getDataUsingStatus(valuesToReturn,"valid");
        data.put("username",values[0][0].toString());
        data.put("password",values[0][1].toString());
        return data;
    }

    @DataProvider
    public Object[][] getInvalidLoginTestData() {
        return  JsonReader.getDataUsingStatus(valuesToReturn,"invalid");
    }


    //---Data driven dynamically from Excel file---
    public Map<String,String> registerTData =excelReader.getRegisterTestData("UserX");

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