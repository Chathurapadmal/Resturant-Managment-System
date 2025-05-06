package model;

public class PosPageServlet {
    private int id;
    private int tableId;
    private int waiterId;
    private double totalAmount;
    private String orderStatus;

    public PosPageServlet() {}

    public PosPageServlet(int id, int tableId, int waiterId, double totalAmount, String orderStatus) {
        this.id = id;
        this.tableId = tableId;
        this.waiterId = waiterId;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(int waiterId) {
        this.waiterId = waiterId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
