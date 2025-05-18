package DAO;

import Model.Item;
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

    public List<Item> getAllMenuItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, name, price, image, category FROM items ORDER BY category, name";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new Item(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("image"),
                    rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Other CRUD methods unchanged...
}
