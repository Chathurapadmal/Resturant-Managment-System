<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, DAO.dbdao, Model.Userm" %>
<%@ page session="true" %>

<%
    Integer userId = (Integer) session.getAttribute("userId");

    if (userId == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    Userm user = null;

    try {
        con = dbdao.getConnection();
        String sql = "SELECT * FROM users WHERE id = ?";
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, userId);
        rs = stmt.executeQuery();

        if (rs.next()) {
            user = new Userm();
            user.setId(rs.getInt("id"));
            user.setFullName(rs.getString("full_name"));
            user.setUsername(rs.getString("username"));
            user.setRole(rs.getString("role"));
            user.setBio(rs.getString("bio"));
            user.setProfilePicture(rs.getString("profile_picture"));
        } else {
            response.sendRedirect("Login.jsp");
            return;
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("Login.jsp");
        return;
    } finally {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (con != null) con.close();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Admin Dashboard</title>

  <!-- Google Material Symbols -->
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet" />
  <!-- External CSS -->
  <link rel="stylesheet" href="style1.css" />
</head>
<body>

  <!-- Header -->
  <header class="header">
    <div class="logo">
      <img src="icons/logo.png" alt="Infinity Dine Logo" class="logo-img" />
    </div>   
    <div class="title">PROFILE</div>
    <div class="dropdown-container" tabindex="0">
      <button class="profile-btn" aria-label="Profile">
        <span class="material-symbols-outlined">account_circle</span>
      </button>
      <div class="dropdown">
        <div class="icon-circle">
          <span class="material-symbols-outlined">account_circle</span>
        </div>
        <form action="LogoutServlet" method="post">
          <button class="signin-btn" type="submit">Sign out</button>
        </form>
      </div>
    </div>
  </header>

  <!-- Navbar -->
  <nav class="navbar">
    <form action="orderListServlet"><button>Dashboard</button></form>
    <form action="KitchenDashboardServlet"><button>Kitchen</button></form>
    <form action="POSServlet"><button>POS Invoice</button></form>
    <form action="add_item.jsp"><button>Add Product</button></form>
  </nav>

  <!-- Main Content Area -->
  <main class="main-content">
    <div class="profile-layout">
      <!-- Left Side -->
      <div class="profile-left">
        <img src="<%= (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) ? "uploads/" + user.getProfilePicture() : "default-avatar.png" %>" alt="Profile" class="profile-pic" />
        <div class="name"><%= user.getFullName() %></div>
        <div class="title-card"><%= user.getRole() %></div>
      </div>

      <!-- Right Side -->
      <div class="profile-right">
        <div class="bio">
          <%= (user.getBio() != null && !user.getBio().isEmpty()) ? user.getBio() : "No bio yet." %>
        </div>

        <hr style="margin: 20px 0;">

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
    </div>

    <!-- Bottom Center Sign Out Button -->
    <div class="signout-container">
      <form action="LogoutServlet" method="post">
        <button class="signout-btn" type="submit">Sign Out</button>
      </form>
    </div>
  </main>

</body>
</html>
