package DAO;

import Model.product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection conn;

    public ProductDAO() {
        try {
            conn = dbdao.getConnection(); // Assumes dbdao.getConnection() is implemented
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<product> getAllProducts() {
        List<product> products = new ArrayList<>();
        String sql = "SELECT id, name, price, image FROM products";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                product p = new product(
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("image")
                );
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public boolean addProduct(product product) {
        String sql = "INSERT INTO products (name, price, image) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getImage());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
