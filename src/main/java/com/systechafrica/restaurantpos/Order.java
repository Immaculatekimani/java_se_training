package com.systechafrica.restaurantpos;

public class Order {
    
    private double totalAmount = 0.0;
    final int MAX_ITEMS = 1000;
    private MenuItem[] items = new MenuItem[MAX_ITEMS];

    public void addItem(MenuItem item){
        for (int index = 0; index < items.length; index++) {
            if(items[index] == null){
                items[index] = item;
                totalAmount += item.getItemPrice();
                break;
            }
        }
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public int getMAX_ITEMS() {
        return MAX_ITEMS;
    }
    public MenuItem[] getItems() {
        return items;
    }
    public void setItems(MenuItem[] items) {
        this.items = items;
    }

    
    
    
}
