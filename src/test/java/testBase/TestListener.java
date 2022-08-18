package testBase;

import base.ExtentReporterNG;
import base.ProjectActions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    ExtentReports extentReports= ExtentReporterNG.config();
    ExtentTest test;

    //to run TCs parallel
    ThreadLocal<ExtentTest>exTest=new ThreadLocal<>();
    @Override
    public void onTestStart(ITestResult result) {

        ITestListener.super.onTestStart(result);
         test =extentReports.createTest(result.getMethod().getMethodName());
         exTest.set(test);
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        exTest.get().log(Status.PASS,"Test passed");
    }
    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        WebDriver driver;
        exTest.get().fail(result.getThrowable());
        try {
            driver=(WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
            ProjectActions gm =new ProjectActions(driver);
            String scrPath= gm.getScreenshot(result.getName());
            exTest.get().addScreenCaptureFromPath(scrPath,result.getMethod().getMethodName());
            System.out.println(scrPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
        exTest.get().skip(result.getThrowable());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
    }

    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
    }

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        extentReports.flush();
    }
}
