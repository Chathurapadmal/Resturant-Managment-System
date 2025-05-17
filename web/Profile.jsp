<%-- 
    Document   : Profile
    Created on : 17 May 2025, 15:48:30
    Author     : Chathura
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ page import="Model.Userm" %> <!-- Replace with your actual User class package -->
<%@ page session="true" %>
<%
    Userm user = (Userm) session.getAttribute("loggedInUser");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>My Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
            background: #f2f2f2;
        }
        .profile-container {
            background: #fff;
            padding: 25px;
            width: 500px;
            margin: auto;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        .profile-pic {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #555;
        }
        .info {
            margin-top: 20px;
        }
        .info p {
            margin: 5px 0;
        }
        .edit-form {
            margin-top: 20px;
        }
        .edit-form input, .edit-form textarea {
            width: 100%;
            padding: 8px;
            margin-top: 8px;
            margin-bottom: 15px;
        }
        .edit-form button {
            padding: 10px 15px;
            background: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>

<div class="profile-container">
    <h2>My Profile</h2>

    <img class="profile-pic" src="<%= (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) ? "uploads/" + user.getProfilePicture() : "default-avatar.png" %>" alt="Profile Picture">

    <div class="info">
        <p><strong>Name:</strong> <%= user.getFullName() %></p>
        <p><strong>Role:</strong> <%= user.getRole() %></p>
        <p><strong>Bio:</strong></p>
        <p><%= user.getBio() != null ? user.getBio() : "No bio yet." %></p>
    </div>

    <hr>

    <form class="edit-form" action="UpdateProfileServlet" method="post" enctype="multipart/form-data">
        <label for="fullName">Full Name</label>
        <input type="text" name="fullName" value="<%= user.getFullName() %>" required>

        <label for="bio">Bio</label>
        <textarea name="bio" rows="4"><%= user.getBio() != null ? user.getBio() : "" %></textarea>

        <label for="profilePicture">Change Profile Picture</label>
        <input type="file" name="profilePicture" accept="image/*">

        <button type="submit">Update Profile</button>
    </form>
</div>

</body>
</html>

