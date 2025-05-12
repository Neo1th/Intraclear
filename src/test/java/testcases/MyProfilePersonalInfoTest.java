package testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import Pages.LoginPage;
import Pages.MyProfilePersonalInfoPage;
import base.BaseTest;
import utilities.ReadXls;

public class MyProfilePersonalInfoTest extends BaseTest{
	
	LoginPage loginpage;
	MyProfilePersonalInfoPage Myprofile;
	
	
	 @BeforeClass
	 public void loginbeforetest() throws InterruptedException {
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    driver.get(prop.getProperty("adminurl"));
	        loginpage = new LoginPage(driver, loc);
	        loginpage.enterUsername("Sanket01");
	        loginpage.enterPassword("Sanket@73");
	        loginpage.clickLoginButton();
	        wait.until(ExpectedConditions.urlContains("dashboard"));
	        Myprofile = new MyProfilePersonalInfoPage(driver,loc);
		 
	 }
	 @BeforeMethod
	 public void mainpage() throws InterruptedException {
		    driver.get(prop.getProperty("adminurl"));
            Thread.sleep(3000);
	 }
	 
	 public void gotomyprofile() {
		 Myprofile.ClickViewProfilebtn();
		 Myprofile.ClickEditInfoTab();;
	 }
	 

      public void usernameFromExcelRow(int rowNum)  {
	        Map<String, String> rowData = null;
			try {
				rowData = ReadXls.getRowData(filepath, "MyProfileInfo", rowNum);
			} catch (EncryptedDocumentException | IOException e) {
				
				e.printStackTrace();
			}
	        
	        String username = rowData.get("Username"); 
	        
	        Myprofile.enterName(username);
	  
	  }
      public void userEmailFromExcelRow(int rowNum)  {
	        Map<String, String> rowData = null;
			try {
				rowData = ReadXls.getRowData(filepath, "MyProfileInfo", rowNum);
			} catch (EncryptedDocumentException | IOException e) {
				
				e.printStackTrace();
			}
	        
	        String email = rowData.get("Email"); 
	        
	        Myprofile.enterEmail(email);
	  
	  }
	 
	 
	 
	 @Test(priority=1)
	 public void TC01() throws InterruptedException {
	     Myprofile.ClickViewProfilebtn();
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Type']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Avatar']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[contains(text(),'Name')]"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[contains(text(),'E-mail Address')]"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Timezone']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Account Created']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("(//th[@class='border-bottom-0'])[1]"))).isDisplayed());
       	 
	 }
	 
	 @Test(priority=2)
	 public void TC02() throws InterruptedException {
		 
		 Myprofile.ClickImageicon();
		 driver.findElement(By.xpath(loc.getProperty("MyAccountonicon"))).click();

		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Type']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Avatar']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[contains(text(),'Name')]"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[contains(text(),'E-mail Address')]"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Timezone']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Account Created']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("(//th[@class='border-bottom-0'])[1]"))).isDisplayed());
	 
		
	 }
	 
	 @Test(priority=3)
	 public void TC03() throws InterruptedException {
		 Myprofile.ClickMyAccountbtn();
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Type']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Avatar']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[contains(text(),'Name')]"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[contains(text(),'E-mail Address')]"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Timezone']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("//th[normalize-space()='Account Created']"))).isDisplayed());
		 Assert.assertTrue(driver.findElement(By.xpath(("(//th[@class='border-bottom-0'])[1]"))).isDisplayed());
          
	 }
	 
	 @Test(priority=5)
	 public void TC05()  {
		 
		 gotomyprofile();
		 Assert.assertTrue(Myprofile.getElement("UpdateName").isDisplayed());
		 Assert.assertTrue(Myprofile.getElement("UpdateEmail").isDisplayed());
         Assert.assertTrue(Myprofile.getElement("Updatebtn").isDisplayed());
	 }
	 
	 @Test(priority=6)
	 public void TC06()  {
		 
		 gotomyprofile();
		 String actualName = Myprofile.getAttribute("UpdateName", "value");
		 String actualEmail = Myprofile.getAttribute("UpdateEmail", "value");
		
		 Assert.assertEquals(actualName,"Sanket Tiwari" );
		 Assert.assertEquals(actualEmail,"sanket.tiwari@izisstechnology.com" );

	 }
	 
