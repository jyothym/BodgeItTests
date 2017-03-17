package tw.java.utils;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;

import java.net.MalformedURLException;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.CapabilityType;

public class BrowserFactory {

	public static WebDriver getBrowser(String browserName) throws MalformedURLException {
		RemoteWebDriver driver = null;
		DesiredCapabilities capabilities = new DesiredCapabilities();
		ChromeOptions options = new ChromeOptions();
		Dimension d;
		Proxy proxy = new Proxy();

		if (browserName.equals("chrome")) {
			ChromeDriverManager.getInstance().setup();
			options.addArguments("no-sandbox");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(capabilities);

		} else if (browserName.equals("firefox")) {
			String proxyIP = "localhost:8090";
			proxy.setHttpProxy(proxyIP)
			.setFtpProxy(proxyIP)
			.setSslProxy(proxyIP)
			.setNoProxy("");

			FirefoxDriverManager.getInstance().setup();
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			driver = new FirefoxDriver(capabilities);
		}

		d = new Dimension(768,1024);  //iPad
		driver.manage().window().setSize(d);
		return driver;
	}
}

