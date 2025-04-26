package qtrip;
    
import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

// Single class to create only one instance of WebDriver at a time
public class DriverSingleton {

    // Create private, static WebDriver instance and initialize as null
    private static WebDriver driver = null;

    // Create private constructor so that no one could create object of this class
    private DriverSingleton() {}

    // Define public static method which
    // creates new WebDriver if it is null and returns the WebDriver
    // or returns the object if it is not null
    public static WebDriver getDriver() throws MalformedURLException {
        if (driver == null) {
            driver = getDriver("Chrome");
            driver.manage().window().maximize();
            System.out.println("createDriver()");
        }
        return driver;
    }


//     For local machine to create single instance throughout the test cases
     public static WebDriver getDriver(String browser) throws MalformedURLException {
         if (driver == null) {
             switch (browser) {
                 case "Chrome":
                     driver = new ChromeDriver();
                     break;

                 case "Edge":
                     driver = new EdgeDriver();
                     break;

                 case "Firefox":
                     driver = new FirefoxDriver();
                     break;

                 default:
                     System.out.println("Driver not found for given browser");
                     break;
             }
         }
         return driver;
     }

//    public static void main(String[] args) throws MalformedURLException {
//        WebDriver driver1 = getDriver("Chrome");
//        driver1.get("https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city=bengaluru");
//         System.out.println(driver1.getCurrentUrl());
//     }
}