<%-- 
    Document   : Dashboard
    Created on : 6 May 2025, 13:19:36
    Author     : Chathura
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #fff;
            margin: 0;
            padding: 0;
        }
        .header {
            background: #f97c0d;
            padding: 20px;
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .logo {
            font-size: 24px;
            font-weight: bold;
        }
        .profile {
            background: white;
            color: #f97c0d;
            padding: 10px 20px;
            border-radius: 20px;
            text-decoration: none;
            font-weight: bold;
        }
        .nav {
            display: flex;
            justify-content: center;
            gap: 20px;
            background: #f9d6b5;
            padding: 15px;
        }
        .nav button {
            padding: 10px 15px;
            background: white;
            border: none;
            border-radius: 10px;
            font-weight: bold;
            cursor: pointer;
        }
        .cards {
            display: flex;
            justify-content: center;
            gap: 30px;
            padding: 20px;
        }
        .card {
            background: #ffe8d6;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
            box-shadow: 2px 2px 5px rgba(0,0,0,0.1);
            width: 200px;
        }
        .card h2 {
            margin: 0;
            font-size: 36px;
            color: #f97c0d;
        }
        .section-title {
            background: #f97c0d;
            color: white;
            padding: 10px;
            margin: 20px;
            border-radius: 10px;
            font-size: 18px;
        }
        table {
            width: 90%;
            margin: 0 auto 30px;
            border-collapse: collapse;
            text-align: center;
            background: #ffe8d6;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #f97c0d;
        }
        th {
            background: #f97c0d;
            color: white;
        }
        .chart, .top-items {
            width: 90%;
            margin: 0 auto 30px;
            background: #ffe8d6;
            height: 300px;
            border-radius: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
    </style>
</head>
<body>

    <div class="header">
        <div class="logo">INFINITY DINE - ADMIN PANEL</div>
        <a href="#" class="profile">Profile</a>
    </div>

    <div class="nav">
        <button>Order List</button>
        <button>Kitchen Dashboard</button>
        <button>POS Invoice</button>
        <button>Add Product</button>
    </div>

    <div class="cards">
        <div class="card">
            <div>Today Orders</div>
            <h2>34</h2> <!-- Ideally dynamic -->
        </div>
        <div class="card">
            <div>Today Sales</div>
            <h2>3500</h2> <!-- Ideally dynamic -->
        </div>
    </div>

    <div class="section-title">Order Summary</div>
    <table>
        <tr>
            <th>Customer Name</th>
            <th>Phone Number</th>
            <th>Order Number</th>
            <th>Table Number</th>
        </tr>
        <%
            List<Order> orders = (List<Order>) request.getAttribute("orders");
            if (orders != null) {
                for (Order o : orders) {
        %>
        <tr>
            <td><%= o.getCustomerName() %></td>

        </tr>
        <% 
                }
            } else {
        %>
        <tr>
            <td colspan="4">No orders available.</td>
        </tr>
        <% } %>
    </table>

    <div class="section-title">Sales Summary</div>
    <div class="chart">
        <!-- You can insert a chart here using Chart.js or an image placeholder -->
        <p>Sales Chart Placeholder</p>
    </div>

    <div class="section-title">Top Selling Items</div>
    <div class="top-items">
        <!-- List your top items or charts here -->
        <p>Top Selling Items Placeholder</p>
    </div>

</body>
</html>
