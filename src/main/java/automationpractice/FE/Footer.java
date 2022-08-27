package automationpractice.FE;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class Footer {
    private WebDriver driver;
    public Footer(WebDriver driver) {
        this.driver = driver;
    }

    private By url=By.xpath("//footer[@id='footer'] //a[contains(@href,'http')]");

    public List<String>getFooterLinks(){
       List<String>links =new ArrayList<>();
       driver.findElements(url).forEach(s->links.add(s.getAttribute("href")));
       return links;
    }

}
