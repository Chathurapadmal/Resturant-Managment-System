package Controller;

import DAO.MenuDAO;
import DAO.OrderDAO;
import DAO.dbdao;
import Model.Item;
import Model.Order;
import Model.OrderItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/POSServlet")
public class POSServlet extends HttpServlet {

    private Connection conn;

    @Override
    public void init() {
        conn = dbdao.getConnection();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch menu items dynamically from DB
        MenuDAO menuDAO = new MenuDAO();
        List<Item> menuItems = menuDAO.getAllMenuItems();

        // Set menu items as request attribute and forward to JSP
        request.setAttribute("menuItems", menuItems);
        request.getRequestDispatcher("pos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Set UTF-8 encoding and content type
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            // Read form fields
            String customerName = request.getParameter("customerName");
            String tableStr = request.getParameter("table");
            String[] itemIds = request.getParameterValues("itemId");
            String[] qtys = request.getParameterValues("qty");
            String[] prices = request.getParameterValues("price");

            if (tableStr == null || itemIds == null || qtys == null || prices == null) {
                response.getWriter().println("Missing order details. Please try again.");
                return;
            }

            int tableId = Integer.parseInt(tableStr);

            // Create order
            Order order = new Order();
            order.setCustomerName(customerName != null ? customerName : "");
            order.setTableId(tableId);
            order.setStatus("pending");
            order.setCashierName("cashier"); // replace with real user/session if needed

            // Build order items list
            List<OrderItem> orderItems = new ArrayList<>();
            double totalAmount = 0;

            for (int i = 0; i < itemIds.length; i++) {
                int id = Integer.parseInt(itemIds[i]);
                int quantity = Integer.parseInt(qtys[i]);
                double price = Double.parseDouble(prices[i]);

                if(quantity <= 0) continue;  // skip zero quantity items

                OrderItem item = new OrderItem();
                item.setItemId(id);
                item.setQuantity(quantity);
                item.setPrice(price);

                totalAmount += price * quantity;

                orderItems.add(item);
            }

            if(orderItems.isEmpty()) {
                response.getWriter().println("Please order at least one item.");
                return;
            }

            order.setTotalAmount(totalAmount);

            // Insert into DB
            OrderDAO orderDAO = new OrderDAO(conn);
            int orderId = orderDAO.insertOrderWithItems(order, orderItems);

            // On success, show confirmation and link back
            response.getWriter().println("<h3>Order placed successfully. Order ID: " + orderId + "</h3>");
            response.getWriter().println("<a href='POSServlet'>Place another order</a>");

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error processing order: " + e.getMessage());
        }
    }

    @Override
    public void destroy() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
