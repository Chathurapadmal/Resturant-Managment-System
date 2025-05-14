package Model;

import java.sql.Timestamp;

public class Order {
    private int id;
    private int tableId;
    private int waiterId;
    private double totalAmount;
    private String status;
    private Timestamp orderDate;
    private String phone;
    private String cashierName;
    private int orderid;
    private String WaiterName;
    private String CustomerName;
    private String OrderItem;


    // Constructor for inserting a new order
    public Order(int tableId, int waiterId, double totalAmount, String status, String phone, String cashierName) {
        this.tableId = tableId;
        this.waiterId = waiterId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.phone = phone;
        this.cashierName = cashierName;
    }

    // Empty constructor for setters
    public Order() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTableId() { return tableId; }
    public void setTableId(int tableId) { this.tableId = tableId; }

    public int getWaiterId() { return waiterId; }
    public void setWaiterId(int waiterId) { this.waiterId = waiterId; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getOrderDate() { return orderDate; }
    public void setOrderDate(Timestamp orderDate) { this.orderDate = orderDate; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCashierName() { return cashierName; }
    public void setCashierName(String cashierName) { this.cashierName = cashierName; }

    public int getOrderid() { return orderid; }
    public void setOrderid(int orderid) { this.orderid = orderid; }

    public String getCustomerName() { return CustomerName;}
    public void setCustomerName(String CustomerName) { this.CustomerName = CustomerName; }
    

    public String getWaiterName() {return WaiterName; }
    public void setWaiterName(String WaiterName) { this.WaiterName = WaiterName; }
    
    public String getOrderItem() { return OrderItem; }
    public void setOrderItem(String OrderItem) { this.OrderItem = OrderItem; }
    
}
