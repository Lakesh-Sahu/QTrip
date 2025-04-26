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

public class AdventuresDetailPage extends SeleniumWrapper {

    WebDriver driver;
    WebDriverWait wait;
    String url;

    @FindBy(id = "myForm")
    WebElement formPanel;

    @FindBy(name = "name")
    WebElement nameInputField;

    @FindBy(name = "date")
    WebElement dateInputField;

    @FindBy(name = "person")
    WebElement personCountField;

    @FindBy(className = "reserve-button")
    WebElement reserveBtn;

    @FindBy(id = "reserved-banner")
    WebElement reservationSuccessBanner;

    public AdventuresDetailPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));

        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(this.driver, 10);
        PageFactory.initElements(ajax, this);
    }

    public boolean verifyOnAdventureDetailsPage() {
        try {
            if (driver.getCurrentUrl().contains("/pages/adventures/detail/?adventure=")) {
                url = driver.getCurrentUrl();
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public boolean sendKeyInNameField(String name) {
        try {
            wait.until(ExpectedConditions.visibilityOf(nameInputField)).clear();
            return sendKeys(nameInputField, name);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendKeyInDateField(String date) {
        try {
            wait.until(ExpectedConditions.visibilityOf(dateInputField)).clear();
            return sendKeys(dateInputField, date);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendKeyInPersonCountField(String personCount) {
        try {
            wait.until(ExpectedConditions.visibilityOf(personCountField)).clear();
            return sendKeys(personCountField, personCount);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickReserveBtn() {
        try {
            return click(wait.until(ExpectedConditions.visibilityOf(reserveBtn)));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean performReservation(String name, String date, String personCount) {
        try {
            return sendKeyInNameField(name) && sendKeyInDateField(date) && sendKeyInPersonCountField(personCount) && clickReserveBtn();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyReservationSuccess() {
        try {
            wait.until(ExpectedConditions.visibilityOf(reservationSuccessBanner));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}