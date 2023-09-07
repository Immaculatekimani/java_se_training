package com.systechafrica.restaurantpos;

public class MenuItem {
    private int itemNumber;
    private String itemName;
    private double itemPrice;

    public MenuItem(int itemNumber, String itemName, double itemPrice) {
        this.itemNumber = itemNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }
    
    public MenuItem(){

    }
    public int getItemNumber() {
        return itemNumber;
    }
    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public double getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
    
}
