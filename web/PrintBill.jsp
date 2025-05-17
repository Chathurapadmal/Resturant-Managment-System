<%@ page import="java.sql.*, DAO.dbdao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, DAO.dbdao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int orderId = Integer.parseInt(request.getParameter("orderId"));
    Connection conn = dbdao.getConnection();

    PreparedStatement psOrder = conn.prepareStatement("SELECT * FROM orders WHERE id = ?");
    psOrder.setInt(1, orderId);
    ResultSet rsOrder = psOrder.executeQuery();

    PreparedStatement psItems = conn.prepareStatement("SELECT * FROM order_items WHERE order_id = ?");
    psItems.setInt(1, orderId);
    ResultSet rsItems = psItems.executeQuery();

    String customerName = "";
    String orderDate = "";
    double total = 0;
    if (rsOrder.next()) {
        customerName = rsOrder.getString("customer_name");
        orderDate = rsOrder.getString("order_date");
        total = rsOrder.getDouble("total_amount");
    }
%>

<html>
<head>
    <title>Printable Bill</title>
    <style>
        body { font-family: 'Courier New'; margin: 40px; }
        h2, h4 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid black; padding: 8px; text-align: center; }
        .total { text-align: right; font-weight: bold; }
        .print-btn { display: block; margin: 20px auto; padding: 10px 20px; font-size: 16px; }
    </style>
</head>
<body>
    <h2>Restaurant POS - Bill</h2>
    <h4>Order ID: <%= orderId %><br>Customer: <%= customerName %><br>Date: <%= orderDate %></h4>

    <table>
        <tr>
            <th>Item</th>
            <th>Qty</th>
            <th>Unit Price</th>
            <th>Subtotal</th>
        </tr>
        <%
            while (rsItems.next()) {
                String itemName = rsItems.getString("item_name");
                int quantity = rsItems.getInt("quantity");
                double price = rsItems.getDouble("price");
                double subtotal = quantity * price;
        %>
        <tr>
            <td><%= itemName %></td>
            <td><%= quantity %></td>
            <td><%= String.format("%.2f", price) %></td>
            <td><%= String.format("%.2f", subtotal) %></td>
        </tr>
        <% } %>
        <tr>
            <td colspan="3" class="total">Total</td>
            <td><%= String.format("%.2f", total) %></td>
        </tr>
    </table>

    <button class="print-btn" onclick="window.print()">Print</button>
</body>
</html>
