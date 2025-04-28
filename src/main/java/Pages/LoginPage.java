package Pages;

import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;
    Properties loc;
    
    public LoginPage(WebDriver driver, Properties loc) {
        this.driver = driver;
        this.loc = loc;
  
    }


	public void enterUsername(String username) {
        driver.findElement(By.xpath(loc.getProperty("Username"))).clear();
        driver.findElement(By.xpath(loc.getProperty("Username"))).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(By.xpath(loc.getProperty("Password"))).clear();
        driver.findElement(By.xpath(loc.getProperty("Password"))).sendKeys(password);
    }

    public void clickRememberMe() {
        driver.findElement(By.xpath(loc.getProperty("Checkbox"))).click();
    }

    public void clickLoginButton() {
        driver.findElement(By.xpath(loc.getProperty("Loginbtn"))).click();
    }
  
    
     public void logout() {
    	 
    	 driver.findElement(By.xpath("//a[@role='button']")).click();
    	 driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
     }
    
    
}
