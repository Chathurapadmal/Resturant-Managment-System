package DAO;

import Model.Userm;
import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO() {
        try {
            conn = dbdao.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Validate user login
    public Userm validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Userm(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("full_name"),
                    rs.getString("bio"),
                    rs.getString("profile_picture")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update user profile details
    public boolean updateUser(Userm user) {
        String sql = "UPDATE users SET full_name = ?, bio = ?, profile_picture = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getBio());
            ps.setString(3, user.getProfilePicture());
            ps.setInt(4, user.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
