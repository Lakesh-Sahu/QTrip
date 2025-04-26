package qtrip.tests;

import qtrip.BaseTest;
import qtrip.pages.HomePage;
import qtrip.pages.LoginPage;
import qtrip.pages.RegisterPage;
import qtrip.utils.ExternalDataProvider;
import qtrip.utils.NavigationBar;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class testCase_01 extends BaseTest{ 
 
    @Test(enabled = true, priority = 1, groups = {"Login Flow"}, dataProvider = "userOnboardData",
            dataProviderClass = ExternalDataProvider.class)
    public void TestCase01(String userName, String password){
        
        System.out.println("Verify user registration-login-logout");
        boolean status;

        SoftAssert sa = new SoftAssert();

        HomePage hp = new HomePage(driver);
        sa.assertTrue(hp.navigateToHomePage(), "Unable to navigate to home page");

        NavigationBar nb = new NavigationBar(driver);
        status = nb.clickRegisterBtn();

        sa.assertTrue(status, "Click on registration button Failed");

        RegisterPage rp = new RegisterPage(driver);
        status = rp.verifyOnRegistrationPage();
        sa.assertTrue(status, "Not on registration page");

        String newUserName = rp.registerNewUser(userName, password, password, true);
        sa.assertNotNull(newUserName, "New generated username is null");

        LoginPage lp = new LoginPage(driver);
        sa.assertTrue(lp.verifyOnLoginPage(), "After registration not on login page");

        sa.assertTrue(lp.performLogin(newUserName, password), "Performing Login Failed");

        sa.assertTrue(hp.isUserLoggedIn(), "Login verification Failed");

        sa.assertTrue(nb.clickLogoutBtn(), "Logout button click Failed");

        sa.assertTrue(nb.verifyUserLoggedOut(), "User not logged out");

        System.out.println("END TestCase01");

        sa.assertAll();
    }
}