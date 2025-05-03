<%-- 
    Document   : pos
    Created on : 3 May 2025, 20:14:47
    Author     : Chathura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant POS</title>
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            margin: 0;
            padding: 0;
            background: #fff;
            color: #ff7f00;
        }
        .navbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 20px;
            background: #ff7f00;
            color: white;
        }
        .navbar .links button {
            background: white;
            color: #ff7f00;
            border: none;
            padding: 10px 20px;
            margin: 0 5px;
            cursor: pointer;
            font-weight: bold;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s;
        }
        .navbar .links button:hover {
            transform: scale(1.05);
        }
        .profile {
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .container {
            display: grid;
            grid-template-columns: 2fr 1fr;
            padding: 20px;
            gap: 20px;
        }
        .left-panel, .right-panel {
            background: #fff7f0;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            position: relative;
        }
        .form-section {
            display: flex;
            align-items: center;
            gap: 20px;
            margin-bottom: 20px;
        }
        .form-group label {
            font-weight: bold;
            font-size: 14px;
        }
        .form-group input {
            padding: 8px;
            border: 2px solid #ff7f00;
            border-radius: 5px;
            width: 120px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            background: #ff7f00;
            color: white;
            border-radius: 10px;
            overflow: hidden;
        }
        table th, table td {
            padding: 12px;
            text-align: left;
        }
        table th {
            background: #e56d00;
        }
        .order-summary {
            background: #ff7f00;
            color: white;
            padding: 15px;
            border-radius: 10px;
            font-weight: bold;
            font-size: 16px;
        }
        .item-list {
            position: absolute;
            top: 20px;
            left: -160px;
            width: 140px;
            background: #fff7f0;
            border-radius: 12px;
            padding: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .item-list select {
            width: 100%;
            padding: 10px;
            border: 2px solid #ff7f00;
            border-radius: 5px;
            margin-bottom: 10px;
            color: #ff7f00;
            font-weight: bold;
        }
        .right-panel {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 15px;
            position: relative;
        }
        .table-box {
            width: 100%;
            padding-top: 100%;
            position: relative;
            background: #ffe0b3;
            border: 2px dashed #ff7f00;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            transition: background 0.3s;
        }
        .table-box:hover {
            background: #ffd699;
        }
        .table-box img {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            max-width: 80%;
            max-height: 80%;
            border-radius: 8px;
        }
    </style>
</head>
<body>

    <div class="navbar">
        <div class="links">
            <button>Home</button>
            <button>New Order</button>
            <button>Pending</button>
        </div>
        <div class="profile">
            <span>Profile</span>
            <span>Operator's Name - Designation | Login as ADMIN</span>
        </div>
    </div>

    <div class="container">
        <div class="left-panel">
            <div class="form-section">
                <div class="form-group">
                    <label>Customer Name</label>
                    <input type="text" placeholder="Name">
                </div>
                <div class="form-group">
                    <label>Waiter</label>
                    <input type="text" placeholder="Waiter">
                </div>
                <div class="form-group">
                    <label>Table</label>
                    <input type="text" placeholder="Table No">
                </div>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Item</th>
                        <th>Price</th>
                        <th>Qty.</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Fruit Juice</td>
                        <td>150</td>
                        <td>2</td>
                        <td>300</td>
                    </tr>
                </tbody>
            </table>

            <div class="order-summary">
                <p>Total: 1000.00</p>
                <p>Service Charge: 500.00</p>
                <p>VAT/Tax: 1500.00</p>
                <p>Grand Total: 3000.00</p>
            </div>
        </div>

        <div class="right-panel">
            <div class="item-list">
                <select>
                    <option>Item 1</option>
                    <option>Item 2</option>
                    <option>Item 3</option>
                </select>
                <select>
                    <option>Item 1</option>
                    <option>Item 2</option>
                    <option>Item 3</option>
                </select>
                <select>
                    <option>Item 1</option>
                    <option>Item 2</option>
                    <option>Item 3</option>
                </select>
            </div>

            <div class="table-box"><img src="https://via.placeholder.com/50" alt="Food"></div>
            <div class="table-box"></div>
            <div class="table-box"></div>
            <div class="table-box"></div>
            <div class="table-box"></div>
            <div class="table-box"></div>
            <div class="table-box"></div>
            <div class="table-box"></div>
            <div class="table-box"></div>
        </div>
    </div>

</body>
</html>