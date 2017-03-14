package tw.java.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import tw.java.pages.BodgeIt;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest extends TestListenerAdapter {
	public ExtentTest testReporter;
	public WebDriver driver;

	public BodgeIt homePage;
	InputStream input = null;
	Properties prop = new Properties();

	@BeforeClass
	public void intializeDriver(Object[] arg0) throws IOException, InterruptedException {
		input = new FileInputStream("config.properties");
		prop.load(input);
		String browser = prop.getProperty("browser").trim();
		driver = BrowserFactory.getBrowser(browser);
		loadURL();
	}

	@BeforeMethod
	public void beforeMethod(Method caller, ITestContext ctx) throws Exception {
		homePage = new BodgeIt(driver);

		ExtentTestManager.startTest(caller.getName(), getClass().getSimpleName())
		.assignCategory(Thread.currentThread().getName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result, Method caller) throws Exception {
		if (result.isSuccess()) {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = CaptureScreenshot.captureScreenshot(driver, caller.getName());
			String image = "Screenshot below"+ExtentTestManager.getTest().addScreenCapture(screenshotPath);
			ExtentTestManager.getTest().log(LogStatus.INFO, image);
			ExtentTestManager.getTest().log(LogStatus.FAIL, "<pre>" + getStackTrace(result.getThrowable()) + "</pre>");
			System.out.println("******" + result.getThrowable().toString());
		} else if (result.getStatus() == ITestResult.SKIP)
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped");

		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}

	protected String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
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
		ExtentManager.getInstance().flush();
	}

	public void loadURL() throws IOException, InterruptedException {
		driver.get("http://localhost:8080/bodgeit/");
		waitForHomePageLoad();
	}

	private void waitForHomePageLoad() throws InterruptedException {
		Thread.sleep(1000);
	}
}
