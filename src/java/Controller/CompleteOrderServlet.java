package Controller;

import DAO.dbdao;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/CompleteOrderServlet")
public class CompleteOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try (Connection conn = dbdao.getConnection()) {
            // 1. Mark order as completed
            PreparedStatement ps1 = conn.prepareStatement("UPDATE orders SET status = 'completed' WHERE id = ?");
            ps1.setInt(1, orderId);
            ps1.executeUpdate();

            // 2. Mark as printed
            PreparedStatement ps2 = conn.prepareStatement("UPDATE orders SET printed = TRUE WHERE id = ?");
            ps2.setInt(1, orderId);
            ps2.executeUpdate();

            // 3. Redirect to printable bill
            response.sendRedirect("PrintBill.jsp?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
