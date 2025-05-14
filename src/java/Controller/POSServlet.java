package Controller;

import DAO.ItemDAO;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/POSServlet")
public class POSServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            dbdao db = new dbdao();
            conn = db.getConnection();

            ItemDAO itemDAO = new ItemDAO(conn);
            List<Item> items = itemDAO.getAllItems();

            request.setAttribute("itemList", items);
            request.getRequestDispatcher("POS.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(POSServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        try {
            dbdao db = new dbdao();
            conn = db.getConnection();

            String customerName = request.getParameter("customerName");
            String waiterName = request.getParameter("waiterName");
            int tableId = Integer.parseInt(request.getParameter("tableId"));

            String[] selectedItems = request.getParameterValues("itemId");
            List<OrderItem> orderItems = new ArrayList<>();
            double totalAmount = 0;

            ItemDAO itemDAO = new ItemDAO(conn);

            if (selectedItems != null) {
                for (String itemIdStr : selectedItems) {
                    int itemId = Integer.parseInt(itemIdStr);
                    int qty = Integer.parseInt(request.getParameter("qty_" + itemIdStr));
                    Item item = itemDAO.getItemById(itemId);
                    double itemTotal = item.getPrice() * qty;

                    OrderItem oi = new OrderItem();
                    oi.setItemId(itemId);
                    oi.setQuantity(qty);
                    oi.setPrice(item.getPrice());

                    orderItems.add(oi);
                    totalAmount += itemTotal;
                }
            }

            Order order = new Order();
            order.setCustomerName(customerName);
            order.setWaiterName(waiterName);
            order.setTableId(tableId);
            order.setTotalAmount(totalAmount);
            order.setStatus("pending");

            OrderDAO orderDAO = new OrderDAO(conn);
            orderDAO.insertOrderWithItems(order, orderItems);

            response.sendRedirect("POSServlet"); // refresh page after order
        } catch (Exception ex) {
            Logger.getLogger(POSServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Order Processing Failed");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
