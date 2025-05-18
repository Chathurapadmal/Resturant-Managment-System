package Controller;

import DAO.dbdao;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/FeedbackServlet")
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderIdStr = request.getParameter("orderId");
        String ratingStr = request.getParameter("rating");

        if(orderIdStr == null || ratingStr == null) {
            response.sendRedirect("");
            return;
        }

        int orderId = Integer.parseInt(orderIdStr);
        int rating = Integer.parseInt(ratingStr);

        try (Connection conn = dbdao.getConnection()) {
            String sql = "INSERT INTO feedback (order_id, rating) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ps.setInt(2, rating);
                ps.executeUpdate();
            }
            response.sendRedirect("");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Redirect GET requests to an error page or another appropriate page
        response.sendRedirect("");
    }
}
