package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import Model.Order;
import Controller.TopItem;

public class DashboardDAO {

    private Connection conn;

    public DashboardDAO() {
        try {
            conn = dbdao.getConnection(); // Using your dbdao class
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get today's total number of orders
    public int getTodayOrdersCount() {
        String sql = "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get today's total sales
    public double getTodaySalesTotal() {
        String sql = "SELECT SUM(total_amount) FROM orders WHERE DATE(order_date) = CURDATE()";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Get today's orders (basic view)
    public List<Order> getTodayOrders() {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT id, table_id, phone, cashier_name, customer_name FROM orders WHERE DATE(order_date) = CURDATE()";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setTableId(rs.getInt("table_id"));
                order.setPhone(rs.getString("phone"));
                order.setCashierName(rs.getString("cashier_name"));
                order.setCustomerName(rs.getString("customer_name"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Top 5 selling menu items
    public List<TopItem> getTopSellingItems() {
        List<TopItem> items = new ArrayList<>();
        // Adjust the query to your real table/column names for items
        String sql = "SELECT i.name, SUM(oi.quantity) AS total_sold " +
                     "FROM order_items oi JOIN items i ON oi.item_id = i.id " +
                     "GROUP BY oi.item_id ORDER BY total_sold DESC LIMIT 5";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new TopItem(rs.getString("name"), rs.getInt("total_sold")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Daily sales summary (for charting, analytics)
    public List<TopItem> getSalesSummary() {
        List<TopItem> sales = new ArrayList<>();
        String sql = "SELECT DATE(order_date) AS day, SUM(total_amount) AS daily_total " +
                     "FROM orders GROUP BY DATE(order_date) ORDER BY DATE(order_date)";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                sales.add(new TopItem(rs.getString("day"), rs.getInt("daily_total")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    // Get feedback counts for pie chart ("Best", "Good", "Sad")
    public Map<String, Integer> getFeedbackCounts() {
        Map<String, Integer> feedbackCounts = new HashMap<>();

        // Assuming feedback table stores category as "Best", "Good", "Sad" in a 'category' column
        String sql = "SELECT category, COUNT(*) AS count FROM feedback GROUP BY category";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                feedbackCounts.put(rs.getString("category"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Provide default 0 counts for missing categories to avoid JS errors
        if (!feedbackCounts.containsKey("Best")) feedbackCounts.put("Best", 0);
        if (!feedbackCounts.containsKey("Good")) feedbackCounts.put("Good", 0);
        if (!feedbackCounts.containsKey("Sad")) feedbackCounts.put("Sad", 0);

        return feedbackCounts;
    }
}
