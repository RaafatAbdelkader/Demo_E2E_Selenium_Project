package basePg;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

public class ProjectActions {
    private WebDriver driver;
    private String downloadPath= PropReader.getDownloadPath();
    public ProjectActions(WebDriver driver) {
        this.driver = driver;
    }
    public String getScreenshot(String image_name) throws IOException {
        File screenshot = driver.findElement(By.tagName("Body")).getScreenshotAs(OutputType.FILE);
        String path= System.getProperty("user.dir")+"/scrShots/failedTCs/" + image_name+".png";
        FileUtils.copyFile(screenshot, new File(path));
        System.out.println("Took a screenshot: "+ path);
        return path;
    }
    public String getPDFContent(String filePath) throws IOException {
        File file=new File(filePath);
        PDDocument pdDoc = Loader.loadPDF(file);
        String content=new  PDFTextStripper().getText(pdDoc);
        pdDoc.close();
        return content;
    }
    private String lastTabId=null;
    public String openNewTab(){
        lastTabId=driver.getWindowHandle();
        return  driver.switchTo().newWindow(WindowType.TAB).getWindowHandle();
    }
    public void returnToLastTab(){
        if (lastTabId!=null)
            driver.switchTo().window(lastTabId);
        else
            System.out.println("No last tab found");
    }
    public String getIdOfCurrentTab(){
        return  driver.getWindowHandle();
    }
    public String[] getFileNames() {
        File dir = new File(downloadPath);
        Collection<String> files =new ArrayList<>();
        if(dir.isDirectory()){
            File[] listFiles = dir.listFiles();
            for(File file : listFiles){
                if(file.isFile()) {
                    files.add(file.getName());
                }
            }
        }
        return files.toArray(new String[]{});
    }
    public File  getLastModifiedFile() {
        File dir = new File(downloadPath);
        File lastModifiedFile = null;
        if(dir.isDirectory()&&dir.listFiles().length>0){
            File[] listFiles = dir.listFiles();
            lastModifiedFile=listFiles[0];
            for (int i = 1; i < listFiles.length; i++) {
                if (lastModifiedFile.lastModified()<listFiles[i].lastModified()) {
                    lastModifiedFile=listFiles[i];
                }
            }
        }else
            System.out.println("No file was found");

        return lastModifiedFile;
    }
    public void deleteLastModifiedFile(){
       File lastModifiedFile=getLastModifiedFile();
       String path=lastModifiedFile.getPath();
        if (lastModifiedFile.exists()) {
            if (lastModifiedFile.delete())
                System.out.println("file deleted: "+path);
            else
                System.out.println("File cant be deleted"+path);
        }
    }
    public void openLastModifiedFile(){
        File fl=getLastModifiedFile();
        if (fl!=null&& fl.isFile())
           driver.navigate().to(fl.getPath());
    }
    public void cleanupProjectDownLoadDir(){
        File dir = new File(downloadPath);
        if(dir.isDirectory()&&dir.listFiles().length>0){
          File[]files=  dir.listFiles();
            for (File file:files) {
                file.delete();
            }
        }

    }
    public Integer getNumOfFilesExist(){
        File dir = new File(downloadPath);
        return dir.listFiles().length;
    }
    public void waitToBeDownloaded(){
       int tabs= driver.getWindowHandles().size();
       WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(30));
       wait.until(ExpectedConditions.numberOfWindowsToBe(tabs-1));
    }

}
