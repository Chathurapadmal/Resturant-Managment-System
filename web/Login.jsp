<%-- 
    Document   : Login
    Created on : 3 May 2025, 21:55:32
    Author     : Chathura
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Login - Restaurant POS</title>
  <link href="Style/Login.css" rel="stylesheet" />
</head>
<body>
  <div class="container">
    <div class="login-box">
            <img src="img/logo.png" alt="Infinity Dine" class="logo-img" />

      <img src="img/logo.png" alt="Logo" class="logo" />
      <h2>Login</h2><br><br>

 

      <form action="LoginServlet" method="post">
        <input type="text" name="username" placeholder="Username" required />
        
        <div class="password-wrapper">
          <input type="password" name="password" placeholder="Password" required />
        </div>

        <button type="submit">LOGIN</button>
      </form>

      
           <% if (request.getAttribute("error") != null) { %>
        <div style="color: red; margin-bottom: 15px;">
          <%= request.getAttribute("error") %>
        </div>
      <% } %>
      
      
    </div>
  </div>
</body>
</html>
