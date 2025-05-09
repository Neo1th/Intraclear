package Pages;

import java.util.Properties;
import org.openqa.selenium.WebDriver;

public class ForgetPassword extends BasePage{
	


	public ForgetPassword(WebDriver driver, Properties loc) {
		super(driver,loc);
	}
	
	public void enterUsername(String username) {
	    type("UsernameinReset", username);
	}
	
	 public void clickContinue() {
	        click("Continuebtn");
   }
	 
	 public void clickBackToLogin() {
	        click("Backtolginbtn");
   }
	
	public void clickForgetpassword() {
		    click("ForgetPassword");
	}

	public void Skipfornowbtn() {
		    click("Skipfornowbtn");
		
	}


	
	
}
