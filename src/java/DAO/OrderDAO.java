package DAO;

import Model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection conn;

    public OrderDAO() {
        try {
            conn = dbdao.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Order(
                    rs.getInt("id"),
                    rs.getString("customer_name"),
                    rs.getString("order_date"),
                    rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getTodaysOrderCount() {
    int count = 0;
    String sql = "SELECT COUNT(*) FROM orders WHERE DATE(order_date) = CURDATE()";
    try (Connection conn = dbdao.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            count = rs.getInt(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return count;
}

public double getTodaysSalesTotal() {
    double total = 0;
    String sql = "SELECT SUM(total_amount) FROM orders WHERE DATE(order_date) = CURDATE()";
    try (Connection conn = dbdao.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            total = rs.getDouble(1);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return total;
}

}
