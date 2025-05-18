package com.restaurantsystem.servlets;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, // 1MB
                 maxFileSize = 1024 * 1024 * 5,     // 5MB
                 maxRequestSize = 1024 * 1024 * 10) // 10MB

@WebServlet ("/AddItemServlet")
public class AddItemServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");
        String category = request.getParameter("category");

        Part filePart = request.getPart("productImage");
        String fileName = extractFileName(filePart);
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String imagePath = "uploads" + File.separator + fileName;
        filePart.write(uploadPath + File.separator + fileName);

        double price = Double.parseDouble(priceStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3309/resturentsystem", "root", ""
            );

            String sql = "INSERT INTO items (name, price, image, category) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setString(3, imagePath);
            ps.setString(4, category);

            int result = ps.executeUpdate();
            conn.close();

            if (result > 0) {
                response.sendRedirect("add_item.jsp?message=Item+added+successfully");
            } else {
                response.sendRedirect("add_item.jsp?message=Error+adding+item");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("add_item.jsp?message=Server+Error");
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return "";
    }
}
