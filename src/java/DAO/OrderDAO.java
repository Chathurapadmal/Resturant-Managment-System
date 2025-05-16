package DAO;

import Model.Order;
import Model.OrderItem;
import Model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection conn;

    public OrderDAO(Connection conn1) {
        this.conn = conn1;
    }

    // 🔄 Helper: Get order items by order ID
    private List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.item_id, i.name, i.price, oi.quantity " +
                     "FROM order_items oi " +
                     "JOIN items i ON oi.item_id = i.id " +
                     "WHERE oi.order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem orderItem = new OrderItem();

                    Item item = new Item();
                    item.setId(rs.getInt("item_id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getDouble("price"));

                    orderItem.setItem(item);
                    orderItem.setItemId(item.getId());
                    orderItem.setQuantity(rs.getInt("quantity"));
                    orderItem.setPrice(item.getPrice());

                    items.add(orderItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // ✅ Get all orders with order items
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTableId(rs.getInt("table_id"));
                order.setWaiterId(rs.getInt("waiter_id"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setPhone(rs.getString("phone"));
                order.setCashierName(rs.getString("cashier_name"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setWaiterName(rs.getString("waiter_name"));

                // Load items
                List<OrderItem> items = getOrderItemsByOrderId(order.getId());
                order.setOrderItems(items);

                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // 🔽 Insert only order
    public void insertOrder(Order order) {
        String sql = "INSERT INTO orders (table_id, waiter_id, total_amount, status, phone, cashier_name, order_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW())";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getTableId());
            ps.setInt(2, order.getWaiterId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getCashierName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔄 Update order status
    public void updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ❌ Delete order if not processing
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ? AND status != 'processing'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔢 Get today's order count
    public int getTodaysOrderCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // 💰 Get today's total sales
    public double getTodaysSalesTotal() {
        double total = 0;
        String sql = "SELECT SUM(total_amount) FROM orders WHERE DATE(order_date) = CURDATE()";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    // 🔽 Insert order and items
    public void insertOrderWithItems(Order order, List<OrderItem> items) {
        String orderSql = "INSERT INTO orders (table_id, waiter_id, total_amount, status, phone, cashier_name, order_date, customer_name, waiter_name) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?)";
        String itemSql = "INSERT INTO order_items (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getTableId());
            ps.setInt(2, order.getWaiterId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getCashierName());
            ps.setString(7, order.getCustomerName());
            ps.setString(8, order.getWaiterName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            for (OrderItem item : items) {
                PreparedStatement psItem = conn.prepareStatement(itemSql);
                psItem.setInt(1, orderId);
                psItem.setInt(2, item.getItemId());
                psItem.setInt(3, item.getQuantity());
                psItem.setDouble(4, item.getPrice());
                psItem.executeUpdate();
                psItem.close();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 🔍 Get a single order
    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setTableId(rs.getInt("table_id"));
                    order.setWaiterId(rs.getInt("waiter_id"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setStatus(rs.getString("status"));
                    order.setPhone(rs.getString("phone"));
                    order.setCashierName(rs.getString("cashier_name"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setCustomerName(rs.getString("customer_name"));
                    order.setWaiterName(rs.getString("waiter_name"));

                    List<OrderItem> items = getOrderItemsByOrderId(order.getId());
                    order.setOrderItems(items);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }
}
