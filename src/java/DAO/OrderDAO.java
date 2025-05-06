package dao;

import Controller.PosPageServlet;
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

    public void createOrder(PosPageServlet order) {
        String sql = "INSERT INTO orders (table_id, waiter_id, total_amount, order_status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getTableId());
            ps.setInt(2, order.getWaiterId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getOrderStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PosPageServlet> getAllOrders() {
        List<PosPageServlet> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(new PosPageServlet(
                        rs.getInt("id"),
                        rs.getInt("table_id"),
                        rs.getInt("waiter_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("order_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public PosPageServlet getOrderById(int id) {
        PosPageServlet order = null;
        String sql = "SELECT * FROM orders WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new PosPageServlet(
                            rs.getInt("id"),
                            rs.getInt("table_id"),
                            rs.getInt("waiter_id"),
                            rs.getDouble("total_amount"),
                            rs.getString("order_status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public void updateOrder(PosPageServlet order) {
        String sql = "UPDATE orders SET table_id=?, waiter_id=?, total_amount=?, order_status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getTableId());
            ps.setInt(2, order.getWaiterId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getOrderStatus());
            ps.setInt(5, order.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
