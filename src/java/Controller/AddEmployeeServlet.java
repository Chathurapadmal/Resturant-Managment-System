package Controller;

import DAO.dbdao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fullName = request.getParameter("fullName");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        try (Connection con = dbdao.getConnection()) {
            String sql = "INSERT INTO users (full_name, username, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, fullName);
            stmt.setString(2, username);
            stmt.setString(3, password); 
            stmt.setString(4, role);

            stmt.executeUpdate();
            response.sendRedirect("add_employee.jsp?success=true");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("add_employee.jsp?error=true");
        }
    }
}
