package qtrip.tests;

import qtrip.BaseTest;
import qtrip.pages.AdventurePage;
import qtrip.pages.HomePage;
import qtrip.utils.ExternalDataProvider;
import qtrip.utils.Helper;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_02 extends BaseTest {

    @Test(enabled = true, priority = 2, groups = {"Search and Filter flow"}, dataProvider = "advanturePageData", dataProviderClass = ExternalDataProvider.class)
    public void TestCase02(String validCityName, String category, String duration, String expectedFilterResult, String expectedWithoutFilterResult) {
        System.out.println("Verify that Search and filter works fine");
        String invalidCityName = "notpresent";
        int expectedCardSizeWithFilter = Integer.valueOf(expectedFilterResult);
        int expectedCardSizeWithoutFilter = Integer.valueOf(expectedWithoutFilterResult);

        SoftAssert sa = new SoftAssert();

        HomePage hp = new HomePage(driver);
        Helper hl = new Helper();

        sa.assertTrue(hp.navigateToHomePage(), "Unable to navigate to homepage");
        sa.assertTrue(hp.verifyOnHomePage(), "Not on homepage");

        sa.assertTrue(hp.sendKeysInSearchBar(invalidCityName),
                "Unable to send keys in the search bar");

        sa.assertTrue(hp.isNoCityFound(), "City found for invalid keyword");

        sa.assertTrue(hp.sendKeysInSearchBar(validCityName), "No city found for valid keyword");

        sa.assertTrue(hp.isCityFound(validCityName), "No city found for valid keyword");

        String cityName = hp.clickOnCity(validCityName);
        sa.assertNotNull(cityName, "Click on valid city name failed");

        AdventurePage ap = new AdventurePage(driver, cityName);

        sa.assertTrue(ap.verifyOnAdvanturePage(), "Not on advanture page");

        sa.assertTrue(ap.selectDurationByVisibleText(duration),
                "Unable to select given duration");

        int[] startEndDuration = hl.durationSeparator(duration);

        sa.assertTrue(ap.verifyCardsDuration(startEndDuration[0], startEndDuration[1]),
                "Duration of card does not match with selected filter duration");

        sa.assertTrue(ap.selectCategoryByVisibleText(category), "Unable to select given category");

        sa.assertTrue(ap.verifyCategory(category), "Category of card are not found as applied category filter");

        sa.assertTrue(ap.cardSize(expectedCardSizeWithFilter), "Card size is not as expected with filter");

        sa.assertTrue(ap.clickDurationClearBtn(), "Unable to click duration clear button"); 
        sa.assertTrue(ap.clickCategoryClearBtn(), "Unable to click category clear button");

        sa.assertTrue(ap.cardSize(expectedCardSizeWithoutFilter), "Card size is not as expected without filter");
       
        System.out.println("END TestCase02");

        sa.assertAll();
    }
}
