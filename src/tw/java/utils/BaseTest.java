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
	private static final String ZAP_ADDRESS = "localhost";
	private static final int ZAP_PORT = 8090;
	private static final String ZAP_API_KEY = null; // Change this if you have set the apikey in ZAP via Options / API
	ClientApi clientApi = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);
	Core core = new Core(clientApi);
	CreateHTMLReport c = new CreateHTMLReport();
	
	@BeforeClass
	public void intializeDriver(Object[] arg0) throws Exception {
		new ProcessBuilder("/Applications/OWASP ZAP.app/Contents/MacOS/OWASP ZAP.sh").start(); //, "-daemon", "-port" + ZAP_PORT
		System.out.println("Starting ZAP...");
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
		
//		System.out.println("XML report output");
//      String alerts_report = new String(core.xmlreport());

		System.out.println("Generating HTML Report...");
        String alerts_report = new String(core.htmlreport());
        c.generateHTMLFile(alerts_report);
        
        System.out.println("Shutting down ZAP...");
        clientApi.core.shutdown();
	}

	@AfterSuite
	public void afterSuite() {
		if(driver!=null)
			driver.quit();
	}

}
