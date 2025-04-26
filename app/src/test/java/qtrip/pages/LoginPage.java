package qtrip.pages;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import qtrip.SeleniumWrapper;

public class LoginPage extends SeleniumWrapper {
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
            return url.contains(driver.getCurrentUrl());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean enterEmail(String email) {
        try {
            return sendKeys(emailInput, email);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean enterPassword(String password) {
        try {
            return sendKeys(passwordInput, password);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickLoginBtn() {
        try {
            return click(loginBtn);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean performLogin(String username, String password) {
        try {
            return enterEmail(username) && enterPassword(password) && clickLoginBtn();
        } catch (Exception e) {
            return false;
        }
    }
}