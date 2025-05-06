<%-- 
    Document   : pos
    Created on : 3 May 2025, 20:14:47
    Author     : Chathura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="dao.OrderDAO" %>
<%@ page import="Controller.PosPageServlet" %>
<!DOCTYPE html>
<html>
<head>
    <title>POS System</title>
</head>
<body>
    <h1>Point of Sale (POS)</h1>

    <form action="OrderControll" method="post">
        <input type="hidden" name="action" value="create">
        
        <label>Table ID:</label>
        <input type="number" name="table_id" required><br><br>

        <label>Waiter ID:</label>
        <input type="number" name="waiter_id" required><br><br>

        <label>Total Amount:</label>
        <input type="number" step="0.01" name="total_amount" required><br><br>

        <label>Order Status:</label>
        <select name="order_status">
            <option value="Pending">Pending</option>
            <option value="Completed">Completed</option>
        </select><br><br>

        <button type="submit">Submit Order</button>
    </form>

    <h2>Current Orders</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Table ID</th>
            <th>Waiter ID</th>
            <th>Total Amount</th>
            <th>Status</th>
        </tr>
        <%
            OrderDAO orderDAO = new OrderDAO();
            for (PosPageServlet order : orderDAO.getAllOrders()) {
        %>
        <tr>
            <td><%= order.getId() %></td>
            <td><%= order.getTableId() %></td>
            <td><%= order.getWaiterId() %></td>
            <td><%= order.getTotalAmount() %></td>
            <td><%= order.getOrderStatus() %></td>
        </tr>
        <% } %>
    </table>
</body>
</html>
