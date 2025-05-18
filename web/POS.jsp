<%@ page import="java.util.*" %>
<%@ page import="Model.Item" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Item> menuItems = (List<Item>) request.getAttribute("menuItems");
    Map<String, List<Item>> categoryMap = new LinkedHashMap<>();

    if(menuItems != null) {
        for(Item item : menuItems) {
            String category = (item.getCategory() != null && !item.getCategory().trim().isEmpty()) ? item.getCategory() : "Uncategorized";
            if(!categoryMap.containsKey(category)) {
                categoryMap.put(category, new ArrayList<Item>());
            }
            categoryMap.get(category).add(item);
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />

<link rel="StyleSheet" href = "Style/pos.css">

<title>POS - Menu by Categories</title>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
</head>
<body>

<h1>Place a New Order</h1>

<form action="POSServlet" method="post" id="orderForm" onsubmit="return prepareOrder()">

    Customer Name: <input type="text" name="customerName" /><br/><br/>
    Table Number: <input type="number" name="table" required /><br/><br/>

    <div class="container">

        <div class="menu">
            <h3>Menu Items (Click to add)</h3>

            <%
                if(categoryMap.isEmpty()) {
            %>
                <p>No menu items found.</p>
            <%
                } else {
                    for(Map.Entry<String, List<Item>> entry : categoryMap.entrySet()) {
                        String category = entry.getKey();
                        List<Item> itemsInCategory = entry.getValue();
            %>
                <div class="category-section">
                    <div class="category-title"><%= category %></div>
                    <div class="menu-grid">
                        <%
                            for(Item item : itemsInCategory) {
                                String imgPath = (item.getImage() != null && !item.getImage().trim().isEmpty()) 
                                                ? item.getImage() : "web/uploads/default.png";
                        %>
                            <div class="menu-item" 
                                 data-id="<%= item.getId() %>" 
                                 data-name="<%= item.getName() %>" 
                                 data-price="<%= item.getPrice() %>"
                                 onclick="addItem(this)">
                                <img src="<%= imgPath %>" alt="<%= item.getName() %>" />
                                <div class="menu-item-name"><%= item.getName() %></div>
                                <div class="menu-item-price">Rs.<%= String.format("%.2f", item.getPrice()) %></div>
                            </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            <%
                    }
                }
            %>
        </div>

        <div class="order-summary">
            <h3>Your Order</h3>
            <table id="orderTable">
                <thead>
                    <tr><th>Item</th><th>Price</th><th>Quantity</th><th>Total</th><th>Remove</th></tr>
                </thead>
                <tbody id="orderBody"></tbody>
            </table>
            <h4>Total: Rs.<span id="orderTotal">0.00</span></h4>
        </div>

    </div>

    <br/>
    <button type="button" id="printBillBtn">Print Bill</button>
    <button type="submit">Place Order</button>

</form>

<script>
  const orderItems = {};

  function addItem(element) {
    const id = element.getAttribute('data-id');
    const name = element.getAttribute('data-name');
    const price = parseFloat(element.getAttribute('data-price'));

    if(orderItems[id]) {
      orderItems[id].qty += 1;
    } else {
      orderItems[id] = { name: name, price: price, qty: 1 };
    }
    renderOrder();
  }

  function removeItem(id) {
    delete orderItems[id];
    renderOrder();
  }

  function changeQty(id, input) {
    const val = parseInt(input.value);
    if(val <= 0 || isNaN(val)) {
      input.value = orderItems[id].qty;
      return;
    }
    orderItems[id].qty = val;
    renderOrder();
  }

  function renderOrder() {
    const tbody = document.getElementById('orderBody');
    tbody.innerHTML = '';

    let total = 0;
    for(let id in orderItems) {
      const item = orderItems[id];
      const row = document.createElement('tr');

      const nameTd = document.createElement('td');
      nameTd.textContent = item.name;
      row.appendChild(nameTd);

      const priceTd = document.createElement('td');
      priceTd.textContent = "Rs." + item.price.toFixed(2);
      row.appendChild(priceTd);

      const qtyTd = document.createElement('td');
      const qtyInput = document.createElement('input');
      qtyInput.type = 'number';
      qtyInput.min = '1';
      qtyInput.value = item.qty;
      qtyInput.style.width = '50px';
      qtyInput.onchange = () => changeQty(id, qtyInput);
      qtyTd.appendChild(qtyInput);
      row.appendChild(qtyTd);

      const totalTd = document.createElement('td');
      const lineTotal = item.price * item.qty;
      totalTd.textContent = "Rs." + lineTotal.toFixed(2);
      row.appendChild(totalTd);

      const removeTd = document.createElement('td');
      const removeBtn = document.createElement('span');
      removeBtn.textContent = 'X';
      removeBtn.className = 'remove-btn';
      removeBtn.onclick = () => removeItem(id);
      removeTd.appendChild(removeBtn);
      row.appendChild(removeTd);

      tbody.appendChild(row);

      total += lineTotal;
    }

    document.getElementById('orderTotal').textContent = total.toFixed(2);
  }

  function prepareOrder() {
    const form = document.getElementById('orderForm');
    const oldInputs = document.querySelectorAll('.dynamic-input');
    oldInputs.forEach(input => input.remove());

    if(Object.keys(orderItems).length === 0) {
      alert('Please add at least one item to order.');
      return false;
    }

    for(let id in orderItems) {
      const item = orderItems[id];

      const inputId = document.createElement('input');
      inputId.type = 'hidden';
      inputId.name = 'itemId';
      inputId.value = id;
      inputId.classList.add('dynamic-input');
      form.appendChild(inputId);

      const inputQty = document.createElement('input');
      inputQty.type = 'hidden';
      inputQty.name = 'qty';
      inputQty.value = item.qty;
      inputQty.classList.add('dynamic-input');
      form.appendChild(inputQty);

      const inputPrice = document.createElement('input');
      inputPrice.type = 'hidden';
      inputPrice.name = 'price';
      inputPrice.value = item.price;
      inputPrice.classList.add('dynamic-input');
      form.appendChild(inputPrice);
    }

    return true;
  }

  // Print Bill button handler
  document.getElementById('printBillBtn').addEventListener('click', () => {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    doc.setFontSize(16);
    doc.text("Order Bill", 14, 20);

    // Customer & Table info
    const customerName = document.querySelector('input[name="customerName"]').value || "N/A";
    const tableNumber = document.querySelector('input[name="table"]').value || "N/A";

    doc.setFontSize(12);
    doc.text(`Customer Name: ${customerName}`, 14, 30);
    doc.text(`Table Number: ${tableNumber}`, 14, 38);

    // Prepare order table data
    const headers = [["Item", "Price", "Quantity", "Total"]];
    const data = [];

    const tbodyRows = document.querySelectorAll("#orderBody tr");
    tbodyRows.forEach(row => {
      const cells = row.querySelectorAll("td");
      if(cells.length >= 4) {
        const item = cells[0].textContent.trim();
        const price = cells[1].textContent.trim();
        const qty = cells[2].querySelector("input").value.trim();
        const total = cells[3].textContent.trim();
        data.push([item, price, qty, total]);
      }
    });

    if(data.length === 0){
      alert("No items in order to print.");
      return;
    }

    // Dynamically load jsPDF autotable plugin
    const script = document.createElement("script");
    script.src = "https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.28/jspdf.plugin.autotable.min.js";
    script.onload = () => {
      doc.autoTable({
        head: headers,
        body: data,
        startY: 45,
        theme: 'grid',
        headStyles: { fillColor: [21, 101, 192] },
      });

      const totalValue = document.getElementById("orderTotal").textContent.trim();
      const totalY = doc.lastAutoTable.finalY + 10;
      doc.setFontSize(14);
      doc.setTextColor(0, 0, 0);
      doc.text(`Grand Total: Rs.${Total}`, 14, totalY);

      doc.save("order-bill.pdf");
    };
    document.body.appendChild(script);
  });
</script>

</body>
</html>
