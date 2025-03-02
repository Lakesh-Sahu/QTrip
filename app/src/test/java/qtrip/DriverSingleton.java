package qtrip;
    
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// Single class to create only one instance of WebDriver at a time
public class DriverSingleton {

    // Create privat, static WebDriver instance and initilize as null
    private static WebDriver driver = null;

    // Create private constructor so that no one could create object of this class
    private DriverSingleton() {}

    // Define public static method which
    // creates new WebDriver if it is null and returns the WebDriver
    // or returns the object if it is not null
    public static WebDriver getDriver() throws MalformedURLException {
        if (driver == null) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            System.out.println("createDriver()");
        }
        return driver;
    }


    // For local machine to create single instance through out the test cases
    // public static WebDriver getDriver(String browser) throws MalformedURLException {
    //     if (driver == null) {
    //         switch (browser) {
    //             case "Chrome":
    //                 driver = new ChromeDriver();
    //                 break;

    //             case "Edge":
    //                 driver = new EdgeDriver();
    //                 break;

    //             case "Firefox":
    //                 driver = new FirefoxDriver();
    //                 break;

    //             default:
    //                 System.out.println("Driver not found for given browser");
    //                 break;
    //         }
    //     }
    //     return driver;
    // }
}