package tw.java.utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.TestListenerAdapter;

import tw.java.pages.BodgeIt;


public class BaseTest extends TestListenerAdapter {
	public WebDriver driver;
	public BodgeIt homePage;
	public String browser = "firefox";
	
	@BeforeClass
	public void intializeDriver(Object[] arg0) throws IOException, InterruptedException {
		driver = BrowserFactory.getBrowser(browser);
		driver.get("http://localhost:8080/bodgeit/");
	}

	@BeforeMethod
	public void beforeMethod() throws Exception {
		homePage = new BodgeIt(driver);

	}

	@AfterMethod
	public void afterMethod() throws Exception {

	}

	@AfterClass
	public void closeBrowser(){
		driver.manage().deleteAllCookies();
		driver.quit();
	}

	@AfterSuite
	public void afterSuite() {
		if(driver!=null)
			driver.quit();
	}

}
