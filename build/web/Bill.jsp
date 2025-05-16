<%-- 
    Document   : Bill
    Created on : 16 May 2025, 14:50:27
    Author     : Chathura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Model.Order, Model.OrderItem" %>
<!DOCTYPE html>

<%
    Order order = (Order) request.getAttribute("order");
%>
<html>
<head>
    <title>Bill</title>
    <style>
        body { font-family: Arial; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        td, th { border: 1px solid #ccc; padding: 8px; text-align: left; }
        .total { font-weight: bold; }
        .center { text-align: center; margin-top: 20px; }
    </style>
</head>
<body>
<h2>Restaurant Bill</h2>
<p>Order ID: <%= order.getId() %></p>
<p>Date: <%= order.getOrderDate() %></p>
<p>Customer: <%= order.getCustomerName() %></p>
<p>Table: <%= order.getTableId() %></p>

<table>
    <tr>
        <th>Item</th>
        <th>Qty</th>
        <th>Unit Price</th>
        <th>Total</th>
    </tr>
    <% for(OrderItem item : order.getOrderItems()) { %>
        <tr>
            <td><%= item.getItem().getName() %></td>
            <td><%= item.getQuantity() %></td>
            <td>Rs. <%= item.getPrice() %></td>
            <td>Rs. <%= item.getTotalPrice() %></td>
        </tr>
    <% } %>
    <tr class="total">
        <td colspan="3">Total</td>
        <td>Rs. <%= order.getTotalAmount() %></td>
    </tr>
</table>

<div class="center">
    <button onclick="window.print()">🖨️ Print Bill</button>
</div>

</body>
</html>

