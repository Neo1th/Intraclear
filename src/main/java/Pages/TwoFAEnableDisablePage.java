package Pages;

import java.util.Properties;
import org.openqa.selenium.WebDriver;

public class TwoFAEnableDisablePage extends BasePage {

	public TwoFAEnableDisablePage(WebDriver driver, Properties loc) {
		super(driver, loc);
	}

	public void ClickEnable2FaAuth() {
		   click("Enable2FAbtn");
	   }
	public void Clickcontinuebtn() {
		   click("2FAContinuebtn");
	   }
	public void Clickrescan() {
		   click("Rescanbtn");
	   }
	public void entercode(String code) {
		   type("AuthField" ,code);
	   }
	public void clicksubmitcode() {
		click("Submit2FAEnable");
	}
	
}
