package qtrip.pages;

import static qtrip.SeleniumWrapper.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HistoryPage {
    WebDriver driver;
    WebDriverWait wait;
    String url =
            "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/reservations/index.html";

    @FindBy(id = "reservation-table-parent")
    WebElement reservationLogo;

    @FindBy(xpath = "//table[@class='table']/thead/tr/th")
    List<WebElement> tableHeader;

    @FindBy(id = "reservation-table")
    WebElement reservationTableBody;

    @FindBy(xpath = "//tbody[@id='reservation-table']/tr/th")
    WebElement transactionIds;

    @FindBy(xpath = "//tbody[@id= 'reservation-table']/tr")
    List<WebElement> reservationList;

    public HistoryPage(WebDriver driver) {
        this.driver = driver;

        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));

        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(this.driver, 10);
        PageFactory.initElements(ajax, this);
    }

    public boolean verifyOnReservationPage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(reservationLogo));
            if (driver.getCurrentUrl().contains("/pages/adventures/reservations/")) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean verifyReservationPresent() {
        try {
            Thread.sleep(3000);
            wait.until(ExpectedConditions.visibilityOf(
                findElementWithRetry(driver, By.xpath("//tbody[@id='reservation-table']/tr[1]"), 3)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public HashMap<String, Integer> getHeaderNameAndColn() {

        HashMap<String, Integer> headerList = new HashMap<>();

        try {
            List<WebElement> allHeading =
                    wait.until(ExpectedConditions.visibilityOfAllElements(tableHeader));
            int colnSize = allHeading.size();

            for (int i = 1; i <= colnSize; i++) {
                WebElement singleHeading = wait.until(ExpectedConditions.visibilityOf(findElementWithRetry(driver, By.xpath("//table[@class='table']/thead/tr/th[" + i + "]"), 3)));
                switch (singleHeading.getText()) {
                    case "Transaction ID":
                        headerList.put("Transaction ID", i);
                        break;
                    case "Booking Name":
                        headerList.put("Booking Name", i);
                        break;
                    case "Adventure":
                        headerList.put("Adventure", i);
                        break;
                    case "Person(s)":
                        headerList.put("Person(s)", i);
                        break;
                    case "Date":
                        headerList.put("Date", i);
                        break;
                    case "Price":
                        headerList.put("Price", i);
                        break;
                    case "Booking Time":
                        headerList.put("Booking Time", i);
                        break;
                    case "Action":
                        headerList.put("Action", i);
                        break;
                    case "Cancel":
                        headerList.put("Cancel", i);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
        }
        return headerList;
    }

    public Boolean verifyMatchingReservationPresent(String name, String adventureName,
            String personCount, String date) {
        try {
            HashMap<String, Integer> headerList = getHeaderNameAndColn();

            int nameColn = headerList.get("Booking Name");
            int adventureColn = headerList.get("Adventure");
            int personCountColn = headerList.get("Person(s)");
            // int dateColn = headerList.get("Date");
            // int transactionIdColn = headerList.get("Transaction ID");
            // int priceColn = headerList.get("Price");
            // int bookingTimeColn = headerList.get("Booking Time");
            // int actionColn = headerList.get("Action");
            // int cancelBtnColn = headerList.get("Cancel");

            int rowSize = getReservationList().size();

            for (int rowNum = 1; rowNum <= rowSize; rowNum++) {

                String nameText = getNameElement(rowNum, nameColn).getText();
                String adventureText = getAdventureElement(rowNum, adventureColn).getText();
                String personCountText = getPersonCountElement(rowNum, personCountColn).getText();

                if (containsMatching(nameText, name)
                        && containsMatching(adventureText, adventureName)
                        && containsMatching(personCountText, personCount)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public String getTransactionId(String name, String adventureName, String personCount,
            String date) {
        try {
            HashMap<String, Integer> headerList = getHeaderNameAndColn();

            int transactionIdColn = headerList.get("Transaction ID");
            int nameColn = headerList.get("Booking Name");
            int adventureColn = headerList.get("Adventure");
            int personCountColn = headerList.get("Person(s)");
            // int dateColn = headerList.get("Date");

            int rowSize = getReservationList().size();

            for (int rowNum = 1; rowNum <= rowSize; rowNum++) {

                String nameText = getNameElement(rowNum, nameColn).getText();
                String adventureText = getAdventureElement(rowNum, adventureColn).getText();
                String personCountText = getPersonCountElement(rowNum, personCountColn).getText();

                if (containsMatching(nameText, name)
                        && containsMatching(adventureText, adventureName)
                        && containsMatching(personCountText, personCount)) {
                    return getTransactionIdElement(rowNum, transactionIdColn).getText();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Boolean verifyPresenceOfReservationByTransactionId(String transactionId) {
        try {
            HashMap<String, Integer> headerList = getHeaderNameAndColn();

            int transactionIdColn = headerList.get("Transaction ID");

            int rowSize = getReservationList().size();

            for (int rowNum = 1; rowNum <= rowSize; rowNum++) {
                String transactionIdText =
                        getTransactionIdElement(rowNum, transactionIdColn).getText();

                if (transactionIdText.equalsIgnoreCase(transactionId)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean cancelReservationByTransactionId(String transactionId) {
        try {
            HashMap<String, Integer> headerList = getHeaderNameAndColn();

            int transactionIdColn = headerList.get("Transaction ID");
            int cancelBtnColn = headerList.get("Cancel");

            int rowSize = getReservationList().size();

            for (int rowNum = 1; rowNum <= rowSize; rowNum++) {

                String transactionIdText =
                        getTransactionIdElement(rowNum, transactionIdColn).getText();

                if (transactionIdText.equalsIgnoreCase(transactionId)) {
                    return clickCancelReservationBtn(rowNum, cancelBtnColn);
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean cancelReservationByDetails(String name, String adventureName, String personCount,
            String date) {
        try {
            HashMap<String, Integer> headerList = getHeaderNameAndColn();

            int nameColn = headerList.get("Booking Name");
            int adventureColn = headerList.get("Adventure");
            int personCountColn = headerList.get("Person(s)");
            // int dateColn = headerList.get("Date");
            int cancelBtnColn = headerList.get("Cancel");

            int rowSize = getReservationList().size();

            for (int rowNum = 1; rowNum <= rowSize; rowNum++) {

                String nameText = getNameElement(rowNum, nameColn).getText();
                String adventureText = getAdventureElement(rowNum, adventureColn).getText();
                String personCountText = getPersonCountElement(rowNum, personCountColn).getText();

                if (containsMatching(nameText, name)
                        && containsMatching(adventureText, adventureName)
                        && containsMatching(personCountText, personCount)) {
                    return clickCancelReservationBtn(rowNum, cancelBtnColn);
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public List<WebElement> getReservationList() {
        try {
            return wait.until(ExpectedConditions.visibilityOfAllElements(reservationList));
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }

    public WebElement getTransactionIdElement(int rowNumber, int transactionIdColn) {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//tbody[@id= 'reservation-table']/tr["
                            + rowNumber + "]/*[" + transactionIdColn + "]")));
        } catch (Exception e) {
        }
        return null;
    }

    public WebElement getNameElement(int rowNumber, int nameColn) {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//tbody[@id= 'reservation-table']/tr["
                            + rowNumber + "]/*[" + nameColn + "]")));
        } catch (Exception e) {
        }
        return null;
    }

    public WebElement getAdventureElement(int rowNumber, int adventureColn) {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//tbody[@id= 'reservation-table']/tr["
                            + rowNumber + "]/*[" + adventureColn + "]")));
        } catch (Exception e) {
        }
        return null;
    }

    public WebElement getPersonCountElement(int rowNumber, int personCountColn) {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//tbody[@id= 'reservation-table']/tr["
                            + rowNumber + "]/*[" + personCountColn + "]")));
        } catch (Exception e) {
        }
        return null;
    }

    public WebElement getDateElement(int rowNumber, int dateColn) {
        try {
            return wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//tbody[@id= 'reservation-table']/tr["
                            + rowNumber + "]/*[" + dateColn + "]")));
        } catch (Exception e) {
        }
        return null;
    }

    public boolean clickCancelReservationBtn(int rowNumber, int cancelBtnColn) {
        try {
            click(wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//tbody[@id='reservation-table']/tr["
                            + rowNumber + "]/*[" + cancelBtnColn + "]//button[text()='Cancel']"))), driver);
            Thread.sleep(8000);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean containsMatching(String s1, String s2) {
        try {
            return (s1.toLowerCase().contains(s2.toLowerCase())
                    || s2.toLowerCase().contains(s1.toLowerCase()));
        } catch (Exception e) {
            return false;
        }
    }
}