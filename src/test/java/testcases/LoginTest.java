package testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.*;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import Pages.LoginPage;
import base.BaseTest;
import utilities.ReadXls;

public class LoginTest extends BaseTest {

    LoginPage loginPage;
    WebDriverWait wait;
    
    @BeforeMethod
    public void initPage() throws InterruptedException {
    	Thread.sleep(3000);  
        driver.get(prop.getProperty("adminurl")); 
        loginPage = new LoginPage(driver,loc);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 

    }
    public void loginFromExcelRow(int rowNum)  {
        Map<String, String> rowData = null;
		try {
			rowData = ReadXls.getRowData(filepath, "login", rowNum);
		} catch (EncryptedDocumentException | IOException e) {
			
			e.printStackTrace();
		}
        
        String username = rowData.get("Username");
        String password = rowData.get("Password");

        
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }


    @Test(priority = 1)
    public void TC01_VerifyAllFieldsDisplayed() {
       
        Assert.assertTrue(loginPage.getElement("Username").isDisplayed(), "Username field not displayed");
        Assert.assertTrue(loginPage.getElement("Password").isDisplayed(), "Password field not displayed");
        Assert.assertTrue(loginPage.getElement("Checkbox").isDisplayed(), "Remember me checkbox not displayed");
        Assert.assertTrue(loginPage.getElement("Loginbtn").isDisplayed(), "Login button not displayed");
    }

    @Test(priority = 2)
    public void TC02_VerifyPlaceholderText() {
       
        String usernamePlaceholder = loginPage.getAttribute("Username" ,"placeholder");
        String passwordPlaceholder = loginPage.getAttribute("Password", "placeholder");
        Assert.assertEquals(usernamePlaceholder, "Enter Username");
        Assert.assertEquals(passwordPlaceholder, "Enter Password");
    }

    @Test(priority = 3)
    public void TC03_LoginWithValidCredentials() {
        
        loginFromExcelRow(1);
        wait.until(ExpectedConditions.urlContains("dashboard"));
        Assert.assertTrue(driver.getCurrentUrl().equals(prop.getProperty("dashboardurl")), "Login failed with valid credentials");
        
    }

    @Test(priority = 4)
    public void TC04_LoginWithInvalidCredentials(){
       
       loginFromExcelRow(2);
        WebElement error = loginPage.getElement("ErrorCredentials");
        Assert.assertTrue(error.isDisplayed());  
        }

    @Test(priority = 5)
    public void TC05_LoginWithEmptyFields() {
       
        loginFromExcelRow(3);
        Assert.assertTrue(loginPage.getElement("ErrorUsername").isDisplayed());  
        Assert.assertTrue(loginPage.getElement("ErrorPassword").isDisplayed());  

    }

    @Test(priority = 6)
    public void TC06_LoginWithEmptyUsername() {
       
        
    	loginFromExcelRow(4);
        Assert.assertTrue(loginPage.getElement("ErrorUsername").isDisplayed());  

    }

    @Test(priority = 7)
    public void TC07_LoginWithEmptyPassword() {
        
       loginFromExcelRow(5);
        Assert.assertTrue(loginPage.getElement("ErrorPassword").isDisplayed());  
    }

    @Test(priority = 8)
    public void TC08_VerifyPasswordMasked() {
        
        String typeAttribute = driver.findElement(By.xpath(loc.getProperty("Password"))).getAttribute("type");
        Assert.assertEquals(typeAttribute, "password", "Password field is not masked");
    }

    @Test(priority = 9)
    public void TC09_RememberMeCheckboxFunctionality() throws InterruptedException {
       
        loginFromExcelRow(1);
        Thread.sleep(3000);
        loginPage.logout();

        String rememberedUsername = loginPage.getAttribute("Username","value");
        String rememberedPassword = loginPage.getAttribute("Password","value");

        Assert.assertEquals(rememberedUsername, "abhilasha01", "Username is not remembered");
        Assert.assertEquals(rememberedPassword, "Tiwari(&%975", "Password is not remembered");  
    }

    @Test(priority = 10)
    public void TC10_LoginWithoutSelectingRememberMe() throws InterruptedException {
       
        loginFromExcelRow(1);
        Thread.sleep(4000);

        loginPage.logout();

        String usernameField = loginPage.getAttribute("Username","value");
        String passwordField = loginPage.getAttribute("Password","value");

        Assert.assertTrue(usernameField.isEmpty(), "Username should not be remembered after logout.");
        Assert.assertTrue(passwordField.isEmpty(), "Password should not be remembered after logout.");
    }


   @Test(priority = 11)
   public void TC13_VerifyErrorMessageDisappearsAfterCorrectingInput() throws InterruptedException {
       
       loginFromExcelRow(2);
       Thread.sleep(1000);

       Assert.assertTrue(loginPage.getElement("ErrorCredentials").isDisplayed());

       driver.navigate().refresh();
       loginFromExcelRow(1);
       Thread.sleep(2000);
       Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("dashboardurl"));
   }
   
  @Test(priority = 12)
   public void TC14_VerifyCheckboxDefaultState() {
      
        boolean isChecked = loginPage.getElement("Checkbox").isSelected();
        Assert.assertFalse(isChecked, "Checkbox should not be selected by default");
    }

   @Test(priority = 13)
    public void TC17_VerifyLoginWithSpacesBeforeAfterUsername() {
        loginFromExcelRow(7);
        Assert.assertNotEquals(driver.getCurrentUrl(), prop.getProperty("testurl"), "Login failed with spaces around username");
    }

    @Test(priority = 14)
    public void TC18_VerifyLoginWithSpacesBeforeAfterPassword() {
      
        loginFromExcelRow(8);
        Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("adminurl"));
   }
   
    @AfterMethod
    public void afterEachTest() throws InterruptedException {
    	   loginPage = new LoginPage(driver, loc);
        try {
            if (driver.getCurrentUrl().contains("dashboard")) {
                loginPage.logout();
            }
        } catch (Exception e) {
            System.out.println("Already logged out or logout not possible: " + e.getMessage());
        }
        driver.get(prop.getProperty("adminurl"));
    }
}


