package com.example.lab;

public class OrderHistoryDetails {
    String OrderId;
    String Date;
    String TimeSlot;
    String Products;
    String Charges;

    public OrderHistoryDetails(String orderId, String date, String timeSlot, String products, String charges) {
        OrderId = orderId;
        Date = date;
        TimeSlot = timeSlot;
        Products = products;
        Charges = charges;
    }

    public OrderHistoryDetails() {
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTimeSlot() {
        return TimeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        TimeSlot = timeSlot;
    }

    public String getProducts() {
        return Products;
    }

    public void setProducts(String products) {
        Products = products;
    }

    public String getCharges() {
        return Charges;
    }

    public void setCharges(String charges) {
        Charges = charges;
    }
}
