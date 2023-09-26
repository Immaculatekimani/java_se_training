package com.systechafrica.possystemupdate;

public class Item {
    private int id;
    private String itemCode;
    private int quantity;
    private double price;

    public Item(int id, String itemCode, int quantity, double price) {
        this.id = id;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.price = price;
    }
    
    public Item(String itemCode, int quantity, double price){
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.price = price;
    }
    public Item(){
        
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
