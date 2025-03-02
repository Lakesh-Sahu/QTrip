package qtrip;

import java.util.concurrent.ConcurrentHashMap;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentTestManager {
    static ExtentReports extentReport = ReportSingleton.getExtentReports();
    static ConcurrentHashMap<Object, Object> extentMap = new ConcurrentHashMap<>();

    public static ExtentTest startTest(String testName) {
        ExtentTest extentTest = extentReport.startTest(testName);
        extentMap.put(Thread.currentThread().threadId(), extentTest);
        return extentTest;
    }

    public static ExtentTest getExtentTest() {
        return (ExtentTest) extentMap.get(Thread.currentThread().threadId());
    }

    public static void logInfo(String info) {
        getExtentTest().log(LogStatus.INFO, info);
    }

    public static void logPass(String passMessage) {
        getExtentTest().log(LogStatus.PASS, passMessage);
    }

    public static void logFail(String failMessage) {
        getExtentTest().log(LogStatus.FAIL, failMessage);
    }

    public static void endTest() {
        extentReport.endTest(getExtentTest());
        extentReport.flush();
    }
}
