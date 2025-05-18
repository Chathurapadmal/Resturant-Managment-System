<%@page import="Model.OrderItem"%>
<%@page import="Model.Order"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Comparator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Kitchen Dashboard</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #fff8f0;
            margin: 0;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #f97c0d;
            margin-bottom: 30px;
        }
        .dashboard {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
        }
        .order-card {
            background-color: #fff3e6;
            border: 1px solid #ccc;
            border-radius: 10px;
            padding: 15px;
            margin: 10px;
            width: 260px;
            box-shadow: 2px 2px 8px rgba(0,0,0,0.1);
        }
        .order-card.completed {
            background-color: #d4edda; /* light green */
        }
        .order-card h4 {
            margin: 10px 0 5px 0;
            color: #333;
        }
        .order-card p {
            margin: 5px 0;
            font-size: 14px;
        }
        .items {
            margin-top: 10px;
        }
        .items ul {
            padding-left: 18px;
            margin: 5px 0;
        }
        .items li {
            font-size: 13px;
        }
        .btn {
            padding: 6px 12px;
            margin: 5px 2px;
            border: none;
            border-radius: 5px;
            background-color: #f97c0d;
            color: white;
            cursor: pointer;
            font-size: 14px;
        }
        .btn:disabled {
            background-color: #aaa;
            cursor: not-allowed;
        }
        .btn-group {
            text-align: center;
            margin-top: 10px;
        }
        .completed-label {
            color: green;
            font-weight: bold;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body>

    
    <header class="navbar">


  <nav class="nav-links">
    <a href="POSServlet" class="nav-link">POS</a>
    <a href="AllorderServlet" class="nav-link">Order</a>
  </nav>

</header>
    
<h2>Kitchen Dashboard</h2>

<div class="dashboard">
<%
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
        // Sort so completed orders appear last
        Collections.sort(orders, new Comparator<Order>() {
            public int compare(Order o1, Order o2) {
                boolean c1 = "completed".equalsIgnoreCase(o1.getStatus());
                boolean c2 = "completed".equalsIgnoreCase(o2.getStatus());
                if (c1 && !c2) return 1;
                if (!c1 && c2) return -1;
                return 0;
            }
        });

        for (Order o : orders) {
            boolean isCompleted = "completed".equalsIgnoreCase(o.getStatus());
%>
    <div class="order-card <%= isCompleted ? "completed" : "" %>">
        <h4>Order #: <%= o.getId() %></h4>
        <p>Customer: <%= o.getCustomerName() != null ? o.getCustomerName() : "N/A" %></p>
        <p>Cashier: <%= o.getCashierName() != null ? o.getCashierName() : "N/A" %></p>
        <p>Table #: <%= o.getTableId() %></p>
        <p>Waiter ID: <%= o.getWaiterId() == 0 ? "N/A" : o.getWaiterId() %></p>
        <p>Status: <strong><%= o.getStatus() %></strong></p>

        <div class="items">
            <strong>Items:</strong>
            <ul>
            <%
                List<OrderItem> items = o.getOrderItems();
                if (items != null && !items.isEmpty()) {
                    for (OrderItem item : items) {
                        String itemName = (item.getItem() != null && item.getItem().getName() != null)
                                          ? item.getItem().getName()
                                          : "Unnamed Item";
            %>
                        <li><%= itemName %> x <%= item.getQuantity() %></li>
            <%
                    }
                } else {
            %>
                <li>No items</li>
            <%
                }
            %>
            </ul>
        </div>

        <% if (!isCompleted) { %>
        <form method="post" action="KitchenDashboardServlet">
            <input type="hidden" name="orderId" value="<%= o.getId() %>">
            <div class="btn-group">
                <% if (!"processing".equalsIgnoreCase(o.getStatus())) { %>
                    <button class="btn" name="action" value="start">Start</button>
                    <button class="btn" name="action" value="delete">Delete</button>
                <% } else { %>
                    <button class="btn" disabled>Started</button>
                <% } %>
                <button class="btn" name="action" value="edit">Edit</button>
                <button class="btn" name="action" value="complete">Complete</button>
            </div>
        </form>
        <% } else { %>
        <p class="completed-label">&#10004; Completed</p>
        <% } %>
    </div>
<%
        }
    } else {
%>
    <p style="text-align:center; width:100%;">No orders available in the kitchen.</p>
<%
    }
%>
</div>

</body>
</html>
