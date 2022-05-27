package DriverFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import constant.AppUtil;
import utilities.ExcelFileUtils;

public class AppTest extends AppUtil{
	String Inputpath = "E:\\MySeleniumworkspace\\Maven_Project\\TestInput\\LoginData.xlsx";
	String Outputpath = "E:\\MySeleniumworkspace\\Maven_Project\\TestOutput\\DataDrivenResult.xlsx";
	ExtentReports report;
	ExtentTest test;
	@Test
	
	public void VerifyLogin ()throws Throwable
	{
		//define path of html report
		report = new ExtentReports("./ExtentReports/DataDriven.html");
		Properties config = new Properties();
		config.load(new FileInputStream("E:\\MySeleniumworkspace\\Maven_Project\\PropertyFiles\\Environment.properties"));
		//access excel file util methods
		ExcelFileUtils xl = new ExcelFileUtils(Inputpath);
		//count no of rows in login sheet
		int rc =xl.rowCount("Login");
		//count no of cells in row
		int cc =xl.cellCount("Login");
		Reporter.log(rc+"   "+cc,true);
		for(int i=1;i<=rc; i++)
		{
			test =report.startTest("Login Test");
			driver.get(config.getProperty("Url"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//read username and password cell
			String username =xl.getCellData("Login", i, 0);
			String password =xl.getCellData("Login", i, 1);
			driver.findElement(By.xpath(config.getProperty("ObjUser"))).sendKeys(username);
			driver.findElement(By.xpath(config.getProperty("ObjPass"))).sendKeys(password);
			driver.findElement(By.xpath(config.getProperty("ObjLoginbtn"))).click();
			String expected ="dashboard";
			String actual =driver.getCurrentUrl();
			if(actual.contains(expected))
			{
				//write as login success in result cell
				xl.setCellData("Login", i, 2, "Login Success", Outputpath);
				//write as pass in status cell
				xl.setCellData("Login", i, 3, "Pass", Outputpath);
				Reporter.log("Login Success",true);
				test.log(LogStatus.PASS, "Login Success", expected+"    "+actual);
			}
			else
			{
				File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screen,new File("./Screens/Iteration"+i+"Loginpage.png"));
				//capture error message
				String errormessage= driver.findElement(By.xpath(config.getProperty("ObjErrormeessage"))).getText();
				xl.setCellData("Login", i, 2,errormessage, Outputpath);
				xl.setCellData("Login", i, 3, "Fail", Outputpath);
				Reporter.log(errormessage,true);
				test.log(LogStatus.FAIL, errormessage+", "+expected+"    "+actual);
				
			}
			report.flush();
			report.endTest(test);
		}
	}

}
