package DAO;

import Model.Order;
import Model.OrderItem;
import Model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Get order items for a specific order.
     */
    private List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.item_id, i.name, i.price, oi.quantity " +
                     "FROM order_items oi " +
                     "JOIN items i ON oi.item_id = i.id " +
                     "WHERE oi.order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setId(rs.getInt("item_id"));
                    item.setName(rs.getString("name"));
                    item.setPrice(rs.getDouble("price"));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setItem(item);
                    orderItem.setItemId(item.getId());
                    orderItem.setQuantity(rs.getInt("quantity"));
                    orderItem.setPrice(item.getPrice());

                    items.add(orderItem);
                }
            }
        }

        return items;
    }

    /**
     * Get all orders with their items.
     */
    public List<Order> getAllOrders() throws SQLException {
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

                // Attach order items (throws SQLException if fails)
                order.setOrderItems(getOrderItemsByOrderId(order.getId()));
                orders.add(order);
            }
        }

        return orders;
    }

    public void insertOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders (table_id, waiter_id, total_amount, status, phone, cashier_name, order_date, customer_name, waiter_name) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getTableId());
            ps.setInt(2, order.getWaiterId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getCashierName());
            ps.setString(7, order.getCustomerName());
            ps.setString(8, order.getWaiterName());
            ps.executeUpdate();
        }
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
    }

    public void deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM orders WHERE id = ? AND status != 'processing'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        }
    }

    public int getTodaysOrderCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public double getTodaysSalesTotal() throws SQLException {
        String sql = "SELECT SUM(total_amount) FROM orders WHERE DATE(order_date) = CURDATE()";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    public int insertOrderWithItems(Order order, List<OrderItem> items) throws SQLException {
        String orderSql = "INSERT INTO orders (table_id, waiter_id, total_amount, status, phone, cashier_name, order_date, customer_name, waiter_name) VALUES (?, ?, ?, ?, ?, ?, NOW(), ?, ?)";
        String itemSql = "INSERT INTO order_items (order_id, item_id, quantity, price) VALUES (?, ?, ?, ?)";

        int orderId = -1;

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, order.getTableId());
                ps.setInt(2, order.getWaiterId());
                ps.setDouble(3, order.getTotalAmount());
                ps.setString(4, order.getStatus());
                ps.setString(5, order.getPhone());
                ps.setString(6, order.getCashierName());
                ps.setString(7, order.getCustomerName());
                ps.setString(8, order.getWaiterName());
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }
                }
            }

            if (orderId != -1) {
                for (OrderItem item : items) {
                    try (PreparedStatement psItem = conn.prepareStatement(itemSql)) {
                        psItem.setInt(1, orderId);
                        psItem.setInt(2, item.getItemId());
                        psItem.setInt(3, item.getQuantity());
                        psItem.setDouble(4, item.getPrice());
                        psItem.executeUpdate();
                    }
                }
            }

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }

        return orderId;
    }

    public Order getOrderById(int orderId) throws SQLException {
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

                    order.setOrderItems(getOrderItemsByOrderId(orderId));
                }
            }
        }

        return order;
    }
}
