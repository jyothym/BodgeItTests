package tw.java.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenshot {
	public static String captureScreenshot(WebDriver driver, String methodName) throws Exception {
		String uniqueId = new SimpleDateFormat("ddMMMyy_hhmmss").format(new Date());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "extentReports" + File.separator +"screenshots" + File.separator + methodName + uniqueId +".png";
		FileUtils.copyFile(src, new File(filePath));
		String relativeFilePath = "screenshots" + File.separator + methodName + uniqueId +".png";
		return relativeFilePath;
	}
}
