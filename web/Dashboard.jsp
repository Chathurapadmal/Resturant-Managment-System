<%-- 
    Document   : Dashboard
    Created on : 6 May 2025, 13:19:36
    Author     : Chathura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Order" %>
<%@ page import="Controller.TopItem" %>

<html>
    <head>
        <title>Admin Dashboard</title>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            /* Add your CSS styles here to match the image */
        </style>
    </head>
    <body>
        <div class="header">
            <h1>Admin Panel</h1>
            <nav>
                <button onclick="location.href = 'orderList.jsp'">Order List</button>
                <button onclick="location.href = 'kitchenDashboard.jsp'">Kitchen Dashboard</button>
                <button onclick="location.href = 'pos.jsp'">POS Invoice</button>
                <button onclick="location.href = 'addProduct.jsp'">Add Product</button>
            </nav>
        </div>

        <div class="stats">
            <div>Today Orders: <%= request.getAttribute("todayOrders")%></div>
            <div>Today Sales: <%= request.getAttribute("todaySales")%> LKR</div>
        </div>

        <h2>Order Summary</h2>
        <table border="1">
            <tr>
                <th>Customer Name</th>
                <th>Phone Number</th>
                <th>Order Number</th>
                <th>Table Number</th>
            </tr>
            <%
                List<Order> orders = (List<Order>) request.getAttribute("orders");
                for (Order o : orders) {
            %>
            <tr>
                <td><%= o.getCustomerName()%></td>
                <td><%= o.getPhoneNumber()%></td>
                <td><%= o.getOrderNumber()%></td>
                <td><%= o.getTableNumber()%></td>
            </tr>
            <% } %>
        </table>

        <h2>Sales Summary</h2>
        <canvas id="salesChart"></canvas>

        <h2>Top Selling Items</h2>
        <ul>
            <%
                List<TopItem> topItems = (List<TopItem>) request.getAttribute("topItems");
                for (TopItem item : topItems) {
            %>
            <li><%= item.getLabel()%>: <%= item.getValue()%> sold</li>
                <% } %>
        </ul>

        <script>
            const labels = [
            <%
                    List<TopItem> sales = (List<TopItem>) request.getAttribute("salesSummary");
                    for (int i = 0; i < sales.size(); i++) {
                        out.print("'" + sales.get(i).getLabel() + "'");
                        if (i != sales.size() - 1) {
                            out.print(",");
                        }
                    }
            %>
            ];

            const data = {
                labels: labels,
                datasets: [{
                        label: 'Sales Over Time',
                        data: [
            <%
                            for (int i = 0; i < sales.size(); i++) {
                                out.print(sales.get(i).getValue());
                                if (i != sales.size() - 1) {
                                    out.print(",");
                                }
                            }
            %>
                        ],
                        borderColor: 'orange',
                        fill: false
                    }]
            };

            const config = {
                type: 'line',
                data: data,
            };

            new Chart(
                    document.getElementById('salesChart'),
                    config
                    );
        </script>
    </body>
</html>
