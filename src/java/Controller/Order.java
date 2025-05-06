package Controller;

import Model.*;

public class Order {
    private final String customerName;
    private final String phoneNumber;
    private final int orderNumber;
    private final int tableNumber;

    public Order(String customerName, String phoneNumber, int orderNumber, int tableNumber) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.orderNumber = orderNumber;
        this.tableNumber = tableNumber;
    }

    // Getters
    public String getCustomerName() { return customerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getOrderNumber() { return orderNumber; }
    public int getTableNumber() { return tableNumber; }
}
