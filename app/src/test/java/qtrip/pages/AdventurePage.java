package qtrip.pages;

import static qtrip.SeleniumWrapper.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdventurePage {

    WebDriver driver;
    WebDriverWait wait;
    String url;

    @FindBy(className = "filter-bar-tile")
    WebElement filter;

    @FindBy(id = "duration-select")
    WebElement durationFilter;

    @FindBy(css = "div[onclick='clearDuration(event)']")
    WebElement clearDurationBtn;

    @FindBy(id = "category-select")
    WebElement selectCategoryFilter;

    @FindBy(css = "div[onclick='clearCategory(event)']")
    WebElement clearCategoryBtn;

    @FindBy(id = "search-adventures")
    WebElement searchAdventure;

    @FindBy(css = "div[onclick='resetAdventuresData()']")
    WebElement clearAdventureBtn;

    @FindBy(className = "activity-card")
    List<WebElement> cards;

    @FindBy(xpath = "//div[@class='activity-card']//h5[text()='Duration']/following-sibling::p")
    List<WebElement> cardsDuration;

    @FindBy(className = "category-banner")
    List<WebElement> categoryBanner;

    @FindBy(xpath = "//div/div[1]/h5[@class='text-left']")
    List<WebElement> adventureName; 

    public AdventurePage(WebDriver driver, String cityName) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        url = "https://qtripdynamic-qa-frontend.vercel.app/pages/adventures/?city="
                + cityName.toLowerCase() + "/";

        AjaxElementLocatorFactory ajax = new AjaxElementLocatorFactory(this.driver, 10);
        PageFactory.initElements(ajax, this);
    }

    public boolean verifyOnAdvanturePage() {
        try {
            driver.getCurrentUrl().equals(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cardSize(int expectedCardItems) {
        try {
            Thread.sleep(2000);
            if (cards.size() == expectedCardItems) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean selectDurationByVisibleText(String durationText) {
        // Visible Text 0-2 Hours, 2-6 Hours, 6-12 Hours, 12+ Hours
        try {
            Select select = new Select(durationFilter);
            select.selectByVisibleText(durationText);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean selectDurationByIndex(int index) {
        // Availabe index 1,2,3,4
        try {
            Select select = new Select(durationFilter);
            select.selectByIndex(index);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean selectDurationByValue(String value) {
        // values 0-2, 2-6, 6-12, 12-99
        try {
            Select select = new Select(durationFilter);
            select.selectByValue(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyCardsDuration(int start, int end) {

        try {
            Thread.sleep(1000);
            List<WebElement> cd = cardsDuration;

            Iterator<WebElement> it = cd.iterator();
            
            while (it.hasNext()) {
                StringBuilder sb = new StringBuilder();
                String elementText = it.next().getText();

                for (Character ch : elementText.toCharArray()) {
                    if (Character.isDigit(ch)) {
                        sb.append(ch);
                    }
                }
                if (Integer.valueOf(sb.toString()) < start || Integer.valueOf(sb.toString()) > end) {
                    return false;
                }
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean clickDurationClearBtn() {
        try {
            click(wait.until(ExpectedConditions.visibilityOf(clearDurationBtn)), driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean selectCategoryByVisibleText(String categoryText) {
        try {
            Select select = new Select(selectCategoryFilter);
            select.selectByVisibleText(categoryText);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean selectCategoryByIndex(int index) {
        try {
            Select select = new Select(selectCategoryFilter);
            select.selectByIndex(index);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean selectCategoryByValue(String value) {
        try {
            Select select = new Select(selectCategoryFilter);
            select.selectByValue(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyCategory(String category) {
        try {
            Thread.sleep(1000);
            for(WebElement element : wait.until(ExpectedConditions.visibilityOfAllElements(categoryBanner))) {
                if(!category.toLowerCase().contains(element.getText().toLowerCase())) {
                    return false;
                }
            }
        } catch (Exception e) {
        }
        return true;
    }

    public boolean clickCategoryClearBtn() {
        try {
            click(wait.until(ExpectedConditions.visibilityOf(clearCategoryBtn)), driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean sendKeysInSearchAdventure(String adventure) {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchAdventure)).clear();
            sendKeys(searchAdventure, adventure);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getAdventuresName() {
        List<String> names = new ArrayList<>();
        try {
            Thread.sleep(3000);
            for (WebElement webElement : adventureName) {
                names.add(webElement.getText());
            }
        } catch (Exception e) {
        }
        return names;
    }

    public boolean matchAdventureNameInCard(String adventureName) {
        try {
            for(String name : getAdventuresName()) {
                if(name.toLowerCase().contains(adventureName.toLowerCase())) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean clickOnAdventure(String adventureNameToClick) {
        try {
            Thread.sleep(3000);
            for (WebElement webElement : adventureName) {
                if(webElement.getText().toLowerCase().contains(adventureNameToClick.toLowerCase())) {
                    click(webElement, driver);
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }
}
