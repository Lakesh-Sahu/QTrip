package qtrip.tests;

import qtrip.BaseTest;
import qtrip.pages.AdventurePage;
import qtrip.pages.AdventuresDetailPage;
import qtrip.pages.HistoryPage;
import qtrip.pages.HomePage;
import qtrip.pages.LoginPage;
import qtrip.pages.RegisterPage;
import qtrip.utils.ExternalDataProvider;
import qtrip.utils.NavigationBar;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_04 extends BaseTest {

    @Test(enabled = true, priority = 4, groups = {"Reliability Flow"}, dataProvider = "bookingAndHistoryData",
            dataProviderClass = ExternalDataProvider.class)
    public void TestCase04(String userName, String password, String dataset1, String dataset2,
            String dataset3) {
        System.out.println("Verify that booking history can be viewed");
        SoftAssert sa = new SoftAssert(); 

        HomePage hp = new HomePage(driver); 

        sa.assertTrue(hp.navigateToHomePage(), "Unable to navigate to home page");

        sa.assertTrue(hp.verifyOnHomePage(), "Not on home page");

        NavigationBar nb = new NavigationBar(driver);

        sa.assertTrue(nb.clickregisterBtn(), "Unable to click register button");

        RegisterPage rp = new RegisterPage(driver);

        sa.assertTrue(rp.verifyOnRegistrationPage(), "Not on registration page");

        String newUserName = rp.registerNewUser(userName, password, password, true);
        sa.assertNotNull(newUserName, "New user registration failed");

        LoginPage lp = new LoginPage(driver);

        sa.assertTrue(lp.verifyOnLoginPage(), "Not on login page");

        sa.assertTrue(lp.performLogin(newUserName, password), "Unable to perform user login");

        sa.assertTrue(hp.isUserLoggedIn(), "User not logged in");
 

        List<String> dataSets = new ArrayList<>();
        dataSets.add(dataset1);
        dataSets.add(dataset2);
        dataSets.add(dataset3);

        for (int i = 0; i < dataSets.size(); i++) {
            String[] splittedDataset = dataSets.get(i).split(";");

            String city = splittedDataset[0];
            String adventure = splittedDataset[1];
            String name = splittedDataset[2];
            String date = splittedDataset[3];
            String personCount = splittedDataset[4];

            booking(driver, sa, city, adventure, name, date, personCount);
        }

        sa.assertTrue(nb.clickReservationBtn(), "Unable to click reservation button");

        HistoryPage hsp = new HistoryPage(driver);

        sa.assertTrue(hsp.verifyOnReservationPage(), "Not on reservation page");

        for (int i = 0; i < dataSets.size(); i++) {
            String[] splittedDataset = dataSets.get(i).split(";");

            String adventure = splittedDataset[1];
            String name = splittedDataset[2];
            String date = splittedDataset[3];
            String personCount = splittedDataset[4];

            sa.assertTrue(hsp.verifyMatchingReservationPresent(name, adventure, personCount, date),
                    "Matching reservation not found");
        }

        sa.assertTrue(nb.clickLogoutBtn(), "Unable to click logout button");

        sa.assertTrue(nb.verifyUserLoggedOut(), "User not logged out");;

        System.out.println("END TestCase04");

        sa.assertAll();
    }

    public void booking(WebDriver driver, SoftAssert sa, String city, String adventure,
            String guestName, String date, String personCount) {
        try {
            HomePage hp = new HomePage(driver);
            if (!hp.verifyOnHomePage()) {
                hp.navigateToHomePage();
            }  
 
            sa.assertTrue(hp.sendKeysInSearchBar(city), "Unable to search for given city");
            sa.assertTrue(hp.isCityFound(city), "City not found for given keyword");
            sa.assertNotNull(hp.clickOnCity(city));

            AdventurePage ap = new AdventurePage(driver, city);

            sa.assertTrue(ap.verifyOnAdvanturePage(), "Not on adventure page");

            sa.assertTrue(ap.sendKeysInSearchAdventure(adventure),
                    "Unable to search for given adventure");

            sa.assertTrue(ap.clickOnAdventure(adventure), "Unable to click on adventure");

            AdventuresDetailPage adp = new AdventuresDetailPage(driver);

            sa.assertTrue(adp.verifyOnAdventureDetailsPage(), "Not on adventure detail page");

            sa.assertTrue(adp.performReservation(guestName, date, personCount),
                    "Unable to perform reservation");

            sa.assertTrue(adp.verifyReservationSuccess(), "Reservation not successful");
        } catch (Exception e) {
        }
    }
}
