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

    // Method to get all items from the database
    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                items.add(new Item(id, name, price));
            }
        }
        return items;
    }

    // Method to get a specific item by its ID
    public Item getItemById(int id) throws SQLException {
        String sql = "SELECT * FROM items WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    return new Item(id, name, price);
                }
            }
        }
        return null;
    }

    // Method to insert a new item into the database
    public boolean addItem(Item item) throws SQLException {
        String sql = "INSERT INTO items (name, price) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method to update an existing item
    public boolean updateItem(Item item) throws SQLException {
        String sql = "UPDATE items SET name = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setInt(3, item.getId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    // Method to delete an item
    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }
}
