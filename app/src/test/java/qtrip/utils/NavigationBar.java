package qtrip.utils;

import org.openqa.selenium.WebElement;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
  
public class NavigationBar { 
    WebDriver driver; 
    WebDriverWait wait;
     
    @FindBy(linkText = "Home")  
    WebElement homeBtn; 

    @FindBy(linkText = "Reservations")
    WebElement reservationBtn; 

    @FindBy(xpath = "//div[text()='Logout']") 
    public WebElement logoutBtn;

    @FindBy(linkText = "Login Here")
    WebElement loginBtn;

    @FindBy(linkText = "Register")
    public WebElement registerBtn; 

    @FindBy(linkText = "QTrip")
    WebElement qTripBtn;

    public NavigationBar(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(this.driver, 10);
        PageFactory.initElements(ajax, this);
    }

    public boolean clickHomeBtn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(homeBtn)).click();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean clickReservationBtn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(reservationBtn)).click();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean clickLoginBtn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginBtn)).click();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean clickLogoutBtn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(logoutBtn)).click();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean verifyUserLoggedOut() {
        try {
            wait.until(ExpectedConditions.visibilityOf(registerBtn));
            wait.until(ExpectedConditions.visibilityOf(loginBtn));
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public WebElement getRegisterBtn() {
        return wait.until(ExpectedConditions.visibilityOf(registerBtn));
    }

    public boolean clickregisterBtn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(registerBtn)).click();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public boolean clickqTripBtn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(qTripBtn)).click();
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}