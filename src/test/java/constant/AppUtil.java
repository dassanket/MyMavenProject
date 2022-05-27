
package constant;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class AppUtil {
public static WebDriver driver;
@BeforeTest
public static void setUp()
{
	System.setProperty("webdriver.chrome.driver", "./CommonDrivers/chromedriver.exe");
	driver = new ChromeDriver();
}
@AfterTest
public void tearDown()
{
	driver.close();
}
}

