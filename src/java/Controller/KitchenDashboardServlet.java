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

        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        try (Connection conn = dbdao.getConnection()) {
            OrderDAO dao = new OrderDAO(conn);
            List<Order> orders = dao.getAllOrders();

            request.setAttribute("orders", orders);
            RequestDispatcher rd = request.getRequestDispatcher("KitchenDashboard.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            // Log full stack trace
            e.printStackTrace();

            // Send developer-friendly error message to browser for debugging
            response.setContentType("text/plain");
            response.getWriter().println("Error loading kitchen dashboard:");
            response.getWriter().println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String orderIdParam = request.getParameter("orderId");

        if (action == null || orderIdParam == null || orderIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameters.");
            return;
        }

        try (Connection conn = dbdao.getConnection()) {
            int orderId = Integer.parseInt(orderIdParam);

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
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action.");
                    return;
            }

            response.sendRedirect("KitchenDashboardServlet");

        } catch (NumberFormatException nfe) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID.");
        } catch (Exception e) {
            e.printStackTrace();

            response.setContentType("text/plain");
            response.getWriter().println("Error updating order:");
            response.getWriter().println(e.getClass().getName() + ": " + e.getMessage());
        }
    }
}
