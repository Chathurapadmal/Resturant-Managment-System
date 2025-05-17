<%-- 
    Document   : add_employee
    Created on : 18 May 2025, 02:00:20
    Author     : Chathura
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Employee</title>
</head>
<body>
    <h2>Add New Employee</h2>
    <form method="post" action="AddEmployeeServlet">
        Full Name: <input type="text" name="fullName" required><br>
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        Role:
        <select name="role" required>
            <option value="waiter">Waiter</option>
            <option value="cashier">Cashier</option>
        </select><br>
        <input type="submit" value="Add Employee">
    </form>
    <br>
    <a href="admin-dashboard.jsp">Back to Dashboard</a>
</body>
</html>
