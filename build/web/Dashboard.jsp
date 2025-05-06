<%-- 
    Document   : Dashboard
    Created on : 6 May 2025, 13:19:36
    Author     : Chathura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Order" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <h2>Dashboard</h2>

    <h3>Orders</h3>
    <table border="1">
        <tr>
            <th>Customer Name</th>
            <th>Order Date</th>
            <th>Total</th>
        </tr>
        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders != null) {
                for (Order o : orders) {
        %>
        <tr>
            <td><%= o.getCustomerName() %></td>
            <td><%= o.getOrderDate() %></td>
            <td><%= o.getTotal() %></td>
        </tr>
        <%
                }
            } else {
        %>
        <tr>
            <td colspan="3">No orders found.</td>
        </tr>
        <%
            }
        %>
    </table>
</body>
</html>
