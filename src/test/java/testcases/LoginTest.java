package testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.testng.Assert;
import Pages.LoginPage;
import base.BaseTest;

public class LoginTest extends BaseTest {

    LoginPage loginPage;
    

    @BeforeMethod
    public void initPage() throws InterruptedException {
    	Thread.sleep(3000);  
        driver.get(prop.getProperty("testurl"));  
        loginPage = new LoginPage(driver, loc); 
      
    }

    @Test(priority = 1)
    public void TC01_VerifyAllFieldsDisplayed() {
       
        Assert.assertTrue(driver.findElement(By.xpath(loc.getProperty("Username"))).isDisplayed(), "Username field not displayed");
        Assert.assertTrue(driver.findElement(By.xpath(loc.getProperty("Password"))).isDisplayed(), "Password field not displayed");
        Assert.assertTrue(driver.findElement(By.xpath(loc.getProperty("Checkbox"))).isDisplayed(), "Remember me checkbox not displayed");
        Assert.assertTrue(driver.findElement(By.xpath(loc.getProperty("Loginbtn"))).isDisplayed(), "Login button not displayed");
    }

    @Test(priority = 2)
    public void TC02_VerifyPlaceholderText() {
       
        String usernamePlaceholder = driver.findElement(By.xpath(loc.getProperty("Username"))).getAttribute("placeholder");
        String passwordPlaceholder = driver.findElement(By.xpath(loc.getProperty("Password"))).getAttribute("placeholder");
        Assert.assertEquals(usernamePlaceholder, "Enter Username");
        Assert.assertEquals(passwordPlaceholder, "Enter Password");
    }

    @Test(priority = 3)
    public void TC03_LoginWithValidCredentials() {
        
        loginPage.enterUsername("abhilasha01");
        loginPage.enterPassword("Tiwari(&%975");
        loginPage.clickLoginButton();
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login failed with valid credentials");
        
      
    }

    @Test(priority = 4)
    public void TC04_LoginWithInvalidCredentials() {
       
        loginPage.enterUsername("Sanket01");
        loginPage.enterPassword("Password");
        loginPage.clickLoginButton();
        Assert.assertTrue(driver.getPageSource().contains("These credentials do not match our record"), "Error message not displayed");
    }

    @Test(priority = 5)
    public void TC05_LoginWithEmptyFields() {
       
        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();
        Assert.assertTrue(driver.getCurrentUrl().equals("http://52.215.105.153/login"), "Validation not displayed for empty fields");
    }

    @Test(priority = 6)
    public void TC06_LoginWithEmptyUsername() {
       
        loginPage.enterUsername("");
        loginPage.enterPassword("Tiwari(&%975");
        loginPage.clickLoginButton();
        Assert.assertTrue(driver.getPageSource().contains("Username is required"), "Validation not displayed for empty username");
    }

    @Test(priority = 7)
    public void TC07_LoginWithEmptyPassword() {
        
        loginPage.enterUsername("abhilasha01");
        loginPage.enterPassword("");
        loginPage.clickLoginButton();
        Assert.assertTrue(driver.getPageSource().contains("Password is required"), "Validation not displayed for empty password");
    }

    @Test(priority = 8)
    public void TC08_VerifyPasswordMasked() {
       
        String typeAttribute = driver.findElement(By.xpath(loc.getProperty("Password"))).getAttribute("type");
        Assert.assertEquals(typeAttribute, "password", "Password field is not masked");
    }

    @Test(priority = 9)
    public void TC09_RememberMeCheckboxFunctionality() throws InterruptedException {
       
        loginPage.enterUsername("abhilasha01");
        loginPage.enterPassword("Tiwari(&%975");
        loginPage.clickRememberMe();
        loginPage.clickLoginButton();
        Thread.sleep(3000);
        loginPage.logout();

        String rememberedUsername = driver.findElement(By.xpath(loc.getProperty("Username"))).getAttribute("value");
        String rememberedPassword = driver.findElement(By.xpath(loc.getProperty("Password"))).getAttribute("value");

        Assert.assertEquals(rememberedUsername, "abhilasha01", "Username is not remembered");
        Assert.assertEquals(rememberedPassword, "Tiwari(&%975", "Password is not remembered");

     
    }

    @Test(priority = 10)
    public void TC10_LoginWithoutSelectingRememberMe() throws InterruptedException {
       
        loginPage.enterUsername("abhilasha01");
        loginPage.enterPassword("Tiwari(&%975");
        loginPage.clickLoginButton();
        Thread.sleep(4000);

        loginPage.logout();

        String usernameField = driver.findElement(By.xpath(loc.getProperty("Username"))).getAttribute("value");
        String passwordField = driver.findElement(By.xpath(loc.getProperty("Password"))).getAttribute("value");

        Assert.assertTrue(usernameField.isEmpty(), "Username should not be remembered after logout.");
        Assert.assertTrue(passwordField.isEmpty(), "Password should not be remembered after logout.");
    }


   @Test(priority = 11)
   public void TC13_VerifyErrorMessageDisappearsAfterCorrectingInput() throws InterruptedException {
       
     loginPage.enterUsername("Sanket011");
       loginPage.enterPassword("Test@123");
       loginPage.clickLoginButton();
       Thread.sleep(2000);

       Assert.assertTrue(driver.getPageSource().contains("These credentials do not match our record"), "Error message not displayed");

        driver.navigate().refresh();
        loginPage.enterUsername("abhilasha01");
       loginPage.enterPassword("Tiwari(&%975");
       loginPage.clickLoginButton();
       Thread.sleep(2000);

       Assert.assertFalse(driver.getPageSource().contains("These credentials do not match our record"), "Error message still displayed after correct input");
   }
  @Test(priority = 12)
   public void TC14_VerifyCheckboxDefaultState() {
      
        boolean isChecked = driver.findElement(By.xpath(loc.getProperty("Checkbox"))).isSelected();
        Assert.assertFalse(isChecked, "Checkbox should not be selected by default");
    }

   @Test(priority = 13)
    public void TC17_VerifyLoginWithSpacesBeforeAfterUsername() {
         loginPage.enterUsername(" abhilasha01 ");
        loginPage.enterPassword("Tiwari(&%975");
        loginPage.clickLoginButton();
        Assert.assertNotEquals(driver.getCurrentUrl(), prop.getProperty("testurl"), "Login failed with spaces around username");
    }

    @Test(priority = 14)
    public void TC18_VerifyLoginWithSpacesBeforeAfterPassword() {
      
        loginPage.enterUsername("abhilasha01");
        loginPage.enterPassword("   Tiwari(&%975 ");
        loginPage.clickLoginButton();
        Assert.assertNotEquals(driver.getCurrentUrl(), "http://52.215.105.153/admin/dashboard");
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
        driver.get(prop.getProperty("testurl"));
    }
}


