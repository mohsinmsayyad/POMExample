package testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.utils.FileUtil;
import library.SelectBrowser;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.AppleLoginPage;
import pages.AppleMainPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class AppleTest {
    WebDriver driver;
    AppleMainPage appleMainPage;
    AppleLoginPage appleLoginPage;

    @BeforeSuite
    public void reportsetUp()
    {
        //create the HtmlReporter in that path by the name of  MyOwnReport.html
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/MyOwnReport.html");
        ExtentReports extent;
        extent = new ExtentReports();

        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "DESKTOP-DJ642A6");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Mo Sayyad");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("AutomationTestingApple download pictures Report");
        htmlReporter.config().setReportName("Google Search and Dowload Pictures Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.DARK);
    }

    @BeforeTest
    public void setUpBrowser() {
        driver = SelectBrowser.StartBrowser("Chrome");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://apple.com/");
    }

    @Test(priority = 1)
    public void click_on_bag_test() throws IOException {
        ExtentReports extent = null;
        ExtentTest test = extent.createTest("click_on_bag_test", "Pass");
        test.addScreenCaptureFromPath("test-output/Screenshots/main_page.png");
        appleMainPage = new AppleMainPage(driver);
        appleMainPage.clickOnShoppingBag();
        appleMainPage.clickOnSignInLink();
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("test-output/Screenshots/main_page.png"));
    }
    @Test(priority = 2)
    public void login_into_account_with_wrong_password_test()
    {
        appleLoginPage = new AppleLoginPage(driver);
        appleLoginPage.switchFrames();
        appleLoginPage.inputLogin("i.adulian@gmail.com");
        appleLoginPage.clickButtonForLoginAndSignIn();
        appleLoginPage.inputPassword("edefrfendenjdb");
        appleLoginPage.clickButtonForLoginAndSignIn();

        String expected = "Your Apple ID or password was incorrect.";
        Assert.assertEquals(expected, appleLoginPage.getErrorMessage());
    }
}