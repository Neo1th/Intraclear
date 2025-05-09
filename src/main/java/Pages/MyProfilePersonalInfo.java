package Pages;

import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyProfilePersonalInfo extends BasePage {
	
	WebDriver driver;
	Properties loc;
	WebDriverWait wait;
	
	    public MyProfilePersonalInfo(WebDriver driver, Properties loc) {
	        super(driver,loc);
	  
	    }

	   public void ClickViewProfilebtn() {
		   click("ViewProfilebtn");
	   }
	   public void ClickImageicon() {
          click("ImageIcon");		   
	   }
	   
	   public void ClickMyAccountbtn() {
         click("MyAccountbtn");		   
	   }
	    
	   public void ClickMyProfileTab() {
          click("MyProfileTab");		   
	   }
	    
	   public void ClickEditInfoTab() {
          click("EditInfoTab");
	   }
	
	   public void Click2FATab() {
         click("TwoFaTab");
	   }
	   
	   public void enterName(String name){
		   type("UpdateName", name);
	   }
	   
	   public void enterEmail(String email){
		   type("UpdateEmail", email);
	   }
	   
	   public void clickUpdate() {
		   click("Updatebtn");
	   }
	   
	  
}
