package Model;

public class OrderItem {
    private int orderItemId;
    private Item item;
    private int itemId;
    private int quantity;
    private double price;      // unit price
    private double totalPrice; // quantity * price

    public OrderItem() {
        // Needed for servlet instantiation
    }

    public OrderItem(int orderItemId, Item item, int quantity) {
        this.orderItemId = orderItemId;
        this.item = item;
        this.itemId = item.getId();
        this.quantity = quantity;
        this.price = item.getPrice();
        this.totalPrice = this.price * this.quantity;
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
        if (item != null) {
            this.itemId = item.getId();
            this.price = item.getPrice();
            this.totalPrice = this.price * this.quantity;
        }
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.price * this.quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        this.totalPrice = this.price * this.quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Convenience methods for JSP compatibility

    /** 
     * Returns the item name for JSP.
     * Matches getItemName() used in JSP
     */
    public String getItemName() {
        return (item != null) ? item.getName() : "Unknown Item";
    }

    /** 
     * Returns the unit price for JSP.
     * Matches getUnitPrice() used in JSP
     */
    public double getUnitPrice() {
        return this.price;
    }
}
