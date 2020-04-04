package com.example.lab;

public class Test {

    String TestCode;
    String Name;
    String Details;
    String PreTestInformation;
    String ReportAvailability;
    String TestUsuage;
    String Category;
    String Price;
    String Type;
    String Offer;

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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getOffer() {
        return Offer;
    }

    public void setOffer(String offer) {
        Offer = offer;
    }

    public Test() {
    }

    public Test(String testCode, String name, String details, String preTestInformation, String reportAvailability, String testUsuage, String category, String price, String type, String offer) {
        TestCode = testCode;
        Name = name;
        Details = details;
        PreTestInformation = preTestInformation;
        ReportAvailability = reportAvailability;
        TestUsuage = testUsuage;
        Category = category;
        Price = price;
        Type = type;
        Offer = offer;
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
