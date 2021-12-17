package source_package;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {

	public static void takeScreenshot(WebDriver driver, String name) throws IOException {

		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			TakesScreenshot scrShot = ((TakesScreenshot) driver);

			// Call getScreenshotAs method to create image file

			File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

			// Move image file to new destination

			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd");
			String strDate = formatter.format(date);

			File DestFile = new File("./Screenshots/" + strDate + "-" + name + ".png");

			// Copy file at destination

			FileUtils.copyFile(SrcFile, DestFile);
			System.out.println(name + " Screenshot is taken successfully \n");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
