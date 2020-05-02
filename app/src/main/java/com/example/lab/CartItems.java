package com.example.lab;

public class CartItems {
    String itemCode;
    String itemTime;
    String itemDate;
    String itemPrice;
    String itemName;


    public CartItems(String itemCode, String itemTime, String itemDate, String itemPrice,String itemName) {
        this.itemCode = itemCode;
        this.itemTime = itemTime;
        this.itemDate = itemDate;
        this.itemPrice = itemPrice;
        this.itemName = itemName;
    }

    public CartItems() {
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
