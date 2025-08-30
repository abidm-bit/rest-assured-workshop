package exercisesTestNG;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.createInstance("report.html");
    private static final ThreadLocal<ExtentTest> methodTest = new ThreadLocal<>();
    private static final Logger LOGGER = LogManager.getLogger (Listener.class);

    private void logMessage(final String message){
        LOGGER.info(message);
    }

    private ExtentTest getTest(ITestResult result) {
        return methodTest.get();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        ExtentTest test = extent.createTest(methodName);
        methodTest.set(test);
        logMessage ("Test Execution Started for :" + result.getName ());
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
