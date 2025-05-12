package testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.ForgetPasswordPage;
import base.BaseTest;
import utilities.ReadXls;

public class ForgetPasswordTest extends BaseTest {
	
	 ForgetPasswordPage forgetpassword;
	 WebDriverWait wait;
	 
	  @BeforeMethod
	    public void initPage() throws InterruptedException {
	    	Thread.sleep(2000);  
	        driver.get(prop.getProperty("adminurl"));
	        forgetpassword=new ForgetPasswordPage(driver,loc);
	        forgetpassword.clickForgetpassword();
	        wait=new WebDriverWait(driver,Duration.ofSeconds(10));
	   }  
	  
	  @AfterMethod
	    public void pauseAfterTest() throws InterruptedException {
	        Thread.sleep(4000); 
	        driver.get(prop.getProperty("adminurl"));
	    }

	  public void usernameFromExcelRow(int rowNum)  {
	        Map<String, String> rowData = null;
			try {
				rowData = ReadXls.getRowData(filepath, "ForgetPassword", rowNum);
			} catch (EncryptedDocumentException | IOException e) {
				
				e.printStackTrace();
			}
	        
	        String username = rowData.get("Username"); 
	        forgetpassword.enterUsername(username);
	  
	  }
	  @Test(priority=1)
	  public void TC01() throws InterruptedException{
	     
		  Assert.assertTrue(driver.getCurrentUrl().contains("password"));
	  }
	  
	  @Test(priority=2)
	  public void TC02() throws InterruptedException {
		    Assert.assertTrue(forgetpassword.getElement("UsernameinReset").isDisplayed());
	        Assert.assertTrue(forgetpassword.getElement("Continuebtn").isDisplayed());
	        Assert.assertTrue(forgetpassword.getElement("Backtolginbtn").isDisplayed());    
	              
	    }
		  
	  @Test(priority=3)
	  public void TC03() {
		  forgetpassword.clickBackToLogin();
		  Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("adminurl"));
		  
	  }
	
	 @Test(priority=4)
	 public void TC04() throws InterruptedException {
		 usernameFromExcelRow(1);
		 forgetpassword.clickBackToLogin();
		 forgetpassword.clickForgetpassword();
		 String Ename=forgetpassword.getAttribute("UsernameinReset","value");
		 Assert.assertEquals(Ename, ""); 
	 }
	 
	 @Test(priority=5)
	public void TC05() {
		 usernameFromExcelRow(2);
		 forgetpassword.clickContinue();
		 WebElement error = forgetpassword.getElement("FPUsernameError");
	     Assert.assertTrue(error.isDisplayed());  		 
	 }

	 @Test(priority=6)
	 public void TC06() {
		 usernameFromExcelRow(3);
		 forgetpassword.clickContinue();
	     WebElement error = forgetpassword.getElement("FPUsernameError");
	     Assert.assertTrue(error.isDisplayed());  
	 }
	 
	 @Test(priority=7)
	 public void TC07()
	 {
		 usernameFromExcelRow(4);
		 forgetpassword.clickContinue();
		 WebElement toast = forgetpassword.getElement("EmailToast");
	     Assert.assertTrue(toast.isDisplayed());  	
	 }
	 
	 
	 @Test(priority=8)
	 public void TC08() {
		 
		 usernameFromExcelRow(1);
		 forgetpassword.clickContinue();
		 Assert.assertEquals(driver.getCurrentUrl(),prop.getProperty("emailPassword"));
		 
	 }
	 
	 @Test(priority=9)
     public void TC09(){
		  usernameFromExcelRow(1);
		 forgetpassword.clickContinue();
		 forgetpassword.Skipfornowbtn();
		 Assert.assertEquals(driver.getCurrentUrl(), prop.getProperty("adminurl"));
		 
	 } 
}
