package qtrip;

import qtrip.utils.Screenshot;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    public WebDriver driver; 

    @BeforeMethod (alwaysRun = true)
    public void createDriver() throws MalformedURLException {
        driver = DriverSingleton.getDriver();
    } 
 
    @BeforeMethod (alwaysRun = true)
    public void beforeTestMethodToStartTest(Method m) {
        ExtentTestManager.startTest(m.getName());
    }

    @AfterMethod (alwaysRun = true)
    public void afterTestMethod(ITestResult iTResult) throws IOException {

        
        if(iTResult.getStatus() == ITestResult.SUCCESS){
            ExtentTestManager.logPass("Step is passed");
        } else if(iTResult.getStatus() == ITestResult.FAILURE) {
            ExtentTestManager.getExtentTest().log(LogStatus.FAIL, ExtentTestManager.getExtentTest().addScreenCapture((Screenshot.capture(driver))));

        } else if(iTResult.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getExtentTest().log(LogStatus.SKIP,  "Step is Skipped");
        }
    }
 
    @AfterSuite (alwaysRun = true)
    public void afterSuit() {
        driver.quit();
        ReportSingleton.getExtentReports().endTest(ExtentTestManager.getExtentTest());
        ReportSingleton.getExtentReports().flush();
    }
}