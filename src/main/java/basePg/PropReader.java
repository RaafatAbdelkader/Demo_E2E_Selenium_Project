package basePg;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropReader {
    private static String propFilePath = "src/main/resources/environment.properties";
    private static String downloadPath=System.getProperty("user.dir")+"\\download";
    private static FileInputStream fs;
    private static Properties prop = new Properties();

    public static String getUrl(){
        return getProp("URL");
    }

    public static String getBrowserName(){
        return getProp("browser");
    }
    public static boolean getHeadlessMode(){
        return  Boolean.parseBoolean(getProp("headless"));
    }
    public String getServerIP() {
        return  getProp("Grid_Hub_IP");
    }

    public static String getProp(String key) {
        try {
            fs = new FileInputStream(propFilePath);
            prop.load(fs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
    public static String getDownloadPath(){
        return downloadPath;
    }
}
