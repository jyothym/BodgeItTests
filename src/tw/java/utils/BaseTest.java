package tw.java.utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.TestListenerAdapter;
import org.zaproxy.clientapi.core.ClientApi;
import org.zaproxy.clientapi.core.ClientApiException;
import org.zaproxy.clientapi.gen.Core;

import tw.java.pages.BodgeIt;

public class BaseTest extends TestListenerAdapter {
	public WebDriver driver;
	public BodgeIt homePage;
	public String browser = "firefox";
	ClientApi clientApi = new ClientApi("localhost", 8090, null);
	Core core = new Core(clientApi);
	CreateHTMLReport report = new CreateHTMLReport();

	@BeforeClass
	public void intializeDriver(Object[] arg0) throws Exception {
		System.out.println("Starting ZAP...");
		new ProcessBuilder("/Applications/OWASP ZAP.app/Contents/MacOS/OWASP ZAP.sh").start(); //, "-daemon", "-port" + ZAP_PORT
		Thread.sleep(10000);

		driver = BrowserFactory.getBrowser(browser);
		driver.get("http://localhost:8080/bodgeit/");
	}

	@BeforeMethod
	public void beforeMethod() throws Exception {
		homePage = new BodgeIt(driver);
	}

	@AfterClass
	public void closeBrowser() throws ClientApiException{
		driver.manage().deleteAllCookies();
		driver.quit();

		System.out.println("Generating HTML Report...");
		String alerts_report = new String(core.htmlreport());
		report.generateHTMLFile(alerts_report);

		System.out.println("Shutting down ZAP...");
		clientApi.core.shutdown();
	}

	@AfterSuite
	public void afterSuite() {
		if(driver!=null)
			driver.quit();
	}

}
