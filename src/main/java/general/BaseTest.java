package general;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.venturedive.base.database.connection.SonarDB;
import com.venturedive.base.exception.APIException;
import com.venturedive.base.utility.JIRA;

import static config.ConfigProperties.*;

import com.venturedive.base.utility.MessagesIntegration;
import com.venturedive.base.utility.SendEmailAfterExecution;
import com.venturedive.base.utility.TestRail;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import javax.mail.MessagingException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class BaseTest  {

    ExtentTest logger;

    static Date startTime = null;
    static Date endTime = null;
    static Integer passedCount = 0;
    static Integer failedCount = 0;
    static Integer skippedCount = 0;

    SonarDB dbconn= new SonarDB();
    static ArrayList<String> automationSteps;
    static Integer beforeAddingStepsLength;
    static Integer afterAddingStepsLength;
    static Integer beforeAddingExpectedResultLength;
    static Integer afterAddingExpectedResultLength;
    static ArrayList<String> expectedResults;
    static ArrayList<File> screenShotCollection = new ArrayList<File>();

    @BeforeSuite
    public void beforesuite(ITestContext ctx) throws SQLException, IOException, AWTException, APIException {
        startReport();
        if(IsEnableRecording.equals("true"))
            Recorder.deleteRecordings();
        WebDriverManager.chromedriver().setup();
    }

    @BeforeTest
    @Parameters("Browser")
    public void beforeTest(@Optional("chrome") String browser, ITestContext ctx) throws Exception {
        // Instead of system property, we use the method from WebDriverFactory
        WebDriverFactory.setBrowser(browser);
        MainCall.webDriverFactory.getInstance();
        automationSteps = new ArrayList<>();
        expectedResults = new ArrayList<>();
    }

    public void startReport() {
        if (IsEnableReporting.equals("true")) {
            MainCall.startReport();
        }
        startTime = getTime();
    }

    @BeforeMethod
    public void beforeTestMethod(Method method) throws Exception {
        beforeAddingStepsLength=automationSteps.size();
        beforeAddingExpectedResultLength=expectedResults.size();
        System.out.println("before");

        if(IsEnableRecording.equals("true"))
            Recorder.startRecording(method.getName());

        if(IsEnableReporting.equals("true")) {
            logger = MainCall.getExtentReport().startTest(method.getName(), "");
            logger.setStartedTime(getTime());
        }
    }

    @AfterMethod
    public void QuitDriver(ITestResult result, ITestContext ctx, Method method) throws Exception {
        afterAddingStepsLength=automationSteps.size();
        afterAddingExpectedResultLength=expectedResults.size();
        if(IsEnableReporting.equals("true")) {
//            if (result.getStatus() == ITestResult.FAILURE) {
//                failedCount++;
//                logger.log(LogStatus.FAIL, "Test Case Failed reason is: " + result.getThrowable());
//                logger.log(LogStatus.INFO, "StackTrace Result: " + Arrays.toString(result.getThrowable().getStackTrace()));
//                logger.log(LogStatus.FAIL, logger.addScreenCapture(Screenshots.takeScreenshot(result.getMethod().getMethodName())));
//                screenShotCollection.add(Screenshots.screenShot);
//                if (LogTestRail.equals("true")) {
//                    JIRA.CreateJiraWithScreenShot(result, Screenshots.screenShot, beforeAddingStepsLength, afterAddingStepsLength, automationSteps);
//                    JIRA.PostMobileIssuesJira();
//                }
//            } else if (result.getStatus() == ITestResult.SKIP) {
//                skippedCount++;
//                logger.log(LogStatus.SKIP, "Test Case Skipped is: " + result.getName());
//            } else {
//                passedCount++;
//                logger.log(LogStatus.PASS, result.getMethod().getMethodName() + " is Passed");
//            }

            logger.setEndedTime(getTime());
            MainCall.getExtentReport().endTest(logger);
        }

        //if(IsEnableRecording.equals("true"))
            //Recorder.stopRecording();
        //TestRail.getCaseIdandResultmobile(result,ctx,method,beforeAddingStepsLength,afterAddingStepsLength,automationSteps,beforeAddingExpectedResultLength,afterAddingExpectedResultLength,expectedResults,null);
    }

    @AfterTest
    public void afterTest() {
        MainCall.webDriverFactory.finishDriver();
    }

    private Date getTime(){
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    @AfterSuite
    public void tearDown() throws SQLException, APIException, IOException, MessagingException {
        endTime = getTime();
        if(IsEnableReporting.equals("true")) {
            MainCall.getExtentReport().flush();
            MainCall.getExtentReport().close();
        }
    }

}
