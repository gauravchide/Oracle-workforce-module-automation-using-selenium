package source_package;

//import java.awt.Desktop.Action;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;



public class Base extends ScreenShot {

	//Objects declaration
	Properties prop;
	WebDriver driver;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	FileInputStream file_in;
	FileOutputStream file_out;
	XSSFRow row;
	Cell cell;
	ExtentHtmlReporter htmlReporter;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	Actions actions;
	JavascriptExecutor executor = (JavascriptExecutor) driver;
	ExtentReports extent;
	
	ExtentTest test;

    
	//makes web page to wait for few seconds when called
	public void wait_sec() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}

	//invoking browser function
	@BeforeMethod(groups= {"invokeBrowser"})
	@Parameters("browser")
	
	public void invokeBrowser(String browser) {

		try {
			
			//instance creation 
			prop = new Properties();
			
			//getting the path of properties file in project 
			FileInputStream file = new FileInputStream(
					System.getProperty("user.dir") + "\\resources\\ObjectRepository\\projectConfig.properties");
			
			//load the file
			prop.load(file);
			
			//invoke using multiple browser
			if(browser.equalsIgnoreCase("chrome")){
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\resources\\Drivers\\chromedriver_win32 (1)\\chromedriver.exe");
				driver = new ChromeDriver();
				
				}
			else
			{
				System.setProperty("webdriver.edge.driver",
						System.getProperty("user.dir") + "\\resources\\Drivers\\edgedriver_win64 (1)\\msedgedriver.exe");
				driver = new EdgeDriver();
			}
		  
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			
			//get the URL
			driver.get(prop.getProperty("websiteURL"));
			
			//send the user name
			driver.findElement(By.xpath(prop.getProperty("username"))).sendKeys("PS");
			wait_sec();
		
			//sending the password
			driver.findElement(By.xpath(prop.getProperty("password"))).sendKeys("PSHR2020");
			wait_sec();
		
			//click sign in
			driver.findElement(By.xpath(prop.getProperty("sigin_btn"))).click();
			wait_sec();
			
			//click on navigator icon
			driver.findElement(By.xpath(prop.getProperty("navigator_icon"))).click();
			wait_sec();
         
			//switching to frame
			driver.switchTo().frame("psNavBarIFrame");
			wait_sec();
			
			//click on navigator
			
			driver.findElement(By.xpath(prop.getProperty("navigator_in_submenu"))).click();
			wait_sec();
			
			//click on work force administration
			driver.findElement(By.xpath(prop.getProperty("workforce_administration"))).click();;
			wait_sec();
		} 
		catch (Exception e) {
			reportFail(e.getMessage()); 
		}
	}
	
	
	@Test(groups= {"Regression"} )
	public void employeePaidLeave() {
		
		//creating test case name using logger
		logger = report.createTest("Selecting paid leave for an employee");
		wait_sec();
		
		//click on job information
		driver.findElement(By.xpath(prop.getProperty("JobInfo_Xpath"))).click();
		wait_sec();
		
		//click on job data
		driver.findElement(By.xpath(prop.getProperty("JobData_Xpath"))).click();
		wait_sec();
		
		//switch to frame
		driver.switchTo().frame("ptifrmtgtframe");
		wait_sec();
		
		//enter employee id
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement empid_text = driver.findElement(By.xpath(prop.getProperty("empIDTextbox_Xpath")));
		executor.executeScript("arguments[0].value='K0G002';", empid_text);
		WebElement searchBtn;
		
		//click on search button
		searchBtn = driver.findElement(By.xpath(prop.getProperty("empSearchBtn_Xpath")));
		executor.executeScript("arguments[0].click();", searchBtn);
		wait_sec();
		logger.log(Status.INFO, "employee id entered and clicked on search");
		
		//click on correct history button at the end of page
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("correctHistclick_Xpath"))));
		WebElement correct_history = driver.findElement(By.xpath(prop.getProperty("correctHistclick_Xpath")));
		executor.executeScript("arguments[0].click();", correct_history);
		wait_sec();
		logger.log(Status.INFO, "correct history clicked");
		
		
		//check id
		Assert.assertEquals(driver.findElement(By.xpath(prop.getProperty("checkid"))).getText(), "K0G002");
		
		//click on plus symbol to add new row
		driver.findElement(By.xpath(prop.getProperty("addRowplus_Xpath"))).click();
		wait_sec();
		logger.log(Status.INFO, "new row added");
		
		//select the action as paid leave from absence
		driver.findElement(By.xpath(prop.getProperty("action")));
		Select action = new Select(driver.findElement(By.xpath(prop.getProperty("action"))));
		action.selectByVisibleText("Paid Leave of Absence");
		wait_sec();
		logger.log(Status.INFO, "Paid leave of absence selected in action");
		
		//select the reason as short term disability
		Select reason = new Select(driver.findElement(By.xpath(prop.getProperty("reason"))));
		reason.selectByVisibleText("Short-Term Disability");
		logger.log(Status.INFO, "short term disability selected in reason");
		wait_sec();
		
		//select the effective date
		WebElement effDate = driver.findElement(By.xpath(prop.getProperty("effective_date")));
		effDate.clear();
		effDate.sendKeys("04/06/2021");
		wait_sec();
		logger.log(Status.INFO, "date selected");
		
		
		//click on save
		try {
			WebElement save_btn = driver.findElement(By.xpath(prop.getProperty("save")));
		
			executor.executeScript("arguments[0].click();", save_btn);
			takeScreenshot(driver,"paid leave");
			reportPass("Paid leave selected Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		wait_sec();

	}

	@Test(groups= {"Regression"} )
	public void employeeReturnFromPaidLeave() {


		//creating test case name using logger
		logger = report.createTest("Returning from paid leave");
		
		//click on job information
		driver.findElement(By.xpath(prop.getProperty("JobInfo_Xpath"))).click();
		wait_sec();
		
		//click on job data
		driver.findElement(By.xpath(prop.getProperty("JobData_Xpath"))).click();
		wait_sec();
		driver.switchTo().frame("ptifrmtgtframe");
		wait_sec();
		
		//enter employee id
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement empid_text = driver.findElement(By.xpath(prop.getProperty("empIDTextbox_Xpath")));
		executor.executeScript("arguments[0].value='K0G013';", empid_text);
		WebElement searchBtn;
		
		//click on search button
		searchBtn = driver.findElement(By.xpath(prop.getProperty("empSearchBtn_Xpath")));
		executor.executeScript("arguments[0].click();", searchBtn);
		wait_sec();
		logger.log(Status.INFO, "Employee id entered and clicked on search");
		
		//check id
		Assert.assertEquals(driver.findElement(By.xpath(prop.getProperty("checkid"))).getText(), "K0G013");
		
		//click on correct history button at the end of page
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("correctHistclick_Xpath"))));
		WebElement correct_history = driver.findElement(By.xpath(prop.getProperty("correctHistclick_Xpath")));
		executor.executeScript("arguments[0].click();", correct_history);
		wait_sec();
		
		//click on plus symbol to add new row
		driver.findElement(By.xpath(prop.getProperty("addRowplus_Xpath"))).click();
		wait_sec();
		logger.log(Status.INFO, "new row added");
		
		//select the action as return from leave
		driver.findElement(By.xpath(prop.getProperty("action")));
		
		Select action = new Select(driver.findElement(By.xpath(prop.getProperty("action"))));
		action.selectByVisibleText("Return from Leave");
		wait_sec();
		logger.log(Status.INFO, "return from leave is selected in action");
		
		//select the reason as return from leave
		Select reason = new Select(driver.findElement(By.xpath(prop.getProperty("reason"))));
		reason.selectByVisibleText("Return From Leave");
		wait_sec();
		logger.log(Status.INFO, "return from leave is selected in reason");
		
		//select the date
		WebElement effDate = driver.findElement(By.xpath(prop.getProperty("effective_date")));
		effDate.clear();
		effDate.sendKeys("04/01/2021");
		
		try {
			WebElement save_btn = driver.findElement(By.xpath(prop.getProperty("save")));
			
			executor.executeScript("arguments[0].click();", save_btn);
			takeScreenshot(driver,"return from leave");
			reportPass("return from Paid leave selected Successfully");
			wait_sec();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}	
     
	
	@Test(groups= {"Regression"} )
	public void termination() {

		//creating test case name using logger
		logger = report.createTest("Terminating an Employee");
		
		//click on job information
		driver.findElement(By.xpath(prop.getProperty("JobInfo_Xpath"))).click();
		wait_sec();
		
		//click on job data
		driver.findElement(By.xpath(prop.getProperty("JobData_Xpath"))).click();
		wait_sec();
		driver.switchTo().frame("ptifrmtgtframe");
		wait_sec();
		
		//enter employee id
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement empid_text = driver.findElement(By.xpath(prop.getProperty("empIDTextbox_Xpath")));
		executor.executeScript("arguments[0].value='K0G002';", empid_text);
		WebElement searchBtn;
		
		//click on search button
		searchBtn = driver.findElement(By.xpath(prop.getProperty("empSearchBtn_Xpath")));
		executor.executeScript("arguments[0].click();", searchBtn);
		wait_sec();
		logger.log(Status.INFO, "Id entered and search button clicked");
		
		//check id
				Assert.assertEquals(driver.findElement(By.xpath(prop.getProperty("checkid"))).getText(), "K0G002");
		
		//click on correct history button at the end of page
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("correctHistclick_Xpath"))));
		WebElement correct_history = driver.findElement(By.xpath(prop.getProperty("correctHistclick_Xpath")));
		executor.executeScript("arguments[0].click();", correct_history);
		wait_sec();
		
		//click on plus symbol to add new row
		driver.findElement(By.xpath(prop.getProperty("addRowplus_Xpath"))).click();
		wait_sec();
		logger.log(Status.INFO, "New row added");
		driver.findElement(By.xpath(prop.getProperty("action")));
		
		//select the action as termination
		Select action = new Select(driver.findElement(By.xpath(prop.getProperty("action"))));
		action.selectByVisibleText("Termination");
		wait_sec();
		logger.log(Status.INFO, "Termination is selected in action");
		
		//select the reason as misconduct
		Select reason = new Select(driver.findElement(By.xpath(prop.getProperty("reason"))));
		reason.selectByVisibleText("Misconduct");
		wait_sec();
		logger.log(Status.INFO, "Misconduct is selected in reason");
		
		try {
			WebElement save_btn = driver.findElement(By.xpath(prop.getProperty("save")));
			
			executor.executeScript("arguments[0].click();", save_btn);
			takeScreenshot(driver,"termination");
			reportPass("Terminated Successfully");
			wait_sec();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	@Test(groups= {"Regression"} )
	public void rehire() {

		//creating test case name using logger
		logger = report.createTest("Rehiring an Employee");
		
		//click on job information
		driver.findElement(By.xpath(prop.getProperty("JobInfo_Xpath"))).click();
		wait_sec();
		
		//click on job data
		driver.findElement(By.xpath(prop.getProperty("JobData_Xpath"))).click();
		wait_sec();
		driver.switchTo().frame("ptifrmtgtframe");
		wait_sec();
		
		//enter employee id
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement empid_text = driver.findElement(By.xpath(prop.getProperty("empIDTextbox_Xpath")));
		executor.executeScript("arguments[0].value='K0G008';", empid_text);
		
		//click on search button
		WebElement searchBtn;
		searchBtn = driver.findElement(By.xpath(prop.getProperty("empSearchBtn_Xpath")));
		executor.executeScript("arguments[0].click();", searchBtn);
		wait_sec();
		logger.log(Status.INFO, "Id enetered and search button clicked");
		
		//check id
		Assert.assertEquals(driver.findElement(By.xpath(prop.getProperty("checkid"))).getText(), "K0G008");
		
		
		
		//click on correct history button at the end of page
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("correctHistclick_Xpath"))));
		WebElement correct_history = driver.findElement(By.xpath(prop.getProperty("correctHistclick_Xpath")));
		executor.executeScript("arguments[0].click();", correct_history);
		wait_sec();
		
		//click on plus symbol to add new row
		driver.findElement(By.xpath(prop.getProperty("addRowplus_Xpath"))).click();
		wait_sec();
		logger.log(Status.INFO, "New row added");
		
		//select the action as rehire
		Select action = new Select(driver.findElement(By.xpath(prop.getProperty("action"))));
		action.selectByVisibleText("Rehire");
		wait_sec();
		logger.log(Status.INFO, "Rehire is selected in action");
		
		//select the reason as rehire
		Select reason = new Select(driver.findElement(By.xpath(prop.getProperty("reason"))));
		reason.selectByVisibleText("Rehire");
		wait_sec();
		logger.log(Status.INFO, "Rehire is selected in reason");

		
		//select the effectiive date
		WebElement effDate = driver.findElement(By.xpath(prop.getProperty("effective_date")));
		effDate.clear();
		effDate.sendKeys("04/05/2021");
		wait_sec();
		
		try {
			WebElement save_btn = driver.findElement(By.xpath(prop.getProperty("save")));
			executor.executeScript("arguments[0].click();", save_btn);
			takeScreenshot(driver,"rehire");
			reportPass("Rehired Successfully");
			wait_sec();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	@Test(groups= {"Smoke"})
	public void changeDepartment() {

		//creating test case name using logger
		logger = report.createTest("Changing Department for an Employee");
		
		//click on job information
		driver.findElement(By.xpath(prop.getProperty("JobInfo_Xpath"))).click();
		wait_sec();
		
		//click on job data
		driver.findElement(By.xpath(prop.getProperty("JobData_Xpath"))).click();
		wait_sec();
		
		driver.switchTo().frame("ptifrmtgtframe");
		
		wait_sec();
		//enter employee id
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement emp_id_txtbox = driver.findElement(By.xpath(prop.getProperty("job_emp_id")));
		executor.executeScript("arguments[0].value = 'K0G020';", emp_id_txtbox);
		
        //clcik on search button
		WebElement search_buttonElement;
		search_buttonElement = driver.findElement(By.xpath(prop.getProperty("job_search_button")));
		executor.executeScript("arguments[0].click()", search_buttonElement);
		wait_sec();
		logger.log(Status.INFO, "Id is entered and search clicked");
		
		//check id
				Assert.assertEquals(driver.findElement(By.xpath(prop.getProperty("checkid"))).getText(), "K0G020");
		
		//click on correct history button at the end of page
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("correct_history"))));
		driver.findElement(By.xpath(prop.getProperty("correct_history"))).click();
		wait_sec();

		//click on plus symbol to add new row
		driver.findElement(By.xpath(prop.getProperty("Plus1"))).click();
		wait_sec();
		logger.log(Status.INFO, "New row is added");

		//select the action
		Select action = new Select(driver.findElement(By.xpath(prop.getProperty("data_change_action"))));
		action.selectByVisibleText("Data Change");
		wait_sec();
		logger.log(Status.INFO, "Data Change is selected in action");

		//select the reason
		Select reason = new Select(driver.findElement(By.xpath(prop.getProperty("data_change_reason"))));
		reason.selectByVisibleText("Correction-Department");
		wait_sec();
		logger.log(Status.INFO, "Correction-Department is selected in reason");
		
		//select the department
		WebElement department_txtbox = driver.findElement(By.xpath(prop.getProperty("department_txtbox")));
		department_txtbox.clear();
		executor.executeScript("arguments[0].value = '10000';", department_txtbox);
		logger.log(Status.INFO, "Human resources is selected in Department");

		//select the date
		WebElement effDate = driver.findElement(By.xpath(prop.getProperty("effective_date")));
		effDate.clear();
		effDate.sendKeys("04/05/2021");
		wait_sec();
		logger.log(Status.INFO, "Effective date is selected");

		
		try {
			driver.findElement(By.xpath(prop.getProperty("department_save"))).click();
			takeScreenshot(driver,"department change");
			reportPass("Department changed Successfully");
			wait_sec();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		
	}

	@Test(groups= {"Smoke"})
	public void modifyPerson() {

		//creating test case name using logger
		logger = report.createTest("Modifying address of an Employee");
		driver.findElement(By.xpath(prop.getProperty("personal_information"))).click();
		wait_sec();
		
        //click on biographical
		driver.findElement(By.xpath(prop.getProperty("biographical"))).click();
		wait_sec();
		

		//click on modify a person
		driver.findElement(By.xpath(prop.getProperty("modify_a_person"))).click();
		wait_sec();
		

		//switch to frame
		driver.switchTo().frame("ptifrmtgtframe");
		
		
        //enter employee id 
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		WebElement emp_id_box = driver.findElement(By.xpath(prop.getProperty("modify_emp_id")));
		executor.executeScript("arguments[0].value ='NEW15';", emp_id_box);
		
        //click on search button
		WebElement search_button;
		search_button = driver.findElement(By.xpath(prop.getProperty("modify_search_button")));
		executor.executeScript("arguments[0].click()", search_button);
		wait_sec();
		logger.log(Status.INFO, "Id is entered and search button is clicked");
		
		//check id
		Assert.assertEquals(driver.findElement(By.xpath(prop.getProperty("personid"))).getText(), "NEW15");
		wait_sec();
		
		//click on correct history button at the end of page
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty("correct_his_contact"))));
		driver.findElement(By.xpath(prop.getProperty("correct_his_contact"))).click();
		wait_sec();
		

		//click on contact tab
		driver.findElement(By.xpath(prop.getProperty("contact_tab"))).click();
		logger.log(Status.INFO, "Contact Information is clicked");
		
		//click on view address button
		driver.findElement(By.xpath(prop.getProperty("view_address"))).click();
		logger.log(Status.INFO, "View Address is clicked");
		
		//click on update button
		driver.findElement(By.xpath(prop.getProperty("update_add"))).click();
		wait_sec();
		logger.log(Status.INFO, "Update button is clicked");
		
		//clear the text box
		WebElement add1 = driver.findElement(By.xpath(prop.getProperty("add_1")));
		add1.clear();
		
		//enter the address
		add1.sendKeys("103, Raja Street");

		//clear the textbox
		WebElement add2 = driver.findElement(By.xpath(prop.getProperty("add_2")));
		add2.clear();
		
		//enter the 2nd line of address
		add2.sendKeys("Ghandhipuram");

		//clear the textbox
		WebElement city = driver.findElement(By.xpath(prop.getProperty("city")));
		city.clear();
		
		//enter the city
		city.sendKeys("Coimbatore");
		logger.log(Status.INFO, "New Address is entered");

		//click on ok
		driver.findElement(By.xpath(prop.getProperty("ok"))).click();
		wait_sec();
		
		driver.findElement(By.xpath(prop.getProperty("Okay"))).click();
		wait_sec();
		try {
			driver.findElement(By.xpath(prop.getProperty("save_add"))).click();
			takeScreenshot(driver,"addressChange");
			reportPass("Address Changed Successfully");
			wait_sec();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}	
	}

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		
		Assert.fail(reportString);
	}

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	@AfterMethod(groups= {"closeBrowser"})
	public void close() {

		report.flush();
		driver.quit();

	}

}
