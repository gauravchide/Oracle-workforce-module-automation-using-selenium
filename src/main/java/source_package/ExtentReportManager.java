package source_package;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ExtentReportManager {
public static ExtentReports report;
	
	public static ExtentReports getReportInstance(){
		
		if(report == null){
			
			
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "\\test-output\\testreport.html");
			report =  new ExtentReports();
			report.attachReporter(htmlReporter);
			
			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("Browser", "Chrome,Edge");
			
			htmlReporter.config().setDocumentTitle("OSP WORFORCE ADMINISTRATION AUTOMATION");
			htmlReporter.config().setReportName("OSP WORKFORCE ADMINISTRATION REPORT");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		}
		
		return report;
	}

}
