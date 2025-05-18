<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Add Item</title>
    <link rel="stylesheet" href="Style/add_item.css">
</head>
<body>
<h2>Add New Menu Item</h2>

<form action="AddItemServlet" method="post" enctype="multipart/form-data">
    <label for="name">Item Name:</label><br>
    <input type="text" name="name" id="name" required><br><br>

    <label for="price">Item Price (Rs):</label><br>
    <input type="number" name="price" id="price" step="0.01" required><br><br>

    <label for="category">Category:</label><br>
    <select name="category" id="category" required>
        <option value="">-- Select Category --</option>
        <option value="Fast Food">Fast Food</option>
        <option value="Meals">Meals</option>
        <option value="Juice">Juice</option>
        <option value="Desserts">Desserts</option>
        <option value="Hot Drinks">Hot Drinks</option>
    </select><br><br>

    <label for="productImage">Product Image:</label><br>
    <input type="file" name="productImage" id="productImage" accept="image/*" required><br><br>

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
