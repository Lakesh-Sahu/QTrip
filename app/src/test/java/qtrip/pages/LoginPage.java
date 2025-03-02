package qtrip.pages;

import static qtrip.SeleniumWrapper.*;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;
    String url;

    @FindBy(name = "email")
    WebElement emailInput;

    @FindBy(name = "password")
    WebElement passwordInput;

    @FindBy(xpath = "//button[text() = 'Login to QTrip']")
    WebElement loginBtn;

    @FindBy(linkText = "Register Now")
    WebElement registerNowBtn;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        url = "https://qtripdynamic-qa-frontend.vercel.app/pages/login/";

        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(this.driver, 10);
        PageFactory.initElements(ajax, this);
    }

    public void navigateToLoginPage() {
        navigate(driver, url);
    }

    public boolean verifyOnLoginPage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginBtn));
            wait.until(ExpectedConditions.visibilityOf(registerNowBtn));
            Assert.assertTrue(url.contains(driver.getCurrentUrl()), "Not on login page");
            return true;
        } catch (AssertionError | Exception e) {
            return false;
        }
    }

    public boolean enterEmail(String email) {
        try {
            sendKeys(emailInput, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean enterPassword(String password) {
        try {
            sendKeys(passwordInput, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickLoginBtn() {
        try {
            click(loginBtn, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean performLogin(String username, String password) {
        try {
            enterEmail(username);
            enterPassword(password);
            clickLoginBtn();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
