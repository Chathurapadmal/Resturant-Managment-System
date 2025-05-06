package DAO;

import Controller.MenuItemServlet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private Connection conn;

    public MenuDAO() {
        try {
            conn = dbdao.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MenuItemServlet> getAllMenuItems() {
        List<MenuItemServlet> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new MenuItemServlet(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void insertMenuItem(MenuItemServlet item) {
        String sql = "INSERT INTO menu_items (name, price, description) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, item.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MenuItemServlet getMenuItemById(int id) {
        MenuItemServlet item = null;
        String sql = "SELECT * FROM menu_items WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    item = new MenuItemServlet(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public void updateMenuItem(MenuItemServlet item) {
        String sql = "UPDATE menu_items SET name=?, price=?, description=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, item.getDescription());
            ps.setInt(4, item.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMenuItem(int id) {
        String sql = "DELETE FROM menu_items WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
