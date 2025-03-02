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

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_03 extends BaseTest {

        @Test(enabled = true, priority = 3, groups = {
                        "Booking and Cancellation Flow" }, dataProvider = "advantureBookingData", dataProviderClass = ExternalDataProvider.class)
        public void TestCase03(String userName, String password, String cityName, String adventure,
                        String guestName, String date, String guestCount) {

                String newUserName;
                System.out.println("Verify that adventure booking and cancellation works fine");

                SoftAssert sa = new SoftAssert();

                HomePage hp = new HomePage(driver);
                sa.assertTrue(hp.navigateToHomePage(), "Unable to navigate to homepage");
                sa.assertTrue(hp.verifyOnHomePage(), "Not on homepage");

                NavigationBar nb = new NavigationBar(driver);
                sa.assertTrue(nb.clickregisterBtn(),
                                "Unable to click registration button of navigation bar");

                RegisterPage rp = new RegisterPage(driver);
                sa.assertTrue(rp.verifyOnRegistrationPage(), "Not on registration page");

                newUserName = rp.registerNewUser(userName, password, password, true);
                sa.assertNotNull(newUserName, "newUserName is null");

                sa.assertTrue(nb.clickLoginBtn(), "Unable to click login button");

                LoginPage lp = new LoginPage(driver);
                sa.assertTrue(lp.verifyOnLoginPage(), "Not on login page");

                sa.assertTrue(lp.performLogin(newUserName, password), "Unable to perform login");
                sa.assertTrue(hp.isUserLoggedIn(), "User is not logged in");

                sa.assertTrue(hp.sendKeysInSearchBar(cityName), "Unable to search city");

                sa.assertTrue(hp.isCityFound(cityName), "City not found");

                sa.assertNotNull(hp.clickOnCity(cityName), "Unable to click on city name");

                AdventurePage ap = new AdventurePage(driver, cityName);
                sa.assertTrue(ap.sendKeysInSearchAdventure(adventure), "Unable to search adventure");

                sa.assertTrue(ap.matchAdventureNameInCard(adventure), "Given adventure name not found");

                sa.assertTrue(ap.clickOnAdventure(adventure));

                AdventuresDetailPage adp = new AdventuresDetailPage(driver);
                sa.assertTrue(adp.verifyOnAdventureDetailsPage(), "Not on Adventure Details Page");

                sa.assertTrue(adp.sendKeyInNameField(guestName), "Unable to enter name for reservation");
                sa.assertTrue(adp.sendKeyInDateField(date), "Unable to enter date for reservation");
                sa.assertTrue(adp.sendKeyInPersonCountField(guestCount),
                                "Unable to enter person count for reservation");
                sa.assertTrue(adp.clickReserveBtn(), "Unable to click reservation button");

                sa.assertTrue(adp.verifyReservationSuccess(),
                                "Reservation successful banner not found in adventure detail page");

                sa.assertTrue(nb.clickReservationBtn(), "Unable to click reservation/history button");

                HistoryPage hsp = new HistoryPage(driver);
                sa.assertTrue(hsp.verifyOnReservationPage(), "Not on reservation page");

                sa.assertTrue(hsp.verifyReservationPresent(), "No reservation present");

                sa.assertTrue(hsp.verifyMatchingReservationPresent(guestName, adventure, guestCount, date),
                                "Matching reservation not found");

                String transactionId = hsp.getTransactionId(guestName, adventure, guestCount, date);
                sa.assertNotNull(transactionId, "Transaction ID is null");

                sa.assertTrue(hsp.cancelReservationByTransactionId(transactionId), "Reservation cancellation Failed");

                driver.navigate().refresh();

                sa.assertFalse(hsp.verifyPresenceOfReservationByTransactionId(transactionId),
                                "Reservation exist even after cancellation");

                sa.assertTrue(nb.clickLogoutBtn(), "Unable to click logout button");

                sa.assertTrue(nb.verifyUserLoggedOut(), "User not logged out");;

                System.out.println("END TestCase03");

                sa.assertAll();
        }
}
