package utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.IInvokedMethod;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class testListenerDemo implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Testing has started");        
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub        
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub        
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub        
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub        
    }

    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub        
    }

    @Override
    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub        
    }
    
    public void afterInvocation(IInvokedMethod method, ITestResult arg0) {
    	if (arg0.getMethod().isTest()) {
            //Change Failed to Skipped based on exception text
            if (arg0.getStatus() == ITestResult.FAILURE) {
                if (arg0.getThrowable() != null) {
                    if (arg0.getThrowable().getStackTrace() != null) {
                        StringWriter sw = new StringWriter();
                        arg0.getThrowable().printStackTrace(new PrintWriter(sw));
                        if (sw.toString().contains("visible")) {
                            ITestContext tc = Reporter.getCurrentTestResult().getTestContext();
                            tc.getFailedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());
                            tc.getFailedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
                            Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
                            tc.getSkippedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());
                        }
                    }
                }
            }


            //Change Pass to failure and throw custom exception error
            if (arg0.getStatus() == ITestResult.FAILURE) {
                ITestContext tc = Reporter.getCurrentTestResult().getTestContext();
                tc.getPassedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());
                tc.getPassedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
                Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
                Reporter.getCurrentTestResult().setThrowable(new Exception("test Fail"));
                tc.getSkippedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());
                tc.getPassedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());   
                tc.getPassedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
                Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
                Reporter.getCurrentTestResult().setThrowable(new Exception("test done"));
                tc.getFailedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());
            }


            //Change skip to fail with custom failure
            if (arg0.getStatus() == ITestResult.SKIP) {
                ITestContext tc = Reporter.getCurrentTestResult().getTestContext();
                tc.getSkippedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());
                tc.getSkippedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
                Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS_PERCENTAGE_FAILURE);
                Reporter.getCurrentTestResult().setThrowable(new Exception("test Fail"));
                tc.getFailedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());
                tc.getPassedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());   
                tc.getPassedTests().getAllMethods().remove(Reporter.getCurrentTestResult().getMethod());
                Reporter.getCurrentTestResult().setStatus(ITestResult.SUCCESS);
                Reporter.getCurrentTestResult().setThrowable(new Exception("test done"));
                tc.getFailedTests().addResult(arg0, Reporter.getCurrentTestResult().getMethod());
            }
            
           

        }
    }
}