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

    <!-- ACTION added as # to disable page reload -->
    <form id="orderForm" action="#" method="post" onsubmit="return false;">
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
        </div>

        <div class="total-box">
            <button type="button" id="placeOrderBtn">Place Order</button>
            <button type="button" id="printBillBtn" style="display:none;">Print Bill</button>
        </div>
    </form>

    <script>
        const orderForm = document.getElementById('orderForm');
        const placeOrderBtn = document.getElementById('placeOrderBtn');
        const printBillBtn = document.getElementById('printBillBtn');
        let createdOrderId = null;

        placeOrderBtn.addEventListener('click', function (e) {
            e.preventDefault(); // 💥 Cancel any accidental default form submission

            const formData = new FormData(orderForm);
            const selectedItems = [];

            const newFormData = new FormData();
            newFormData.append('customerName', formData.get('customerName'));
            newFormData.append('waiterName', formData.get('waiterName'));
            newFormData.append('tableId', formData.get('tableId'));

            document.querySelectorAll('input[name="itemId"]:checked').forEach(item => {
                const itemId = item.value;
                const qty = document.querySelector(`input[name="qty_${itemId}"]`).value;
                newFormData.append('itemId', itemId);
                newFormData.append(`qty_${itemId}`, qty);
                selectedItems.push(itemId);
            });

            if (selectedItems.length === 0) {
                alert("Please select at least one item.");
                return;
            }

            fetch('POSServlet', {
                method: 'POST',
                body: newFormData
            })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    createdOrderId = data.orderId;
                    printBillBtn.style.display = 'inline-block';
                    alert("Order placed successfully!");
                } else {
                    alert("Order failed: " + (data.message || 'Unknown error.'));
                }
            })
            .catch(err => {
                console.error(err);
                alert("Error placing order.");
            });
        });

        printBillBtn.addEventListener('click', function () {
            if (createdOrderId) {
                window.open('PrintBill.jsp?orderId=' + createdOrderId, '_blank');
            } else {
                alert("No order to print.");
            }
        });
    </script>
</body>
</html>
