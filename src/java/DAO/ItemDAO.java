package DAO;

import Model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private Connection connection;

    public ItemDAO(Connection connection) {
        this.connection = connection;
    }

    // Retrieve all items with category and image
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT id, name, price, image, category FROM items";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String image = resultSet.getString("image");
                String category = resultSet.getString("category");
                items.add(new Item(id, name, price, image, category));
            }
        }
        return items;
    }

    // Retrieve a specific item by ID with image and category
    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT id, name, price, image, category FROM items WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    String image = resultSet.getString("image");
                    String category = resultSet.getString("category");
                    return new Item(id, name, price, image, category);
                }
            }
        }
        return null;
    }

    // Add a new item to the database
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (name, price, image, category) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setString(3, item.getImage());
            stmt.setString(4, item.getCategory());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
