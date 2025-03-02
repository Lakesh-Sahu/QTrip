package qtrip.utils;

import qtrip.DP;
import java.io.IOException;
import org.testng.annotations.DataProvider;

public class ExternalDataProvider {
    
    @DataProvider(name = "userOnboardData")
    public Object[][] userOnboardData() throws IOException {
        DP dataProvider = new DP();
        return dataProvider.dpMethod("TestCase01");
    }

    @DataProvider(name = "advanturePageData")
    public Object[] advanturePageData() throws IOException {
        DP dataProvider = new DP();
        return dataProvider.dpMethod("TestCase02");
    }

    @DataProvider(name = "advantureBookingData")
    public Object[] advantureBookingData() throws IOException {
        DP dataProvider = new DP();
        return dataProvider.dpMethod("TestCase03");
    }

    @DataProvider(name = "bookingAndHistoryData")
    public Object[] bookingAndHistoryData() throws IOException {
        DP dataProvider = new DP();
        return dataProvider.dpMethod("TestCase04");  
    }
} 