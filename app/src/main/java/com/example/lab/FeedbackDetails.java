package com.example.lab;

public class FeedbackDetails {
    String url;
    String city;
    String date;
    String name;
    String phone;
    String ques1;
    String ques2;

    public FeedbackDetails(String url, String city, String date, String name, String phone, String ques1, String ques2) {
        this.url = url;
        this.city = city;
        this.date = date;
        this.name = name;
        this.phone = phone;
        this.ques1 = ques1;
        this.ques2 = ques2;
    }

    public FeedbackDetails() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQues1() {
        return ques1;
    }

    public void setQues1(String ques1) {
        this.ques1 = ques1;
    }

    public String getQues2() {
        return ques2;
    }

    public void setQues2(String ques2) {
        this.ques2 = ques2;
    }
}
