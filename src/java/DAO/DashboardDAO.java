package dao;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Controller.Order;
import Controller.TopItem;

public class DashboardDAO {

    private Connection conn;

    public DashboardDAO() {
        try {
            conn = dbdao.getConnection(); // using your dbdao class
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public List<Order> getTodayOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT c.name, c.phone, o.id AS order_number, o.table_number " +
                     "FROM orders o JOIN customers c ON o.customer_id = c.id " +
                     "WHERE DATE(o.order_date) = CURDATE()";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("order_number"),
                        rs.getInt("table_number")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<TopItem> getTopSellingItems() {
        List<TopItem> items = new ArrayList<>();
        String sql = "SELECT m.name, SUM(oi.quantity) AS total_sold " +
                     "FROM order_items oi JOIN menu_items m ON oi.menu_item_id = m.id " +
                     "GROUP BY oi.menu_item_id ORDER BY total_sold DESC LIMIT 5";
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

    public List<TopItem> getSalesSummary() {
        List<TopItem> sales = new ArrayList<>();
        String sql = "SELECT DATE(order_date) as day, SUM(total_amount) AS daily_total " +
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
}
