package tw.java.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	private static ExtentReports instance;

	public static synchronized ExtentReports getInstance() {
		String fileName = new SimpleDateFormat("ddMMMyy_hhmm").format(new Date());
		if (instance == null)
			instance = new ExtentReports(System.getProperty("user.dir") + "/target/extentReports/" + fileName + "_buynowTests.html");

		return instance;
	}
}
