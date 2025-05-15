<%@page import="java.util.List"%>
<%@page import="Model.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>POS System</title>
    <link rel="stylesheet" href="Style/pos.css">


</head>
<body>
    <h2>New Order</h2>
    <form action="POSServlet" method="post">
        <label>Customer Name: <input type="text" name="customerName" required></label>
        <label>Waiter: <input type="text" name="waiterName" required></label>
        <label>Table: <input type="number" name="tableId" required></label>

        <div class="container">
            <div class="items">
                <h3>Item List</h3>
                <%
                    List<Item> itemList = (List<Item>) request.getAttribute("itemList");
                    for (Item item : itemList) {
                %>
                <div class="item-row">
                    <input type="checkbox" name="itemId" value="<%= item.getId() %>">
                    <%= item.getName() %> - Rs. <%= item.getPrice() %>
                    Qty: <input type="number" name="qty_<%= item.getId() %>" min="1" value="1">
                </div>
                <% } %>
            </div>

            <div class="order">
                <h3>Order Summary</h3>
                <p>Items selected will show after submission.</p>
            </div>
        </div>

        <div class="total-box">
            <input type="submit" value="Place Order">
        </div>
    </form>
</body>
</html>
