package testcases;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import Pages.LoginPage;
import Pages.MyProfilePasswordPage;
import base.BaseTest;
import utilities.ReadXls;
import Pages.MyProfilePersonalInfoPage;


public class MyProfilePasswordTest extends BaseTest{
	
	LoginPage loginpage;
	MyProfilePasswordPage Myprofilepass;
	MyProfilePersonalInfoPage Myprofile;
	
	@BeforeClass
	public void loginbeforetest() {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		loginpage =new LoginPage(driver,loc);
		Myprofile=new MyProfilePersonalInfoPage(driver,loc);
		Myprofilepass=new MyProfilePasswordPage(driver,loc);	
		loginpage.enterUsername("Sanket01");
		loginpage.enterPassword("Sanket@73");
		loginpage.clickLoginButton();
		wait.until(ExpectedConditions.urlContains("dashboard"));
		
	}
	@BeforeMethod
	public void load() {
	    driver.get(prop.getProperty("adminurl"));
		Myprofile.ClickViewProfilebtn();
		Myprofile.ClickEditInfoTab();
		
	}
	
	public void getdatafromexcel(int rowNum) {
		Map<String,String> rowdata=null;
		try
		{
		 rowdata=ReadXls.getRowData(filepath, "MyProfilePassword" , rowNum);		
		}
		catch(EncryptedDocumentException | IOException e) {
		 e.printStackTrace();
		}
		String currentpass= rowdata.get("Current Password");
		String newpass=rowdata.get("New Password");
		String confirmpass=rowdata.get("Confirm Password");
		
		System.out.println("Current: " + currentpass);
	    System.out.println("New: " + newpass);
	    System.out.println("Confirm: " + confirmpass);
		
	    if (currentpass != null && newpass != null && confirmpass != null) {
		Myprofilepass.enterCurrentPassword(currentpass);
		Myprofilepass.enterNewPassword(newpass);
		Myprofilepass.enterConfirmPassword(confirmpass);
	    } 
	}
	
	@Test(priority=1)
	public void TC01() {
		Assert.assertTrue(Myprofilepass.getElement("CurrentPassword").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("NewPassword").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPassword").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("UpdatePasswordbtn").isDisplayed());	
	}
	
//	@Test(priority=2)
//	public void TC02() {
//       
//		getdatafromexcel(1);
//		Myprofilepass.clickUpdatePassword();
//		Assert.assertTrue("");
//	}
	
	@Test(priority=3)
	public void TC03() {
		getdatafromexcel(2);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("CurrentPasswordError").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("NewPasswordError").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	
	}
	
	@Test(priority=4)
	public void TC04() {
		getdatafromexcel(3);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("NewPasswordError").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=5)
	public void TC05() {
		getdatafromexcel(4);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("CurrentPasswordError").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("NewPasswordError").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=6)
	public void TC06() {
		getdatafromexcel(5);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("PasswordAlert").isDisplayed());
	}

	@Test(priority=7)
	public void TC07() {
		getdatafromexcel(6);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("PasswordAlert").isDisplayed());
	}
	
	@Test(priority=8)
	public void TC08() {
		getdatafromexcel(7);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("NotYourPasswordAlert").isDisplayed());
	}

	@Test(priority=9)
	public void TC09() {
		getdatafromexcel(8);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=10)
	public void TC10() {
		getdatafromexcel(9);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("NewPasswordError").isDisplayed());
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());

	}
	
	@Test(priority=11)
	public void TC11() {
		getdatafromexcel(10);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=12)
	public void TC12() {
	getdatafromexcel(11);
	Myprofilepass.clickUpdatePassword();
	Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=13)
	public void TC13() {
    	getdatafromexcel(12);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=14)
	public void TC14() {
		getdatafromexcel(13);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("NewPasswordError").isDisplayed());
	}
	
	@Test(priority=15)
	public void TC15() {
		getdatafromexcel(14);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=16)
	public void TC16() {
		getdatafromexcel(15);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=17)
	public void TC17() {
		getdatafromexcel(16);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("ConfirmPasswordError").isDisplayed());
	}
	
	@Test(priority=18)
	public void TC18() {
		getdatafromexcel(17);
		Myprofilepass.clickUpdatePassword();
		Assert.assertTrue(Myprofilepass.getElement("NewPasswordError").isDisplayed());
	}
	
}

