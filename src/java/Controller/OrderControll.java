package Controller;

import DAO.OrderDAO;
import DAO.dbdao;
import Model.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/OrderControll")
public class OrderControll extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection conn = null;

        try {
            // Establish connection
            dbdao db = new dbdao();
            conn = db.getConnection();

            String action = request.getParameter("action");

            if ("create".equals(action)) {
                int tableId = Integer.parseInt(request.getParameter("table_id"));
                int waiterId = Integer.parseInt(request.getParameter("waiter_id"));
                double totalAmount = Double.parseDouble(request.getParameter("total_amount"));
                String status = request.getParameter("order_status");
                String phone = request.getParameter("phone");
                String cashierName = request.getParameter("cashier_name");

                // Create Order object
                Order order = new Order(tableId, waiterId, totalAmount, status, phone, cashierName);

                // Use DAO with connection
                OrderDAO dao = new OrderDAO(conn);
                dao.insertOrder(order);
            }

            // Success: redirect to POS
            response.sendRedirect("pos.jsp");

        } catch (Exception e) {
            e.printStackTrace(); // Log for debugging
            request.setAttribute("error", "Invalid order input.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } finally {
            // Clean up connection
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
