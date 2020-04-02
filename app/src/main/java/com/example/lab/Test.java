package com.example.lab;

public class Test {

    String TestCode;
    String Name;
    String Details;
    String PreTestInformation;
    String ReportAvailability;
    String TestUsuage;

    public void setTestCode(String testCode) {
        TestCode = testCode;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public void setPreTestInformation(String preTestInformation) {
        PreTestInformation = preTestInformation;
    }

    public void setReportAvailability(String reportAvailability) {
        ReportAvailability = reportAvailability;
    }

    public void setTestUsuage(String testUsuage) {
        TestUsuage = testUsuage;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setPrice(String price) {
        Price = price;
    }

    String Category;
    String Price;

    public Test() {
    }

    public Test(String testCode, String name, String details, String preTestInformation, String reportAvailability, String testUsuage, String category, String price) {
        TestCode = testCode;
        Name = name;
        Details = details;
        PreTestInformation = preTestInformation;
        ReportAvailability = reportAvailability;
        TestUsuage = testUsuage;
        Category = category;
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public String getDetails() {
        return Details;
    }

    public String getPreTestInformation() {
        return PreTestInformation;
    }

    public String getReportAvailability() {
        return ReportAvailability;
    }

    public String getTestUsuage() {
        return TestUsuage;
    }

    public String getCategory() {
        return Category;
    }

    public String getPrice() {
        return Price;
    }

    public String getTestCode() {
        return TestCode;
    }
}
