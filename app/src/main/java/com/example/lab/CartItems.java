package com.example.lab;

import java.util.List;

public class CartItems {
    String itemCode;
    String itemTime;
    String itemDate;
    String itemPrice;


    public CartItems(String itemCode, String itemTime, String itemDate, String itemPrice) {
        this.itemCode = itemCode;
        this.itemTime = itemTime;
        this.itemDate = itemDate;
        this.itemPrice = itemPrice;

    }

    public CartItems() {
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
