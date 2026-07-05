package listeners;

import database.TestResultDao;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ResultListener implements ITestListener {

    private final TestResultDao dao = new TestResultDao();

    @Override
    public void onTestSuccess(ITestResult result) {
        dao.saveResult(result.getMethod().getMethodName(), "PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        dao.saveResult(result.getMethod().getMethodName(), "FAILED");
    }
}
