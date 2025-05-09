package Pages;


import java.util.Properties;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    
    public LoginPage(WebDriver driver, Properties loc) {
       super(driver,loc);
    }

    public void enterUsername(String username) {
        type("Username", username);
    }

    public void enterPassword(String password) {
        type("Password", password);
    }

    public void clickRememberMe() {
        click("Checkbox");
    }

    public void clickLoginButton() {
        click("Loginbtn");
    }

    public void logout() {
        click("ImageIcon");
        click("Logoutbtn");
    }

    public void clickForgetPassword() {
        click("ForgetPassword");
    }

    public void skipForNow() {
        click("Skipfornowbtn");
    }
    
}
