package qtrip.pages;

import qtrip.SeleniumWrapper;
import qtrip.utils.NavigationBar;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends SeleniumWrapper {
    WebDriver driver;
    WebDriverWait wait;
    String url = "https://qtripdynamic-qa-frontend.vercel.app/";
    NavigationBar nb;

    @FindBy(id = "autocomplete")
    WebElement searchBar;

    @FindBy(xpath = "//h5[text()='No City found']")
    WebElement noCityFound;

    @FindBy(xpath = "//ul//a//li")
    List<WebElement> cityFound;

    @FindBy(className = "tile")
    WebElement firstPageCards;

    public HomePage(WebDriver driver) {
        this.driver = driver;

        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        nb = new NavigationBar(this.driver);

        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(this.driver, 10);
        PageFactory.initElements(ajax, this);
    }

    public boolean navigateToHomePage() {
        try {
            return navigate(driver, url);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyOnHomePage() {
        try {
            if (driver.getCurrentUrl().equals(url)) {
                wait.until(ExpectedConditions.visibilityOf(searchBar));
                wait.until(ExpectedConditions.visibilityOf(firstPageCards));
                return true;
            }
        } catch (Exception ignored) {
        }
        return false;
    }


    public boolean isUserLoggedIn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(nb.logoutBtn));
            return true;
        } catch (Exception e) {
        return false;
        }
    }

    public boolean sendKeysInSearchBar(String keyword) {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchBar)).clear();
            searchBar.sendKeys(keyword.substring(0, 1));
            Thread.sleep(500);
            searchBar.sendKeys(keyword.substring(1));
            Thread.sleep(1500);

            if(!isCityFound(keyword)) {
                searchBar.sendKeys(" ");
            }
            return true;
        } catch (Exception e) {
        return false;
        }
    }

    public boolean isNoCityFound() {
        try {
            wait.until(ExpectedConditions.visibilityOf(noCityFound));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCityFound(String cityName) {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(cityFound));
            for (WebElement webElement : cityFound) {
                if (webElement.getText().toLowerCase().contains(cityName.toLowerCase())) {
                    return true;
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }

    public String clickOnCity(String cityName) {
        try {
            for (WebElement element : cityFound) {
                String elementText = element.getText();
                if (elementText.toLowerCase().contains(cityName.toLowerCase())) {
                    click(element);
                    return elementText;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
