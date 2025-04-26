package qtrip;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

public class SeleniumWrapper {
    public static boolean click(WebElement element) {
        try {
            element.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean sendKeys(WebElement element, String keysToEnter) {
        try {
            element.clear();
            element.sendKeys(keysToEnter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean navigate(WebDriver driver, String url) {
        try {
            driver.get(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static WebElement findElementWithRetry(WebDriver driver, By element, int times) {
        for (int i = 1; i <= times; i++) {
            try {
                return driver.findElement(element);
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}