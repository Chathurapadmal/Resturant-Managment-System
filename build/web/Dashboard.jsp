<%-- 
    Document   : Dashboard
    Created on : 6 May 2025, 13:19:36
    Author     : Chathura
--%>

<%@page import="Controller.TopItem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Order" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link href="Style/Dashboard.css" rel="stylesheet" type="text/css"/>
</head>
<body>
  <header class="header">
<div class="logo">
  <img src="logo.png" alt="Infinity Dine Logo" class="logo-img" />
</div>   
 <div class="title">ADMIN PANEL</div>
    <div class="profile.jsp">Profile</div>
  </header>
    
      <nav class="navbar">
        <form action="orderListServlet"><button>Order List</button></form>
        <form action="KitchenDashboardServlet"><button>Kitchen Dashboard</button></form>
        <form action="POSServlet"><button>POS Invoice</button></form>
        <form action="add_item.jsp"><button>Add Product</button></form>

  </nav>


    <section class="summary-cards">
        <div class="card">
            <div>Today Orders</div>
            <h2><%= request.getAttribute("todaysOrders") != null ? request.getAttribute("todaysOrders") : 0 %></h2>
        </div>
        <div class="card">
            <div>Today Sales</div>
            <h2><%= request.getAttribute("todaysSales") != null ? request.getAttribute("todaysSales") : 0 %></h2>
        </div>
    </section>

    <div class="section-title">Order Summary</div>
    
        <tr>
            <th>Customer Name</th>
            <th>Phone Number</th>
            <th>Order Number</th>
            <th>Table Number</th>
        </tr>
        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders != null && !orders.isEmpty()) {
                for (Order o : orders) {
        %>
        
            <td><%= o.getCashierName() %></td>
  
        
        <% 
                }
            } else {
        %>
        <tr>
            <td colspan="4">No orders available.</td>
        </tr>
        <% } %>
    

    <div class="section-title">Sales Summary</div>
    <div class="chart-container">
       <canvas id="salesChart" width="400" height="200">
            
            <script>
    const dates = <%= request.getAttribute("datesJson") %>;
    const totals = <%= request.getAttribute("totalsJson") %>;

    const ctx = document.getElementById('salesChart').getContext('2d');
    const salesChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: dates,
            datasets: [{
                label: 'Sales',
                data: totals,
                borderColor: 'orange',
                backgroundColor: 'rgba(249, 124, 13, 0.2)',
                fill: true,
                tension: 0.3
            }]
        },
        options: {
            scales: {
                y: { beginAtZero: true }
            }
        }
    });
</script>
            
        </canvas>
    </div>

<div class="section-title">Top Selling Items</div>
<table>
    <tr>
        <th>Item Name</th>
        <th>Total Sold</th>
    </tr>
    <%
        List<TopItem> topItems = (List<TopItem>) request.getAttribute("topItems");
        if (topItems != null && !topItems.isEmpty()) {
            for (TopItem item : topItems) {
    %>
    <tr>
        <td><%= item.getName() %></td>
        <td><%= item.getTotalSold() %></td>
    </tr>
    <%
            }
        } else {
    %>
    <tr>
        <td colspan="2">No top selling items available.</td>
    </tr>
    <% } %>
</table>


    <script>
        const salesData = <%= request.getAttribute("salesDataJson") != null ? request.getAttribute("salesDataJson") : "[]" %>;
        const labels = salesData.map(item => item.date);
        const sales = salesData.map(item => item.total);

        const ctx = document.getElementById('salesChart').getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Daily Sales',
                    data: sales,
                    backgroundColor: 'rgba(249,124,13,0.2)',
                    borderColor: '#f97c0d',
                    borderWidth: 2,
                    fill: true
                }]
            },
            options: {
                scales: {
                    y: { beginAtZero: true }
                }
            }
        });
    </script>

</body>
</html>
