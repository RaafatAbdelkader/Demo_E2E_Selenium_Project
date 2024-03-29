package basePg;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
     static ExtentSparkReporter sparkReporter;
     static ExtentReports extentReports;
     private static  String browserName=PropReader.getBrowserName();

    //this is to get the result report at //reports//index.html
    public static ExtentReports config() {
        String path = System.getProperty("user.dir")+"//reports//index.html";
        sparkReporter = new ExtentSparkReporter(path);
        sparkReporter.config().setReportName("UI Report");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Tester", "Raafat Abdelkader");
        extentReports.setSystemInfo("Browser", browserName);
        return extentReports;

    }
}
