<%-- 
    Document   : add_item
    Created on : 14 May 2025, 10:21:16
    Author     : Chathura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Item</title>
        <link rel="stylesheet" href="Style/additem.css">


</head>
<body>
<h2>Add New Menu Item</h2>

<form action="AddItemServlet" method="post">
    <label for="name">Item Name:</label><br>
    <input type="text" name="name" id="name" required><br><br>

    <label for="price">Item Price (Rs):</label><br>
    <input type="number" name="price" id="price" step="0.01" required><br><br>

    <input type="submit" value="Add Item">
</form>

<%
    String message = request.getParameter("message");
    if (message != null) {
%>
    <p style="color:green;"><%= message %></p>
<%
    }
%>

</body>
</html>

</html>
