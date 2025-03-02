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

public class RegisterPage {
    WebDriver driver;
    WebDriverWait wait;
    String url;

    @FindBy(name = "email")
    WebElement emailInput;

    @FindBy(name = "password")
    WebElement passwordInput;

    @FindBy(name = "confirmpassword")
    WebElement confirmPassword;

    @FindBy(xpath = "//button[text() = 'Register Now']")
    WebElement registerNowBtn;

    @FindBy(linkText = "Login Now")
    WebElement loginNowBtn;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        url = "https://qtripdynamic-qa-frontend.vercel.app/pages/register/";
        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(this.driver, 10);
        PageFactory.initElements(ajax, this);
    }

    public void navigateToRegisterPage() {
        navigate(driver, "https://qtripdynamic-qa-frontend.vercel.app/pages/register/");
    }

    public boolean verifyOnRegistrationPage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(registerNowBtn));
            Assert.assertEquals(url, driver.getCurrentUrl(), "Not on registration page");
            return true;
        } catch (AssertionError | Exception e) {
            return false;
        }
    }

    public boolean enterEmail(String email) {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailInput)).clear();;
            sendKeys(emailInput, email);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean enterPassword(String password) {
        try {
            passwordInput.clear();
            sendKeys(passwordInput, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean enterConfirmPassword(String password) {
        try {
            confirmPassword.clear();
            sendKeys(confirmPassword, password);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickRegisterNowBtn() {
        try {
            click(registerNowBtn, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String registerNewUser(String userName, String password, String confirmPassword,
            Boolean generateRandomuserName) {
        try {
            if (generateRandomuserName) {
                userName = System.currentTimeMillis() + userName;
            }
            enterEmail(userName);
            enterPassword(password);
            enterConfirmPassword(confirmPassword);
            clickRegisterNowBtn();
            return userName;
        } catch (Exception e) {
            return null;
        }
    }
}
