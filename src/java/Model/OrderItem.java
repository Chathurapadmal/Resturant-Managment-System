package Model;

public class OrderItem {
    private int orderItemId;
    private Item item;
    private int itemId;
    private int quantity;
    private double price;
    private double totalPrice;

    public OrderItem() {
        // Needed for servlet instantiation
    }

    public OrderItem(int orderItemId, Item item, int quantity) {
        this.orderItemId = orderItemId;
        this.item = item;
        this.itemId = item.getId();
        this.quantity = quantity;
        this.price = item.getPrice();
        this.totalPrice = price * quantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        this.itemId = item.getId();
        this.price = item.getPrice();
        this.totalPrice = price * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.price * quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.totalPrice = price * quantity;
    }

    // ✅ Added for use in JSP
    public String getName() {
        return item != null ? item.getName() : "Unknown Item";
    }
}
