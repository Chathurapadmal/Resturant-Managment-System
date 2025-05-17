<%-- 
    Document   : All_Orders
    Created on : 17 May 2025, 23:31:36
    Author     : Chathura
--%>

<%@ page import="java.util.List" %>
<%@ page import="Model.Order" %>
<%@ page import="Model.OrderItem" %>  <%-- Added import for OrderItem --%>
<html>
    <head>
        
        <link rel="stylesheet" href="Style/allorder.css">
        
    </head>>
    <body>
<%
    List<Order> orders = (List<Order>) request.getAttribute("orderList");
    if (orders != null && !orders.isEmpty()) {
%>
<table border="1">
    <thead>
        <tr>
            <th>Order ID</th>
            <th>Table</th>
            <th>Waiter</th>
            <th>Cashier</th>
            <th>Customer</th>
            <th>Status</th>
            <th>Order Date</th>
            <th>Total Amount</th>
            <th>Items</th>
        </tr>
    </thead>
    <tbody>
    <% 
        for (Order order : orders) {
    %>
        <tr>
            <td><%= order.getId() %></td>
            <td><%= order.getTableId() %></td>
            <td><%= order.getWaiterName() %></td>
            <td><%= order.getCashierName() %></td>
            <td><%= order.getCustomerName() %></td>
            <td><%= order.getStatus() %></td>
            <td><%= order.getOrderDate() %></td>
            <td><%= order.getTotalAmount() %></td>
            <td>
                <ul>
                <% 
                  if (order.getOrderItems() != null) {
                    for (OrderItem item : order.getOrderItems()) {  // changed var to OrderItem
                %>
                    <li><%= item.getItem().getName() %> x <%= item.getQuantity() %></li>
                <%  
                    }
                  }
                %>
                </ul>
            </td>
        </tr>
    <% } %>
    </tbody>
</table>
<%
    } else {
%>
<p>No orders found.</p>
<%
    }
%>
    </body></html>>