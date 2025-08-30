package exercisesTestNG;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

// A singleton class for generating a test report

public class ExtentManager {

// Private instance
private static ExtentReports extent;

// Create an instance
public static ExtentReports createInstance(String file){
    ExtentSparkReporter htmlReporter = new ExtentSparkReporter(file);
    extent = new ExtentReports();
    extent.attachReporter(htmlReporter);
    return extent;
}

}
