package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropReader {
    private String filePath = "src/main/resources/data.properties";
    private FileInputStream fs;
    private Properties prop = new Properties();

    public String getUrl() throws IOException {
        return getProp("URL");
    }

    public String getBrowserName() throws IOException {
        return getProp("browser");
    }
    public boolean getHeadlessMode(){
        return  Boolean.parseBoolean(getProp("headless"));
    }
    public String getServerIP() {
        return  getProp("Hub_IP");
    }

    public String getProp(String key) {
        try {
            fs = new FileInputStream(filePath);
            prop.load(fs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
