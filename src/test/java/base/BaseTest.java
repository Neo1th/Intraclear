package base;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public static WebDriver driver;
    public static Properties prop = new Properties();
    public static Properties loc = new Properties();
    public static FileReader fr;
    public static FileReader fr1;
	public String filepath="/Users/apple/eclipse-workspace/Intraclear/src/test/resources/testdata/Testdata -Intraclear.xlsx";
    public WebDriverWait wait;

    
	@BeforeSuite
	public void Propload() throws IOException {
		
		FileInputStream configFis = new FileInputStream("/Users/apple/eclipse-workspace/Intraclear/src/test/resources/configfiles/config.properties");
		prop.load(configFis);
		
		FileInputStream locFis =new FileInputStream("/Users/apple/eclipse-workspace/Intraclear/src/test/resources/configfiles/locators.properties");
		loc.load(locFis);
	}

    @BeforeTest
    public void setUpBrowser() throws IOException {
        if (driver == null) {
            fr = new FileReader(System.getProperty("user.dir") + "/src/test/resources/configfiles/config.properties");
            fr1 = new FileReader(System.getProperty("user.dir") + "/src/test/resources/configfiles/locators.properties");
            prop.load(fr);
            loc.load(fr1);
        }

        if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (prop.getProperty("browser").equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("adminurl"));
    }


    @AfterClass
    public void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
