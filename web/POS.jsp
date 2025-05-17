<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Item" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>POS System</title>
    <style>
        .menu, .order-summary { float: left; padding: 20px; }
        .menu { width: 60%; }
        .order-summary { width: 35%; background-color: #f4f4f4; }
        .item-button { margin: 5px; padding: 10px; background-color: lightblue; cursor: pointer; display: inline-block; }
        .order-item { margin-bottom: 10px; }
        .qty-btn { margin: 0 5px; padding: 3px 6px; background-color: #ccc; border: none; cursor: pointer; }
    </style>
    <script>
        let orderItems = [];

        function addItem(id, name, price) {
            id = Number(id); // Ensure it's a number
            const existing = orderItems.find(item => item.id === id);
            if (existing) {
                existing.quantity += 1;
            } else {
                orderItems.push({ id, name, price, quantity: 1 });
            }
            renderOrder();
        }

        function changeQuantity(id, change) {
            id = Number(id);
            const item = orderItems.find(item => item.id === id);
            if (item) {
                item.quantity += change;
                if (item.quantity <= 0) {
                    orderItems = orderItems.filter(i => i.id !== id);
                }
                renderOrder();
            }
        }

        function renderOrder() {
            const list = document.getElementById("orderItems");
            list.innerHTML = '';
            let total = 0;
            orderItems.forEach(function(item) {
                total += item.price * item.quantity;
                list.innerHTML += 
                    '<li class="order-item">' +
                    item.name + ' x ' + item.quantity + ' = Rs.' + (item.price * item.quantity).toFixed(2) +
                    ' <button class="qty-btn" onclick="changeQuantity(' + item.id + ', 1)">+</button>' +
                    ' <button class="qty-btn" onclick="changeQuantity(' + item.id + ', -1)">−</button>' +
                    '</li>';
            });
            document.getElementById("totalAmount").innerText = total.toFixed(2);
        }

        function submitOrder() {
            const form = document.getElementById("orderForm");
            // Remove old hidden input if any
            const oldInput = document.querySelector('input[name="itemsJson"]');
            if (oldInput) {
                form.removeChild(oldInput);
            }
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "itemsJson";
            input.value = JSON.stringify(orderItems);
            form.appendChild(input);
            form.submit();
        }
    </script>
</head>
<body>

<div class="menu">
    <h2>Menu</h2>
    <%
        List<Item> items = (List<Item>) request.getAttribute("itemList");
        if (items != null) {
            for (Item item : items) {
    %>
        <div class="item-button" onclick="addItem(<%= item.getId() %>, '<%= item.getName().replace("'", "\\'") %>', <%= item.getPrice() %>)">
            <strong><%= item.getName() %></strong> - Rs.<%= item.getPrice() %>
        </div>
    <%
            }
        }
    %>
</div>

<div class="order-summary">
    <h3>Selected Items</h3>
    <ul id="orderItems"></ul>
    <p>Total: Rs.<span id="totalAmount">0.00</span></p>
    
    <form id="orderForm" action="POSServlet" method="post">
        Customer Name: <input type="text" name="customerName" required><br>
        Phone: <input type="text" name="phone"><br>
        Table ID: <input type="number" name="tableId" required><br>
        Waiter Name: <input type="text" name="waiterName" required><br>
        Cashier Name: <input type="text" name="cashierName" required><br>
        <button type="button" onclick="submitOrder()">Proceed to Payment</button>
    </form>

    <%
        Integer orderId = (Integer) request.getAttribute("orderId");
        if (orderId != null) {
    %>
        <p><strong>Order ID: <%= orderId %></strong></p>
        <script>
            window.onload = function() {
                window.print(); // Auto print after payment
            };
        </script>
    <% } %>
</div>

</body>
</html>
