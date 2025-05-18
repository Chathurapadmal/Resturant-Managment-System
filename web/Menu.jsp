<%-- 
    Document   : Menu
    Created on : 7 May 2025, 21:43:07
    Author     : Chathura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="Model.product" %>
<html>
<head>
    <title>Menu</title>
    <style>
        .menu-item { border: 1px solid #ccc; padding: 10px; margin: 10px; display: inline-block; width: 200px; text-align: center; }
        img { max-width: 100%; height: auto; }
    </style>
</head>
<body>
    <h2>Our Menu</h2>
    <div class="menu">
        <%
            List<product> products = (List<product>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
                for (product p : products) {
        %>
        <div class="menu-item">
            <img src="<%= p.getImage() %>" alt="Product Image" />
            <h3><%= p.getName() %></h3>
            <p>Price: $<%= p.getPrice() %></p>
        </div>
        <%
                }
            } else {
        %>
        <p>No products available.</p>
        <%
            }
        %>
    </div>
</body>
</html>

