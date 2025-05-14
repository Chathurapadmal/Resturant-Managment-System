package Controller;

import DAO.OrderDAO;
import DAO.dbdao;
import Model.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/KitchenDashboardServlet")
public class KitchenDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        Connection conn = null;

        try {
            dbdao db = new dbdao();
            conn = db.getConnection();

            OrderDAO dao = new OrderDAO(conn);
            List<Order> orders = dao.getAllOrders();

            request.setAttribute("orders", orders);
            RequestDispatcher rd = request.getRequestDispatcher("KitchenDashboard.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Connection conn = null;

        try {
            dbdao db = new dbdao();
            conn = db.getConnection();

            String action = req.getParameter("action");
            int orderId = Integer.parseInt(req.getParameter("orderId"));

            OrderDAO dao = new OrderDAO(conn);

            switch (action) {
                case "start":
                    dao.updateOrderStatus(orderId, "processing");
                    break;
                case "complete":
                    dao.updateOrderStatus(orderId, "completed");
                    break;
                case "delete":
                    dao.deleteOrder(orderId);
                    break;
                case "edit":
                    // Optional: Add edit logic here
                    break;
            }

            res.sendRedirect("KitchenDashboardServlet");

        } catch (Exception e) {
            e.printStackTrace();
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Order update failed");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
