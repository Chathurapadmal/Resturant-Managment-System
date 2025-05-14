<%@page import="java.util.List"%>
<%@page import="Model.Item"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>POS System</title>
    <style>
        body { font-family: Arial; }
        .container { display: flex; }
        .items, .order { margin: 10px; padding: 10px; border: 1px solid #ccc; border-radius: 8px; }
        .items { width: 40%; background-color: #fff3e6; }
        .order { width: 50%; background-color: #f9f9f9; }
        .item-row { margin-bottom: 10px; }
        .total-box { font-weight: bold; margin-top: 10px; }
    </style>
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
                <!-- Summary will be displayed after submission or via JS in enhancement -->
            </div>
        </div>

        <div class="total-box">
            <input type="submit" value="Place Order">
        </div>
    </form>
</body>
</html>
