<%-- 
    Document   : Login
    Created on : 3 May 2025, 21:55:32
    Author     : Chathura
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login - Restaurant POS</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #ff7f00;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .login-box {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
            width: 300px;
        }
        .login-box h2 {
            text-align: center;
            color: #ff7f00;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #333;
        }
        .form-group input {
            width: 100%;
            padding: 10px;
            border: 2px solid #ff7f00;
            border-radius: 5px;
            font-size: 14px;
        }
        .login-box button {
            width: 100%;
            padding: 10px;
            background: #ff7f00;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s;
        }
        .login-box button:hover {
            background: #e56d00;
        }
        .error {
            color: red;
            text-align: center;
            margin-bottom: 15px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>Login</h2>
        <form action="LoginServlet" method="post">
            <input type="text" name="username" placeholder="Username" required />
            <input type="password" name="password" placeholder="Password" required />
            <button type="submit">Login</button>
        </form>
        <div class="error">
            <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
        </div>
    </div>
</body>
</html>
