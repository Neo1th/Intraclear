package testcases;

import org.testng.Assert;
import org.testng.annotations.*;
import Pages.LoginPage;
import Pages.MyProfilePersonalInfoPage;
import Pages.TwoFAEnableDisablePage;
import base.BaseTest;

public class TwoFAEnableDisable extends BaseTest {
	
	LoginPage loginpage;
	MyProfilePersonalInfoPage Myprofile1;
	TwoFAEnableDisablePage Twofa;
	
	@BeforeClass
	public void loginbeforetest() {
		loginpage = new LoginPage(driver,loc);
		Myprofile1= new MyProfilePersonalInfoPage(driver,loc);
		Twofa = new TwoFAEnableDisablePage(driver,loc);
		loginpage.enterUsername("Sanket01");
		loginpage.enterPassword("Sanket@73");
        loginpage.clickLoginButton();		
		Myprofile1.ClickViewProfilebtn();
		Myprofile1.Click2FATab();
		
	}
	
	@Test(priority=1)
	public void TC01() {
		Assert.assertTrue(Twofa.getElement("Enable2FAbtn").isDisplayed());
	}
	
	@Test(priority=2)
	public void TC02() {
		Twofa.ClickEnable2FaAuth();
		Assert.assertTrue(driver.getCurrentUrl().equals(prop.getProperty("2faenable")));
		Assert.assertTrue(Twofa.getElement("2FAContinuebtn").isDisplayed());
	}
	
	@Test(priority=3)
	public void TC03() {
		Twofa.Clickcontinuebtn();
		Assert.assertTrue(Twofa.getElement("Submit2FAEnable").isDisplayed());
		Assert.assertTrue(Twofa.getElement("AuthField").isDisplayed());
	}

	@Test(priority=4)
	public void TC04() {
		Twofa.Clickrescan();
		Assert.assertTrue(Twofa.getElement("2FAContinuebtn").isDisplayed());
	}
	
	@Test(priority=5)
	public void TC05() throws InterruptedException {
		Twofa.Clickcontinuebtn();
		Twofa.entercode("");
		Thread.sleep(3000);
		Twofa.clicksubmitcode();
		Thread.sleep(2000);
		Assert.assertTrue(Twofa.getElement("AlertEmptycode").isDisplayed());
	}
	
	@Test(priority=6)
	public void TC06() throws InterruptedException {
		Twofa.entercode("123");
		Thread.sleep(3000);
		Twofa.clicksubmitcode();
		Assert.assertTrue(Twofa.getElement("AlertSixchar").isDisplayed());
	}
	@Test(priority=7)
	public void TC07() {
		Twofa.entercode("123321");
		Twofa.clicksubmitcode();
		Assert.assertTrue(Twofa.getElement("Alertinvalidcode").isDisplayed());
	}
	
	
	
}
