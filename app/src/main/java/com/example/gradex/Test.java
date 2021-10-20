package com.example.gradex;

public class Test {
    private String testName;
    private String testClass;
    private String testAddDate;
    private String testIconURL;
    private boolean saved;

    public Test(String testName, String testClass, String testAddDate, String testIconURL) {
        this.testName = testName;
        this.testClass = testClass;
        this.testAddDate = testAddDate;
        this.testIconURL = testIconURL;
        this.saved = false;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestClass() {
        return testClass;
    }

    public void setTestClass(String testClass) {
        this.testClass = testClass;
    }

    public String getTestAddDate() {
        return testAddDate;
    }

    public void setTestAddDate(String testAddDate) {
        this.testAddDate = testAddDate;
    }

    public String getTestIconURL() {
        return testIconURL;
    }

    public void setTestIconURL(String testIconURL) {
        this.testIconURL = testIconURL;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }
}
