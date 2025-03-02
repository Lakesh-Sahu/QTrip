package qtrip.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {

    public static String capture(WebDriver driver) throws IOException {
        String path = System.getProperty("user.dir") + File.separator + "screenshot" + File.separator + System.currentTimeMillis() + ".png";

        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(path);
        FileUtils.copyFile(source, dest);

        return dest.getAbsolutePath();
    }
}
