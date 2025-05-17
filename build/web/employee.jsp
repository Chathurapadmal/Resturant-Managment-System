<%-- 
    Document   : employee
    Created on : 18 May 2025, 02:03:29
    Author     : Chathura
--%>

<%@page import="DAO.dbdao"%>
<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>All Employees</title></head>
<body>
    <h2>Employee List</h2>
    <table border="1">
        <tr><th>ID</th><th>Full Name</th><th>Username</th><th>Role</th><th>Created At</th></tr>
        <%
            try (Connection con = dbdao.getConnection()) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE role IN ('waiter', 'cashier')");
                while (rs.next()) {
        %>
            <tr>
                <td><%= rs.getInt("id") %></td>
                <td><%= rs.getString("full_name") %></td>
                <td><%= rs.getString("username") %></td>
                <td><%= rs.getString("role") %></td>
                <td><%= rs.getTimestamp("created_at") %></td>
            </tr>
        <%
                }
            } catch (SQLException e) {
                out.println("Error loading employee list.");
            }
        %>
    </table>
    <br>
    <a href="Admin_Dashboard.jsp">Back to Dashboard</a>
</body>
</html>
