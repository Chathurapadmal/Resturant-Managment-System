<%-- 
    Document   : kitchendashboard
    Created on : 8 May 2025, 21:08:25
    Author     : Chathura
--%>

<%@page import="Model.Order"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Kitchen Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff8f0;
            margin: 0;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #f97c0d;
        }
        .order-card {
            display: inline-block;
            border: 1px solid #ccc;
            padding: 15px;
            margin: 10px;
            border-radius: 10px;
            width: 220px;
            background-color: #fff3e6;
            box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
        }
        .order-card h4, .order-card p {
            margin: 8px 0;
        }
        .btn {
            padding: 6px 12px;
            margin: 5px 2px;
            border: none;
            border-radius: 5px;
            background-color: #f97c0d;
            color: white;
            cursor: pointer;
        }
        .btn:disabled {
            background-color: #aaa;
            cursor: default;
        }
    </style>
</head>
<body>

<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
        for (Order o : orders) {
%>
    <div class="order-card">
        <h4>Order #: <%= o.getId() %></h4>
        <h4>Name: <%= o.getCashierName() %></h4> <%-- Updated from getCustomerName() to getCashierName() --%>
        <p>Table #: <%= o.getTableId() %></p>
        <p>Waiter ID: <%= o.getWaiterId() == 0 ? "N/A" : o.getWaiterId() %></p>

        <form method="post" action="KitchenDashboard">
            <input type="hidden" name="orderId" value="<%= o.getId() %>">
            <% if (!"processing".equalsIgnoreCase(o.getStatus())) { %>
                <button class="btn" name="action" value="start">Start</button>
                <button class="btn" name="action" value="delete">Delete</button>
            <% } else { %>
                <button class="btn" disabled>Started</button>
            <% } %>
            <button class="btn" name="action" value="edit">Edit</button>
            <button class="btn" name="action" value="complete">Complete</button>
        </form>
    </div>
<%
        }
    } else {
%>
    <p style="text-align:center;">No orders available in the kitchen.</p>
<%
    }
%>

</body>
</html>
