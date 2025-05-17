<%@ page import="Controller.TopItem" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Order" %>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Admin Dashboard</title>
  <link href="Style.css" rel="stylesheet" type="text/css"/>
  <link href="Style/Dashboard.css" rel="stylesheet" type="text/css"/>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />
</head>
<body>

<header class="header">
  <div class="logo">
    <img src="icons/logo.png" alt="Infinity Dine Logo" class="logo-img" />
  </div>   
  <div class="title">ADMIN PANEL</div>
  <div class="dropdown-container" tabindex="0">
    <button class="profile-btn" aria-label="Profile">
      <span class="material-symbols-outlined">account_circle</span>
    </button>
    <div class="dropdown">
      <div class="icon-circle">
        <span class="material-symbols-outlined">account_circle</span>
      </div>
      <form action="LogoutServlet" method="get">
        <button class="signin-btn" type="submit">Logout</button>
      </form>
    </div>
  </div>
</header>

<nav class="navbar">
  <form action="AllorderServlet"><button class="active">Order List</button></form>
  <form action="KitchenDashboardServlet"><button>Kitchen Dashboard</button></form>
  <form action="POSServlet"><button>POS Invoice</button></form>
  <form action="add_item.jsp"><button>Add Product</button></form>

  <div class="menu-dropdown-container">
    <button class="menu-icon">
      <img src="icons/menu.png" alt="">
    </button>
    <div class="menu-dropdown">
      <form action="orderListServlet"><button class="dropdown-btn active">Order List</button></form>
      <form action="KitchenDashboardServlet"><button class="dropdown-btn">Kitchen Dashboard</button></form>
      <form action="POSServlet"><button class="dropdown-btn">POS Invoice</button></form>
      <form action="add_item.jsp"><button class="dropdown-btn">Add Product</button></form>
      <form action="add_employee.jsp"><button class="dropdown-btn">Add Employee</button></form>
    </div>
  </div>
</nav>

<section class="summary-row">
  <div class="summary-box card">
    <div class="card-title">Today Orders</div>
    <div class="card-value"><%= request.getAttribute("todaysOrders") != null ? request.getAttribute("todaysOrders") : 0 %></div>
  </div>
  <div class="summary-box card">
    <div class="card-title">Today Sales</div>
    <div class="card-value"><%= request.getAttribute("todaysSales") != null ? request.getAttribute("todaysSales") : 0 %></div>
  </div>
</section>

<h2 class="section-title">Order Summary</h2>
<table class="order-table">
  <thead>
    <tr>
      <th>Customer Name</th>
      <th>Phone Number</th>
      <th>Order Number</th>
      <th>Table Number</th>
    </tr>
  </thead>
  <tbody>
    <%
      List<Order> orders = (List<Order>) request.getAttribute("orders");
      if (orders != null && !orders.isEmpty()) {
        for (Order o : orders) {
    %>
      <tr>
        <td><%= o.getCustomerName() %></td>
        <td><%= o.getPhoneNumber() %></td>
        <td><%= o.getOrderId() %></td>
        <td><%= o.getTableNumber() %></td>
      </tr>
    <%
        }
      } else {
    %>
      <tr>
        <td colspan="4">No orders available.</td>
      </tr>
    <% } %>
  </tbody>
</table>

<h2 class="section-title">Sales Summary</h2>
<div class="chart-container">
  <canvas id="salesChart" width="400" height="200"></canvas>
</div>

<h2 class="section-title">Top Selling Items</h2>
<table class="order-table">
  <thead>
    <tr>
      <th>Item Name</th>
      <th>Total Sold</th>
    </tr>
  </thead>
  <tbody>
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
  </tbody>
</table>

<script>
  const dates = <%= request.getAttribute("datesJson") != null ? request.getAttribute("datesJson") : "[]" %>;
  const totals = <%= request.getAttribute("totalsJson") != null ? request.getAttribute("totalsJson") : "[]" %>;

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

<script>
  const container = document.querySelector('.menu-dropdown-container');
  const dropdown = container.querySelector('.menu-dropdown');

  let hideTimeout;
  container.addEventListener('mouseenter', () => {
    clearTimeout(hideTimeout);
    dropdown.style.display = 'block';
  });
  container.addEventListener('mouseleave', () => {
    hideTimeout = setTimeout(() => {
      dropdown.style.display = 'none';
    }, 300);
  });
</script>

</body>
</html>
