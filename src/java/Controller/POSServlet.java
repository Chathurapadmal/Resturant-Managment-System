package Controller;

import DAO.dbdao;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/POSServlet")
public class POSServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type and encoding upfront
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();

        try {
            // Read parameters
            String customerName = request.getParameter("customerName");
            String waiterName = request.getParameter("waiterName");
            String tableIdStr = request.getParameter("tableId");
            String[] itemIds = request.getParameterValues("itemId");

            // Debug log
            System.out.println("Received order:");
            System.out.println("customerName=" + customerName);
            System.out.println("waiterName=" + waiterName);
            System.out.println("tableId=" + tableIdStr);
            System.out.println("itemIds=" + Arrays.toString(itemIds));

            // Validate input
            if (customerName == null || customerName.isEmpty()
                || waiterName == null || waiterName.isEmpty()
                || tableIdStr == null || tableIdStr.isEmpty()
                || itemIds == null || itemIds.length == 0) {

                json.put("success", false);
                json.put("message", "Missing input data.");
                response.getWriter().write(json.toString());
                return;
            }

            int tableId = Integer.parseInt(tableIdStr);

            try (Connection conn = dbdao.getConnection()) {
                conn.setAutoCommit(false);

                // Calculate total
                double total = 0;
                for (String itemIdStr : itemIds) {
                    String qtyStr = request.getParameter("qty_" + itemIdStr);
                    if (qtyStr == null) {
                        throw new NumberFormatException("Missing quantity for item " + itemIdStr);
                    }
                    int qty = Integer.parseInt(qtyStr);
                    double price = getItemPriceById(conn, Integer.parseInt(itemIdStr));
                    total += qty * price;
                }

                // Insert order
                String insertOrderSql = "INSERT INTO orders (customer_name, waiter_name, table_id, order_date, total_amount) VALUES (?, ?, ?, NOW(), ?)";
                int orderId = -1;

                try (PreparedStatement psOrder = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                    psOrder.setString(1, customerName);
                    psOrder.setString(2, waiterName);
                    psOrder.setInt(3, tableId);
                    psOrder.setDouble(4, total);
                    psOrder.executeUpdate();

                    try (ResultSet rs = psOrder.getGeneratedKeys()) {
                        if (rs.next()) {
                            orderId = rs.getInt(1);
                        }
                    }
                }

                // Insert order items
                String insertItemSql = "INSERT INTO order_items (order_id, item_id, item_name, quantity, price) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement psItem = conn.prepareStatement(insertItemSql)) {
                    for (String itemIdStr : itemIds) {
                        int itemId = Integer.parseInt(itemIdStr);
                        int qty = Integer.parseInt(request.getParameter("qty_" + itemIdStr));
                        double price = getItemPriceById(conn, itemId);
                        String name = getItemNameById(conn, itemId);

                        psItem.setInt(1, orderId);
                        psItem.setInt(2, itemId);
                        psItem.setString(3, name);
                        psItem.setInt(4, qty);
                        psItem.setDouble(5, price);
                        psItem.addBatch();
                    }
                    psItem.executeBatch();
                }

                conn.commit();

                json.put("success", true);
                json.put("orderId", orderId);
                response.getWriter().write(json.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                json.put("success", false);
                json.put("message", "Server error: " + e.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(POSServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.getWriter().write(json.toString());
        }
    }

    private double getItemPriceById(Connection conn, int itemId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT price FROM items WHERE id = ?")) {
            ps.setInt(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("price");
            }
        }
        return 0;
    }

    private String getItemNameById(Connection conn, int itemId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT name FROM items WHERE id = ?")) {
            ps.setInt(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("name");
            }
        }
        return "";
    }
}
