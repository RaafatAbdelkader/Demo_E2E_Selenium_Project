package base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class General {
    public WebDriver driver;
    public General(WebDriver driver) {
        this.driver = driver;
    }

    public String getScreenshot(String image_name) throws IOException {
        File screenshot = driver.findElement(By.tagName("Body")).getScreenshotAs(OutputType.FILE);
        String path= System.getProperty("user.dir")+"/scrShots/failedTCs/" + image_name + ".png";
        FileUtils.copyFile(screenshot, new File(path));
        System.out.println("Took a screenshot: "+ path);
        return path;
    }

    public void waitToBeClickable(WebElement el, int limit){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(limit));
        wait.until(ExpectedConditions.elementToBeClickable(el));
    }


}