//	 @Test(priority=7)
//	 public void TC07()  {
//		 
//		 gotomyprofile();
//		 usernameFromExcelRow(1);
//		 userEmailFromExcelRow(1);
//		 Myprofile.clickUpdate();
//		 Assert.assertTrue(Myprofile.getElement("UpdateAlert").isDisplayed());	 
//
//	 }
	 
	 @Test(priority=8)
	 public void TC08()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(2);
		 userEmailFromExcelRow(2);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("EmailError").isDisplayed());	 

	 }
	 
//	 @Test(priority=9)
//	 public void TC09()  {
//		 
//		 gotomyprofile();
//		 usernameFromExcelRow(3);
//		 Myprofile.clickUpdate();
//		 Assert.assertTrue(Myprofile.getElement("UpdateAlert").isDisplayed());	 
//
//	 }
	 
	 @Test(priority=10)
	 public void TC10()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(4);
		 userEmailFromExcelRow(4);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("EmailError").isDisplayed());	 

	 }
	 
	 @Test(priority=11)
	 public void TC11()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(5);
		 userEmailFromExcelRow(5);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	 

	 }
	 
	 @Test(priority=12)
	 public void TC12()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(6);
		 userEmailFromExcelRow(6);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	 

	 }
	 
	 
	 
	 @Test(priority=13)
	 public void TC13()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(7);
		 userEmailFromExcelRow(7);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("EmailError").isDisplayed());	
		 
	 }
	 
	 @Test(priority=14)
	 public void TC14()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(8);
		 userEmailFromExcelRow(8);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	
		 Assert.assertTrue(Myprofile.getElement("EmailError").isDisplayed());	
		 
	 }
	 
	 @Test(priority=15)
	 public void TC15()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(9);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	
		 
	 }
	 @Test(priority=16)
	 public void TC16()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(10);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	
		 
	 }
	
	 @Test(priority=17)
	 public void TC17()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(11);
		 userEmailFromExcelRow(11);
     	 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	
		 
	 }
	 @Test(priority=18)
	 public void TC18()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(12);
		 userEmailFromExcelRow(12);

		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	
		 
	 }
	 
//	 @Test(priority=19)
//	 public void TC19()  {
//		 
//		 gotomyprofile();
//		 userEmailFromExcelRow(13);
//
//		 Myprofile.clickUpdate();
//		 Assert.assertTrue(Myprofile.getElement("UpdateAlert").isDisplayed());	
//		 
//	 }
	 @Test(priority=20)
	 public void TC20()  {
		 
		 gotomyprofile();
		 userEmailFromExcelRow(14);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("EmailError").isDisplayed());	
 
	 }
	 
	 @Test(priority=21)
	 public void TC21()  {
		 
		 gotomyprofile();
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("UpdateAlert").isDisplayed());	 

	 }
	 
	 @Test(priority=22)
	 public void TC22()  {
		 
		 gotomyprofile();
		 userEmailFromExcelRow(16);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("EmailError").isDisplayed());	

	 }
	 
	 @Test(priority=23)
	 public void TC23()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(17);
		 userEmailFromExcelRow(17);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	 


	 }
	 @Test(priority=24)
	 public void TC24()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(18);
		 userEmailFromExcelRow(18);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("EmailError").isDisplayed());	

	 }
	 @Test(priority=25)
	 public void TC25()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(19);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	
		 
	 }
	 @Test(priority=26)
	 public void TC26()  {
		 
		 gotomyprofile();
		 usernameFromExcelRow(20);
		 userEmailFromExcelRow(20);
		 Myprofile.clickUpdate();
		 Assert.assertTrue(Myprofile.getElement("NameError").isDisplayed());	
		 Assert.assertTrue(Myprofile.getElement("EmailError").isDisplayed());	

	
     }
	 
	 
}
