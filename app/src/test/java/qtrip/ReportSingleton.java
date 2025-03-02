package qtrip;

import java.io.File;
import com.relevantcodes.extentreports.ExtentReports;

public class ReportSingleton {
    static ExtentReports report;

    final static String path = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "report01.html";

    public static synchronized ExtentReports getExtentReports(){
        if (report == null){ 
            report = new ExtentReports(path, true);
        }
        report.loadConfig(new File(System.getProperty("user.dir") + File.separator + "extent_customization_configs.xml"));
        return report;
    }
}