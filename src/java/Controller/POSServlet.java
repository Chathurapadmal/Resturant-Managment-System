package Controller;

import DAO.ItemDAO;
import DAO.OrderDAO;
import DAO.dbdao;
import Model.Item;
import Model.Order;
import Model.OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.*;

@WebServlet("/POSServlet")
public class POSServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (Connection conn = dbdao.getConnection()) {
            ItemDAO itemDAO = new ItemDAO(conn);
            List<Item> items = itemDAO.getAllItems();

            // Group items by category
            Map<String, List<Item>> categorizedItems = new HashMap<>();
            for (Item item : items) {
                String category = item.getCategory(); // Make sure getCategory() exists and returns category name
                categorizedItems.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
            }

            request.setAttribute("categorizedItems", categorizedItems);
            request.getRequestDispatcher("pos.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Error loading POS: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String customerName = request.getParameter("customerName");
        String phone = request.getParameter("phone");
        int tableId = Integer.parseInt(request.getParameter("tableId"));
        String waiterName = request.getParameter("waiterName");
        String cashierName = request.getParameter("cashierName");
        String itemsJson = request.getParameter("itemsJson");

        Gson gson = new Gson();
        Type itemListType = new TypeToken<List<Map<String, Object>>>() {}.getType();
        List<Map<String, Object>> itemMapList = gson.fromJson(itemsJson, itemListType);

        try (Connection conn = dbdao.getConnection()) {
            OrderDAO orderDAO = new OrderDAO(conn);
            List<OrderItem> orderItems = new ArrayList<>();
            double totalAmount = 0;

            for (Map<String, Object> map : itemMapList) {
                OrderItem oi = new OrderItem();
                int itemId = ((Double) map.get("id")).intValue();
                int qty = ((Double) map.get("quantity")).intValue();
                double price = (Double) map.get("price");

                oi.setItemId(itemId);
                oi.setQuantity(qty);
                oi.setPrice(price);
                totalAmount += price * qty;
                orderItems.add(oi);
            }

            Order order = new Order();
            order.setCustomerName(customerName);
            order.setPhone(phone);
            order.setTableId(tableId);
            order.setWaiterName(waiterName);
            order.setCashierName(cashierName);
            order.setTotalAmount(totalAmount);
            order.setStatus("pending");
            order.setWaiterId(1); // Replace with real user ID/session info

            int orderId = orderDAO.insertOrderWithItems(order, orderItems);

            // Reload items for display after order submission
            ItemDAO itemDAO = new ItemDAO(conn);
            List<Item> items = itemDAO.getAllItems();
            Map<String, List<Item>> categorizedItems = new HashMap<>();
            for (Item item : items) {
                String category = item.getCategory();
                categorizedItems.computeIfAbsent(category, k -> new ArrayList<>()).add(item);
            }
            request.setAttribute("categorizedItems", categorizedItems);
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("pos.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Order failed: " + e.getMessage());
        }
    }
}
