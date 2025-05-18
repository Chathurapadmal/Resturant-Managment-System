<%@page import="java.util.Map"%>
<%@page import="Model.Item"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>POS Page</title>
  <link rel="stylesheet" href="Style/pos.css">
</head>
<body>

  <div class="header">
    <div>
      <img src="icons/logo.png" class="logo" alt="Logo">
    </div>
    <div class="profile">
      <img src="icons/account.png" height="30px" alt="Account">
    </div>
  </div>

  <header class="navbar">
    <nav class="nav-links">
      <a href="KitchenDashboardServlet" class="nav-link">Kitchen</a>
      <a href="AllorderServlet" class="nav-link">Order</a>
    </nav>
  </header>

  <div class="container">
    <!-- MENU SECTION -->
    <div class="menu">
      <h2>MENU</h2>
      <%
        Map<String, List<Item>> categorizedItems = (Map<String, List<Item>>) request.getAttribute("categorizedItems");
        if (categorizedItems != null && !categorizedItems.isEmpty()) {
          for (Map.Entry<String, List<Item>> entry : categorizedItems.entrySet()) {
            String category = entry.getKey();
            List<Item> items = entry.getValue();
            String categoryId = category.replaceAll("\\s+", "") + "_grid";
      %>
        <div class="category">
          <button class="category-button" onclick="toggleCategory('<%= categoryId %>')"><%= category %></button>
          <div class="items-grid" id="<%= categoryId %>" style="display: none;">
            <% for (Item item : items) { %>
              <div class="item-box" onclick="addToOrder('<%= item.getName() %>', <%= item.getPrice() %>)">
                <%= item.getName() %><br/>
                Rs. <%= item.getPrice() %>
              </div>
            <% } %>
          </div>
        </div>
      <% 
          }
        } else { 
      %>
        <p>No items found.</p>
      <% } %>
    </div>

    <!-- ORDER SECTION -->
    <div class="order">
      <h2>Order</h2>
      <div class="input-row">
        <span>Customer</span>
        <input type="text" id="customerName" />
      </div>

      <div class="input-row">
        <span>Table</span>
        <input type="text" id="tableNumber" />
      </div>

      <div class="order-list" id="orderList"></div>

      <div class="order-summary">
        <div class="input-row">
          <span>Total</span>
          <input type="text" id="total" readonly />
        </div>
        <div class="input-row">
          <span>Cash</span>
          <input type="text" id="cash" oninput="calculateBalance()" />
        </div>
        <div class="input-row">
          <span>Balance</span>
          <input type="text" id="balance" readonly />
        </div>
      </div>

      <button onclick="submitOrder()">Add TO Bill</button>
    </div>
  </div>

  <script>
    function toggleCategory(id) {
      const grid = document.getElementById(id);
      grid.style.display = grid.style.display === "none" ? "grid" : "none";
    }

    let orderItems = [];
    function addToOrder(name, price) {
      orderItems.push({ name, price });
      renderOrderList();
    }

    function renderOrderList() {
      const list = document.getElementById("orderList");
      list.innerHTML = "";
      let total = 0;
      orderItems.forEach(item => {
        const div = document.createElement("div");
        div.className = "order-item";
        div.innerHTML = `<span>${item.name}</span><span>1</span><span>${item.price}</span>`;
        list.appendChild(div);
        total += item.price;
      });
      document.getElementById("total").value = total;
      calculateBalance();
    }

    function calculateBalance() {
      const total = parseFloat(document.getElementById("total").value) || 0;
      const cash = parseFloat(document.getElementById("cash").value) || 0;
      const balance = cash - total;
      document.getElementById("balance").value = balance >= 0 ? balance : 0;
    }

    function submitOrder() {
      alert("Order submitted! (Hook this to servlet/backend)");
      // TODO: Implement AJAX or form submission here to POST order data
    }
  </script>

</body>
</html>
