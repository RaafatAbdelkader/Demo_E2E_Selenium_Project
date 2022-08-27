package basePg;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverSetup {

    private WebDriver driver;
    private String browserName= PropReader.getBrowserName();
    private String downloadPath=PropReader.getDownloadPath();
    public WebDriver initializeDriver(){
        switch (browserName) {
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions=new FirefoxOptions();
                if(PropReader.getHeadlessMode()){
                    firefoxOptions.addArguments("headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            }
            case "edge": {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions=new EdgeOptions();
                edgeOptions.setExperimentalOption("prefs",getDriverPrefs());
                if(PropReader.getHeadlessMode()){
                    edgeOptions.addArguments("headless");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            }
            default: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions=new ChromeOptions();
                chromeOptions.setExperimentalOption("prefs",getDriverPrefs());
                if(PropReader.getHeadlessMode()){
                    chromeOptions.addArguments("headless");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            }
        }
        return driver;
    }

    public Map<String,Object>getDriverPrefs(){
        Map<String,Object> driverPrefs=new HashMap<>();
        driverPrefs.put("profile.default_content_settings.popups",0);
        driverPrefs.put("download.default_directory",downloadPath);
        return driverPrefs;
    }

}
