package exercisesTestNG;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.google.common.base.Strings.repeat;

public class Listener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.createInstance("report.html");
    private static final ThreadLocal<ExtentTest> methodTest = new ThreadLocal<>();
    private static final Logger LOGGER = LogManager.getLogger (Listener.class);

    private void logMessage(final String message){
        LOGGER.info (repeat ("=", 75));
        LOGGER.info(message);
        LOGGER.info (repeat ("=", 75));
    }

    private ExtentTest getTest(ITestResult result) {
        return methodTest.get();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String suiteName = result.getTestContext().getSuite().getName();
        String testName = result.getTestContext().getName();
        String className = result.getMethod().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        String testReportName = String.format("%s - %s.%s", testName, className, methodName);
//        ExtentTest test = extent.createTest(methodName);
        ExtentTest test = extent.createTest(testReportName);
        methodTest.set(test);
//        logMessage ("Test Execution Started for :" + result.getName ());
        logMessage ("Test Execution Started for: " + testReportName);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        getTest(result).pass("Test passed");
        logMessage ( result.getName () + " Passed Successfully " );
    }

    @Override
    public synchronized void onTestFailure (ITestResult result) {
        getTest(result)
        .fail("Test failed due to: " + result.getThrowable().getMessage());
        logMessage (result.getName () + " Test failed!!!" );
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        logMessage ("Test execution completed for all tests!!" + context.getSuite ()
                .getAllMethods ());
        extent.flush();
    }
}
