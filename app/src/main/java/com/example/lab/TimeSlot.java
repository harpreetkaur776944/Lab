package com.example.lab;

public class TimeSlot {

    String orderId;
    String date;
    String time;

    public TimeSlot(String orderId, String date, String time) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TimeSlot()
    {

    }

}
