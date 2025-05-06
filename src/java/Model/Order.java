package Model;

public class Order {
    private int id;
    private String customerName;
    private String orderDate;
    private double total;

    public Order(int id, String customerName, String orderDate, double total) {
        this.id = id;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.total = total;
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getOrderDate() { return orderDate; }
    public double getTotal() { return total; }
}
