package Pages;

import java.util.Properties;
import org.openqa.selenium.WebDriver;

public class MyProfilePassword extends BasePage {

	public MyProfilePassword(WebDriver driver, Properties loc) {
		super(driver, loc);
		
	}
	
	public void enterCurrentPassword(String currentpass) {
		type("CurrentPassword", currentpass);
	}
	
	public void enterNewPassword(String newpass) {
		type("NewPassword", newpass);
	}
	public void enterConfirmPassword(String confirmpass) {
		type("ConfirmPassword", confirmpass);
	}
	
	public void clickUpdatePassword() {
		click("UpdatePasswordbtn");
	}	

}
