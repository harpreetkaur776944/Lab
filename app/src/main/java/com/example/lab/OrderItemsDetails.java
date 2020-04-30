package com.example.lab;

public class OrderItemsDetails {
    String orderId;
    String ItemName;

    public OrderItemsDetails(String orderId, String itemName) {
        this.orderId = orderId;
        this.ItemName = itemName;
    }

    public OrderItemsDetails() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        this.ItemName = itemName;
    }
}
